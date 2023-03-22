package computers.manage.config;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import computers.manage.exception.SystemOperationException;

public class MyCipher {

    private char[] key;
    private byte[] salt;
    private final int iterationCount = 40000;
    private final int keyLength = 128;
    private SecretKeySpec secretKey;
	
   
    public MyCipher(String key, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.key = key.toCharArray();
		this.salt = salt.getBytes();
		this.secretKey = createSecretKey();
	}

	private SecretKeySpec createSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(this.key, this.salt, this.iterationCount, this.keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public String encrypt(String property) {
        Cipher pbeCipher;
        byte[] cryptoText = null;
		byte[] iv = null;
		try {
			pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        pbeCipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
	        AlgorithmParameters parameters = pbeCipher.getParameters();
	        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
	        cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
	        iv = ivParameterSpec.getIV();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException | InvalidParameterSpecException e) {
			new SystemOperationException("Blad podczas szyfrowania.", e);
		}
		return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String decrypt(String value){
        String iv = value.split(":")[0];
        String property = value.split(":")[1];
        Cipher pbeCipher;
        String returnValue = null;
		try {
			pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        pbeCipher.init(Cipher.DECRYPT_MODE, this.secretKey, new IvParameterSpec(base64Decode(iv)));
	        returnValue = new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IOException | IllegalBlockSizeException | BadPaddingException e) {
			new SystemOperationException("Blad podczas deszyfrowania.", e);
		}

        return returnValue;
    }

    private byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
    
    
}
