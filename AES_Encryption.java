package Ciphers;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.security.SecureRandom;
import java.util.Scanner;

public class AES_Encryption {
    public static void main(String[] args) {
        try {
            // Step 1: Input plaintext
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter text to be encrypted: ");
            String plainText = sc.nextLine();

            // Step 2: Generate AES key (128-bit)
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // can be 128, 192, or 256 bits
            SecretKey secretKey = keyGen.generateKey();

            // Step 3: Generate Initialization Vector (IV)
            byte[] iv = new byte[16]; // 128-bit IV for AES
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Step 4: Initialize Cipher for encryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Step 5: Encrypt plaintext
            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
            String encryptedText = Base64.getEncoder().encodeToString(encrypted);
            String encodedIV = Base64.getEncoder().encodeToString(iv);

            // Display results
            System.out.println("\nGenerated AES Key (Base64): " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            System.out.println("Initialization Vector (Base64): " + encodedIV);
            System.out.println("Encrypted Text: " + encryptedText);

            // Step 6: Decrypt the ciphertext
            Cipher decipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decrypted = decipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decrypted, "UTF-8");

            // Step 7: Display decrypted text
            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
