package com.lsy.test.redis.utils;

import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
	 * 全局数组
	 */
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 返回形式为数字跟字符串
     * @param bByte
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * 返回形式只为数字
     * @param bByte
     * @return
     */
    public static String byteToNum(byte bByte) {
        int iRet = bByte;
        /*System.out.println("iRet1=" + iRet);*/
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    /**
     * 转换字节数组为16进制字串
     * @param bByte
     * @return
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 获取MD5,传入一个字符串
     * @param strObj
     * @return
     */
    public static String getMD5(String strObj) {

    	if(StringUtils.isEmpty(strObj == null ? null : strObj.trim())){
    		return null;
    	}
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return resultString;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("Default Charset in Use=" + getDefaultCharSet());
        System.out.println(getMD5("赵秀秀"));
    	//System.out.println(URLDecoder.decode("head=%7B%22serialID%22%3A%22U00000000120160101b9c59754b28c11e59f22ba0be0483c18%22%2C%22func%22%3A%22SCvld0008%22%2C%22accessToken%22%3A%22004fad287bb545668cc62f0ad7676cb7f7746b4dd6d3a51de90570568dd52818%22%7D&body=%7B%22validType%22%3A%22ma%22%2C%22name%22%3A%2248d8693ef8097542f3b296a503e9977b%22%2C%22encryptType%22%3A1%2C%22idType%22%3A%220101%22%2C%22idCard%22%3A%225ee01b335441a13b7530513a15a5b0cb%22%2C%22prefix%22%3A%22fc221309746013ac554571fbd180e1c8%22%2C%22mobile%22%3A%2268521ea26e750f74ebfcb880fb1c9dcf%22%2C%22idCardSuffix%22%3A%220f678652a68ea1a6033c249a22c76847%22%7D&hashValue=f3cafe0f8f32759ed8e21fd43e1eadefc363f1a395ae42638e9070ed57e46b93&nocache=1", "UTF-8"));
	}
    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }
}
