package br.com.raospower.app.security.cryption;

import br.com.raospower.app.util.PropertiesKeys;
import br.com.raospower.app.util.SecurityAppContext;
import org.springframework.core.env.Environment;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesCryption {

    private static final AesCryption INSTANCE = new AesCryption();

    private static final String ALGORITHM = SecurityAppContext.getBean(Environment.class).getProperty(PropertiesKeys.CRYPTION_ALGORITHM);

    private static final String DIGITS_HEXADECIMAL = "0123456789ABCDEF";

    private static final String HEXADECIMAL_KEY = SecurityAppContext.getBean(Environment.class).getProperty(PropertiesKeys.CRYPTION_KEY);

    private byte[] bytes;

    public static AesCryption getInstance() {
        return INSTANCE;
    }

    public String encrypt(String value) throws CryptionException {
        try {
            this.bytes = asByte(HEXADECIMAL_KEY);
            SecretKeySpec skey = new SecretKeySpec(this.bytes, ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(1, skey);
            byte[] msg = c.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(msg);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptionException(e);
        }
    }

    public String decrypt(String value) throws CryptionException {
        try {
            this.bytes = asByte(HEXADECIMAL_KEY);
            SecretKeySpec skey = new SecretKeySpec(this.bytes, ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(2, skey);
            byte[] decode = Base64.getDecoder().decode(value);
            byte[] msg = c.doFinal(decode);
            return new String(msg);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptionException(e);
        }
    }

    public static String asHex(byte[] b) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            int j = b[i] & 0xFF;
            buf.append(DIGITS_HEXADECIMAL.charAt(j / 16));
            buf.append(DIGITS_HEXADECIMAL.charAt(j % 16));
        }
        return buf.toString();
    }

    public static byte[] asByte(String hexa) throws IllegalArgumentException {
        if (hexa.length() % 2 != 0)
            throw new IllegalArgumentException("String hexa inv");
        byte[] b = new byte[hexa.length() / 2];
        for (int i = 0, j = 0; i < b.length; i++, j += 2)
            b[i] = Integer.valueOf(hexa.substring(j, j + 2), 16).byteValue();
        return b;
    }
}
