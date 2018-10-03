package com.fuqinqin.efficientKits.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by fuqinqin on 2018/9/29.
 */
public class Base64Util {
    private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    /**
     * 3个byte为一组，一起编码
     * */
    private static final Integer GROUP_EACH = 3;

    /**
     * 编码
     * */
    public static String encode(byte[] data){
        if(data==null || data.length<=0){
            return null;
        }

        // 循环次数
        int loop = data.length/3 + (data.length%3==0?0:1);
        // 字符串长度
        int length = data.length;
        int temp;
        StringBuilder encodes = new StringBuilder(loop*4);
        for (int i = 0; i < length; i+=3) {
            temp = (data[i]&0xFC)>>2;
            encodes.append(CODES.charAt(temp));
            temp = (data[i]&0x03)<<4;
            if(i != length-1){
                temp |= (data[i+1]&0xF0)>>4;
                encodes.append(CODES.charAt(temp));
                temp = (data[i+1]&0x0F) << 2;
                if((i+1) != length-1){
                    temp |= (data[i+2]&0xC0)>>6;
                    encodes.append(CODES.charAt(temp));
                    encodes.append(CODES.charAt(data[i+2]&0x3F));
                }else{// 最后一组只有两个元素
                    encodes.append(CODES.charAt(temp));
                    encodes.append("=");
                }
            }else{// 最有一组只有一个元素
                encodes.append(CODES.charAt(temp));
                encodes.append("==");
            }
        }

        return encodes.toString();
    }

    /**
     * 解码
     * */
    public static byte[] decode(String data){
        if(data==null || "".equals(data.trim())){
            return null;
        }

        if(data.length()%4 != 0){
            throw new IllegalArgumentException("无效的Base64字符串");
        }

        // 计算原字节数组的真实长度
        int byteLength = data.length()*3/4;
        byteLength -= data.length()-data.indexOf("=");

        char[] chs = data.toCharArray();
        byte[] source = new byte[byteLength];
        int[] index = new int[4];
        int j = 0;
        for (int i = 0; i < chs.length; i+=4) {
            index[0] = CODES.indexOf(chs[i]);
            index[1] = CODES.indexOf(chs[i+1]);
            index[2] = CODES.indexOf(chs[i+2]);
            index[3] = CODES.indexOf(chs[i+3]);

            source[j++] = (byte) ((index[0]<<2) | (index[1]&0x30)>>4);

            if(index[2] < 64){
                source[j++] = (byte) ((index[1]&0x0F)<<4 | (index[2]&0x3C)>>2);
                if(index[3] < 64){
                    source[j++] = (byte) ((index[2]&0x03)<<6 | (index[3]));
                }
            }
        }

        return source;
    }

    public static String decode2String(String data){
        byte[] source = decode(data);
        if(source == null){
            return null;
        }

        try {
            return new String(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
        String data = "hello world";
        String encode = encode(data.getBytes());
        System.out.println(encode);
        String decode = decode2String(encode);
        System.out.println(decode);
    }
}
