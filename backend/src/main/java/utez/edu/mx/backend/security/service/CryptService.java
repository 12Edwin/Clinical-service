package utez.edu.mx.backend.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class CryptService {

    @Value("${crypt.secret}")
    private String secretKey;

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = new byte[16];

    private SecretKeySpec generateSecretKeyFromString() throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(Object data) throws Exception {
        String plaintext = convertToString(data);
        SecretKey key = generateSecretKeyFromString();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        String base64Encoded = Base64.getEncoder().encodeToString(encryptedBytes);
        return URLEncoder.encode(base64Encoded, StandardCharsets.UTF_8);
    }

    public String decrypt(String encodedEncryptedText) throws Exception {
        String encryptedText = URLDecoder.decode(encodedEncryptedText, StandardCharsets.UTF_8);
        SecretKey key = generateSecretKeyFromString();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
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