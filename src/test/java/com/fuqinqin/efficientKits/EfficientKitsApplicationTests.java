package com.fuqinqin.efficientKits;

import com.fuqinqin.efficientKits.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EfficientKitsApplicationTests {

	@Autowired
	private IMailService mailService;

	@Test
	public void sendSimpleMail() {
//		mailService.sendSimpleMail("fqq904571943@163.com", "来自fqq904571943@126.com的邮件", "来自126的邮件，请查收。");
		log.info("-- send success --");
	}

	@Test
	public void sendHtmlMail() {
		String html = "<html><body><h2 style='color:red;'>这是一封发送HTML的测试邮件</h2><img src='http://t2.hddhhn.com/uploads/tu/201611/168/st30.png'/></body></html>";
//		mailService.sendHtmlMail("fqq904571943@163.com", "来自fqq904571943@126.com的html邮件", html);
		log.info("-- send success --");
	}

	@Test
	public void sendAttachmentMail(){
		String[] filePaths = new String[]{"F:\\lombok-plugin-0.17-2018.1.zip", "F:\\retry.sh"};
		String html = "<html><body><h2 style='color:red;'>这是一封发送带附件的测试邮件</h2></body></html>";
//		mailService.sendAttachmentMail("fqq904571943@163.com", "来自fqq904571943@126.com的附件邮件", html, filePaths);
		log.info("-- send success --");
	}

}
