package utez.edu.mx.backend.security.service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;

@Service
@NoArgsConstructor
@Component
@Setter
public class CryptService {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = new byte[16];

    private SecretKeySpec generateSecretKeyFromString() throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        String secret = resourceBundle.getString("crypt.secret");
        byte[] keyBytes = sha.digest(secret.getBytes(StandardCharsets.UTF_8));
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(Object data) throws UnsupportedEncodingException {
        try {
            String plaintext = convertToString(data);
            SecretKey key = generateSecretKeyFromString();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            IvParameterSpec ivParams = new IvParameterSpec(IV);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            throw new UnsupportedEncodingException();
        }
    }

    public String decrypt(String encodedEncryptedText) throws UnsupportedEncodingException{
        try {
            SecretKey key = generateSecretKeyFromString();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            IvParameterSpec ivParams = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
            byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encodedEncryptedText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new UnsupportedEncodingException();
        }
    }

    private String convertToString(Object data) {
        if (data instanceof Number) {
            return String.valueOf(data);
        } else if (data instanceof String) {
            return (String) data;
        } else if(data != null){
            return String.valueOf(data);
        }else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }
}