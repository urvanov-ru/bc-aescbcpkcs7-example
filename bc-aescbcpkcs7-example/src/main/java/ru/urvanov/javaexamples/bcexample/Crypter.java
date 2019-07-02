package ru.urvanov.javaexamples.bcexample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 * <a href="https://urvanov.ru">https://urvanov.ru</a>
 *
 */
public class Crypter {

    private static final int BUFFER_SIZE = 1024;

    private byte[] aesKey;

    private byte[] hmacShaKey;

    public Crypter(byte[] aesKey, byte[] hmacShaKey) {
        this.aesKey = aesKey;
        this.hmacShaKey = hmacShaKey;
    }

    public void decrypt(InputStream inputStream, OutputStream outputStream)
            throws BcExampleException, IOException {
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                new CBCBlockCipher(new AESEngine()), new PKCS7Padding());

        HMac hmac;
        hmac = new HMac(new SHA512Digest());
        hmac.init(new KeyParameter(hmacShaKey));

        byte[] iv = new byte[cipher.getBlockSize()];
        int readedBytes;
        int offset = 0;
        int limit = iv.length;
        while ((limit - offset > 0) &&
                ((readedBytes = inputStream.read(iv, offset, limit)) != -1)) {
            offset += readedBytes;
            limit = iv.length - offset;
        }
        if (limit > 0) {
            throw new BcExampleException("Incorrect input stream length.");
        }

        cipher.init(false, new ParametersWithIV(
                new KeyParameter(aesKey), iv, 0, iv.length));

        byte[] buffer = new byte[BUFFER_SIZE + hmac.getMacSize()];
        byte[] outputBuffer = new byte[BUFFER_SIZE];
        byte[] signBuffer = new byte[hmac.getMacSize()];
        offset = 0;
        limit = buffer.length;
        int signOffset = 0;
        while ((readedBytes = inputStream.read(buffer, offset, limit)) != -1) {
            if (readedBytes > hmac.getMacSize()) {
                int processBytes = readedBytes - hmac.getMacSize();
                int outputLen = cipher.processBytes(
                        buffer, 0, processBytes, outputBuffer, 0);
                hmac.update(buffer, 0, processBytes);
                outputStream.write(outputBuffer, 0, outputLen);
                signOffset = processBytes;
                offset = 0;
                limit = buffer.length;
            } else {
                offset += readedBytes;
                limit = buffer.length - readedBytes;
            }
        }

        try {
            int outputLen = cipher.doFinal(outputBuffer, 0);
            outputStream.write(outputBuffer, 0, outputLen);
        } catch (CryptoException ce) {
            throw new BcExampleException(ce);
        }

        byte[] calculatedSign = new byte[hmac.getMacSize()];
        byte[] hmacSign = Arrays.copyOfRange(
                buffer, signOffset, signOffset + hmac.getMacSize());
        hmac.doFinal(calculatedSign, 0);
        if (!Arrays.equals(hmacSign, calculatedSign)) {
            throw new SignException();
        }
        Arrays.fill(buffer, (byte) 0);
        Arrays.fill(outputBuffer, (byte) 0);
        Arrays.fill(signBuffer, (byte) 0);
        Arrays.fill(calculatedSign, (byte) 0);
        Arrays.fill(hmacSign, (byte) 0);
    }

    public void encrypt(InputStream inputStream, OutputStream outputStream)
            throws BcExampleException, IOException {
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                new CBCBlockCipher(new AESEngine()), new PKCS7Padding());

        HMac hmac = new HMac(new SHA512Digest());
        hmac.init(new KeyParameter(hmacShaKey));

        byte[] iv = new byte[cipher.getBlockSize()];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        cipher.init(true, new ParametersWithIV(new KeyParameter(aesKey), iv));

        outputStream.write(iv);

        byte[] buffer = new byte[BUFFER_SIZE];
        byte[] outputBuffer = new byte[BUFFER_SIZE];
        byte[] signBuffer = new byte[hmac.getMacSize()];
        int readedBytes;
        while ((readedBytes = inputStream.read(buffer)) != -1) {
            int outputLen = cipher.processBytes(
                    buffer, 0, readedBytes, outputBuffer, 0);
            outputStream.write(outputBuffer, 0, outputLen);
            hmac.update(outputBuffer, 0, outputLen);
        }
        try {
            int outputLen = cipher.doFinal(outputBuffer, 0);
            outputStream.write(outputBuffer, 0, outputLen);
            hmac.update(outputBuffer, 0, outputLen);
            hmac.doFinal(signBuffer, 0);
            outputStream.write(signBuffer, 0, hmac.getMacSize());
        } catch (CryptoException ce) {
            throw new BcExampleException(ce);
        }

        Arrays.fill(iv, (byte) 0);
        Arrays.fill(buffer, (byte) 0);
        Arrays.fill(outputBuffer, (byte) 0);
        Arrays.fill(signBuffer, (byte) 0);
    }
}
