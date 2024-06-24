package com.ruoyi.system.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtils {

    /**
     * 加密算法 - algorithm
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 加密算法 - provider
     */
    private static final String KEY_PROVIDER = "BC";

    /**
     * RSA 加密明文
     */
    private static final int MAX_ENCRYPT_BLOCK = 53;

    /**
     * RSA 解密大小
     */
    private static final int MAX_DECRYPT_BLOCK = 64;

    /**
     * RSA 位数
     */
    private static final int SIZE_LENGTH = 512;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥BASE64
     * @return 公钥
     */
    private static PublicKey getPublicKey(String publicKey) {
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM, KEY_PROVIDER);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes(StandardCharsets.UTF_8)));
            return factory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥Base64
     * @return 私钥
     */
    private static PrivateKey getPrivateKey(String privateKey) {
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM, KEY_PROVIDER);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes(StandardCharsets.UTF_8)));
            return factory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据加密
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @return 加密 Base64
     */
    public static String encryptData(String data, String publicKey) {
        return encryptData(data, getPublicKey(publicKey));
    }

    /**
     * 数据加密
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @return 加密 Base64
     */
    private static String encryptData(String data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypt = data.getBytes(StandardCharsets.UTF_8);
            int length = encrypt.length;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int offset = 0, sub = length - offset;
            while (sub > 0) {
                if (sub > MAX_ENCRYPT_BLOCK) {
                    outputStream.write(cipher.doFinal(encrypt, offset, MAX_ENCRYPT_BLOCK));
                    offset += MAX_ENCRYPT_BLOCK;
                } else {
                    outputStream.write(cipher.doFinal(encrypt, offset, sub));
                    offset = length;
                }
                sub = length - offset;
            }
            byte[] encryptData = outputStream.toByteArray();
            outputStream.close();
            return Base64.encodeBase64String(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密字串
     *
     * @param data       解密数据
     * @param privateKey 私钥
     * @return 接码字串
     */
    public static String decryptData(String data, String privateKey) {
        return decryptData(data, getPrivateKey(privateKey));
    }

    /**
     * 解密字串
     *
     * @param data       解密数据
     * @param privateKey 私钥
     * @return 接码字串
     */
    private static String decryptData(String data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypt = Base64.decodeBase64(data);
            int length = decrypt.length;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int offset = 0, sub = length - offset;
            while (sub > 0) {
                if (sub > MAX_DECRYPT_BLOCK) {
                    outputStream.write(cipher.doFinal(decrypt, offset, MAX_DECRYPT_BLOCK));
                    offset += MAX_DECRYPT_BLOCK;
                } else {
                    outputStream.write(cipher.doFinal(decrypt, offset, sub));
                    offset = length;
                }
                sub = length - offset;
            }
            byte[] decryptData = outputStream.toByteArray();
            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
