package com.fuqinqin.efficientKits.controller;

import com.fuqinqin.efficientKits.entity.common.Result;
import com.fuqinqin.efficientKits.entity.mail.Mail;
import com.fuqinqin.efficientKits.entity.mail.MailRequest;
import com.fuqinqin.efficientKits.enums.MailServiceSenderType;
import com.fuqinqin.efficientKits.enums.ResultCode;
import com.fuqinqin.efficientKits.exception.EfficientMailException;
import com.fuqinqin.efficientKits.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送
 * @author fuqinqin
 * @date 2018-09-26
 */
@RestController
@RequestMapping("/mail")
@Slf4j
public class MailController {

    @Autowired
    private IMailService mailService;

    @Value("${spring.http.multipart.max-file-size}")
    private String maxFileSize;

    private static final String[] specialCharacters = {" ", "~","@","#","$","%","^","&","*","_",".","/"};

    @GetMapping("/hello")
    public String sayHello(){
        return "hello world";
    }

    @RequestMapping(value = "/sendAttachment", method = RequestMethod.POST)
    public Result sendAttachmentMail(MailRequest mailRequest, @RequestParam("files") MultipartFile[] files) throws UnsupportedEncodingException {
        mailRequest.check();

        Mail mail = new Mail();
        BeanUtils.copyProperties(mailRequest, mail);
        mail.decoder("UTF-8");

        MailServiceSenderType serviceSenderType = MailServiceSenderType.parseUsername(mailRequest.getUsername());
        if(serviceSenderType == null){
            return new Result(ResultCode.ILLEGAL_MAIL, ResultCode.ILLEGAL_MAIL.msg()+"[illegal username]");
        }
        mail.setSenderType(serviceSenderType);

        if(files!=null && files.length>0){
            // 单个文件大小上限，单位byte
            Long maxFileSizeValue = Long.parseLong(maxFileSize.substring(0, maxFileSize.indexOf("Mb")))*1024*1024;
            Map<String, Resource> resourceMap = new HashMap<>();
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                // 判断单个文件是否超出大小上限
                if(file.getSize() > maxFileSizeValue){
                    return new Result(ResultCode.MAIL_MAX_FILE_SIZE);
                }

                // 文件名
                String fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
                if(containSpecialCharacters(fileName)){
                    log.info("file={}, 包含特殊字符", fileName);
                    return new Result(ResultCode.SPECIAL_CHARACTERS);
                }

                ByteArrayResource byteArrayResource = null;
                try {
                    byteArrayResource = new ByteArrayResource(toByte(file.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                    return new Result(ResultCode.MAIL_FAILED);
                }
                resourceMap.put(fileName, byteArrayResource);
            }
            mail.setResourceMap(resourceMap);
        }

        try {
            mailService.sendMail(mail);
        } catch (EfficientMailException e) {
            e.printStackTrace();
            return new Result(e.getResultCode(), e.getResultCode().msg()+"["+e.getMessage()+"]");
        }

        return new Result(ResultCode.OK);
    }

    private byte[] toByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bys = new byte[1024];
        int rc = 0;
        while ((rc = inputStream.read(bys, 0, 1024)) > 0){
            outputStream.write(bys, 0, rc);
        }
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        inputStream.close();
        return bytes;
    }

    private boolean containSpecialCharacters(String target){
        if(StringUtils.isBlank(target)){
            return false;
        }

        target = target.substring(0, target.lastIndexOf("."));

        for (String specialCharacter : specialCharacters) {
            if(target.contains(specialCharacter)){
                return true;
            }
        }

        return false;
    }

}
