package todoList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;


public class EncryptDecrypt
{



    public static String encrypt(String token, SecretKey secretKey) throws Exception {

        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] encryptedvalue = c.doFinal(token.getBytes());
        String result = Base64.getEncoder().encodeToString(encryptedvalue);
        return result;
    }

    public static String decrypt ( String encryptedValue,SecretKey secretKey ) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] decryptedBytes = c.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedBytes);
    }

}


