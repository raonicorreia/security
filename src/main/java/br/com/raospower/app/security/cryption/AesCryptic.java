package br.com.raospower.app.security.cryption;

import br.com.raospower.app.util.PropertiesKeys;
import br.com.raospower.app.util.SecurityAppContext;
import org.springframework.core.env.Environment;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesCryptic {

    private static final AesCryptic INSTANCE = new AesCryptic();

    private static final String ALGORITHM = SecurityAppContext.getBean(Environment.class).getProperty(PropertiesKeys.ENCRYPTION_ALGORITHM);

    private static final String KEY_ENCRYPTION = SecurityAppContext.getBean(Environment.class).getProperty(PropertiesKeys.ENCRYPTION_KEY);

    private static final String IV = SecurityAppContext.getBean(Environment.class).getProperty(PropertiesKeys.ENCRYPTION_IV);

    private AesCryptic() {
    }

    public static AesCryptic getInstance() {
        return INSTANCE;
    }

    public static String encrypt(String data) throws CryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(KEY_ENCRYPTION.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new CryptionException(e);
        }
    }

    public static String decrypt(String data) throws CryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(KEY_ENCRYPTION.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new CryptionException(e);
        }
    }
}
