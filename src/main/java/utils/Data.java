package utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class Data {
	static Properties prop = new Properties();
	static FileReader reader = null;
	static FileWriter writer = null;
	private static final String SECRET_KEY = "MySecretKey12345"; // Must be 16 characters long for AES
	static {
		try {
			reader = new FileReader(
					System.getProperty("user.dir") + "\\src\\main\\resources\\Config\\config.properties");

			prop.load(reader);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getProperty(String property) {
		String value = null;
		try {
			value = prop.getProperty(property);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return value;
	}
	
	public static String getAuthproperty(String property) {
		String value = null;
		try {
			value = decrypt(prop.getProperty(property));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return value;
	}
	

	// Decryption method
    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, "UTF-8");
    }

	public static void setProperty(String propertyname, String value) {
		prop.setProperty(propertyname, value);
		/*
		 * try { writer = new FileWriter(System.getProperty("user.dir") +
		 * "\\src\\main\\resources\\Config\\config.properties"); prop.store(writer,
		 * "Booking Id updated"); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
	}

}
