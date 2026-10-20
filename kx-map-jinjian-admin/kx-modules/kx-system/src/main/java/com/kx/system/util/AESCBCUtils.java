package com.kx.system.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class AESCBCUtils {

	public static final String KEY_ALGORITHM = "AES";

	public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
	// 字符集
	public static final String ENCODING = "UTF-8";
	// 向量
	public static final String IV_SEED = "jORnlnu5buoUZG0M";

	/**
	 * AES加密算法
	 * @param str
	 * @param key
	 * @return
	 */
	public static String encrypt(String str, String key) {
		try {
			if (str == null) {
				System.out.println("AES加密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
//			if (key.length() != 16) {
//				System.out.println("AES加密出错:Key长度不是16位");
//				return null;
//			}
			byte[] raw = key.getBytes(ENCODING);
//			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
//			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
//			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//			byte[] srawt = str.getBytes(ENCODING);
//			int len = srawt.length;
//			/* 计算补空格后的长度 */
//			while (len % 16 != 0)
//				len++;
//			byte[] sraw = new byte[len];
//			/* 在最后空格 */
//			for (int i = 0; i < len; ++i) {
//				if (i < srawt.length) {
//					sraw[i] = srawt[i];
//				} else {
//					sraw[i] = 32;
//				}
//			}
//			byte[] encrypted = cipher.doFinal(sraw);



			//设置加密算法，生成秘钥
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			// "算法/模式/补码方式"
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			//偏移
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			//选择加密
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			//根据待加密内容生成字节数组
			byte[] encrypted = cipher.doFinal(str.getBytes(ENCODING));
			//返回base64字符串


			return formatString(new String(Base64.encodeBase64(encrypted), "UTF-8"));
		} catch (Exception ex) {
			log.error("AES加密出错：" + ex.getMessage());
			return null;
		}
	}

	/**
	 * AES解密算法
	 * @param str
	 * @param key
	 * @return
	 */
	public static String decrypt(String str, String key) {
		try {
			// 判断Key是否正确
			if (key == null) {
				System.out.println("AES解密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
//			if (key.length() != 16) {
//				System.out.println("AES解密出错：Key长度不是16位");
//				return null;
//			}
//			byte[] raw = key.getBytes(ENCODING);
//			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
//			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//			byte[] bytes = Base64.decodeBase64(str.getBytes("UTF-8"));
//			bytes = cipher.doFinal(bytes);
//			return new String(bytes, ENCODING);


			//对密码进行编码
			byte[] raw = key.getBytes(ENCODING);
			//设置解密算法，生成秘钥
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			//偏移
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			// "算法/模式/补码方式"
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			//选择解密
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			//先进行Base64解码
			byte[] bytes = Base64.decodeBase64(str.getBytes("UTF-8"));

			//根据待解密内容进行解密
			byte[] decrypted = cipher.doFinal(bytes);
			//将字节数组转成字符串
			return new String(decrypted, ENCODING);

		} catch (Exception ex) {
			log.error("AES解密出错：" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	private static String formatString(String sourceStr) {
		if (sourceStr == null) {
			return null;
		}
		return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
	}

	public static String generateAESKey(int keySize) throws Exception {
		KeyGenerator aes = KeyGenerator.getInstance("AES");
		aes.init(keySize);
		byte[] encoded = aes.generateKey().getEncoded();
		return java.util.Base64.getEncoder().encodeToString(encoded);
	}




}
