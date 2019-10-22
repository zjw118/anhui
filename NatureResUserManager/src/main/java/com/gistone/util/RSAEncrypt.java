package com.gistone.util;

/**
 * @ClassName RSAEncrypt
 * @Description TODO
 * @Author xxh
 * @Date 2019/5/28 18:10
 * @Version 1.0
 **/

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.net.URLDecoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

    static String gy="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDNhCDiLy0CG5jYN/HgCTj19DplFbL+Mh5dd8q9lmcSzVqQGK4vQzzvET1zS+zKUTNPqzIovPlqTKbZA1E5SDJSc0u3suQXIHPJ5V2EXfNf7d3atl6NynwAwiFtPh65bNTHWy0dkM4j5j9vo9rx4G9bb2qTXu81nSh6e5Zm63WTQIDAQAB";
   static String sy="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIM2EIOIvLQIbmNg38eAJOPX0OmUVsv4yHl13yr2WZxLNWpAYri9DPO8RPXNL7MpRM0+rMii8+WpMptkDUTlIMlJzS7ey5Bcgc8nlXYRd81/t3dq2Xo3KfADCIW0+Hrls1MdbLR2QziPmP2+j2vHgb1tvapNe7zWdKHp7lmbrdZNAgMBAAECgYASc4fe91uR9Z2QXA8b/ukxISg/sJyEEngenHTFld8EG49nYaaAVBV+MPuBlVkf2K3ULvwdOs0RfrmfIHK9PXten2IH/wn5ZfCgaWY4pslVyqthZhNt7+0FnYGQm7gyNu77yS+CD9gCl1posneaacPtSym6PLMQqi92Zbvz0xpwAQJBAMG8kwCfazdO51mSxHcx0JX0MgCkUHWOqDtOKRMwo34s9ITk00bI3X0Qzp0LMSBZVz3Wq3IS0r24OnZSHaPUuzsCQQCtYUrzAbBCQPOGtxTL/vjNSSh8S7fioq6ZTmoKbMmmmktB/q2l3WYzgRJULMKdbReLtRFYX6eBnw35UqKpOswXAkEArXUeFN+nCgT+RAeRGbsjKy9RGZwEYcyROU/4nLLenti3MMkDlQvAqvpCUv8zQ+hZoQczx1WtE6n/xfItkYKlAwJAXlOLWi/1++WoWAdtChr6s3z0yMLNFUEaqo9tw6QyBTD2dr4fLRkJzEWRejgr3UgHwltNR34q/KtTB+z8UAOMGwJBAIsSaRO1Erve1cofrKAw3LehpiqrytrbpqbnyUC9JzZ1wrgO5tVA+XEz55pcGB2ISgFxjf88vgJ7DrlcX3zTCSA=";
    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        //String message = "df723820";
        //System.out.println("随机生成的公钥为:" + keyMap.get(0));
        //System.out.println("随机生成的私钥为:" + keyMap.get(1));
        //String messageEn = encrypt(message,gy);
        //System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt("apOoHD4PwG37UqhaDh6gDvnUhtkUJyMDeQjy1QQKZLVG/y%2By4oi%2B%2B3en1j4V6L5RuU2hGvJVlI%2BZuWVbT/D8p%2BMmEOJ%2B5M4Ijuul9TG4w/2J/8%2BSAISmykEeD%2BOPedEoYZZNl%2BhKnuf3UjmASfaqMVxxMdnBjuYEuBoOUi/4fa0=",sy);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer, String> genKeyPair()   {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
        return keyMap;
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //privateKey= sy;
        str= URLDecoder.decode(str,"UTF-8");
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }



}


