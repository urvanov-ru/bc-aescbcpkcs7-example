package ru.urvanov.javaexamples.bcexample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.Test;

public class CrypterTest {

    @Test
    public void encrypt() throws BcExampleException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] aesKey = secretKey.getEncoded();
        byte[] hmacShaKey = "hmacShaKey".getBytes(StandardCharsets.UTF_8);
        byte[] message = "myMessage".getBytes(StandardCharsets.UTF_8);
        Crypter crypter = new Crypter(aesKey, hmacShaKey);
        ByteArrayOutputStream encryptedStream = new ByteArrayOutputStream();
        crypter.encrypt(new ByteArrayInputStream(message), encryptedStream);
        ByteArrayOutputStream decryptedStream = new ByteArrayOutputStream();
        crypter.decrypt(new ByteArrayInputStream(encryptedStream.toByteArray()), decryptedStream);
        System.out.println("Decrypted: " + new String(decryptedStream.toByteArray(), StandardCharsets.UTF_8));
    }
}
