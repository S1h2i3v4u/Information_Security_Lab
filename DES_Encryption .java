package Ciphers;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;
import java.util.Base64;

public class DES_Encryption {
    public static void main(String[] args) {
        try {
            // Step 1: Take plaintext input from the user
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the text to be encrypted: ");
            String plainText = sc.nextLine();

            // Step 2: Generate a DES key (56-bit)
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGen.generateKey();

            // Step 3: Create DES Cipher
            Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Step 4: Initialize cipher for encryption
            desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Step 5: Perform encryption
            byte[] textEncrypted = desCipher.doFinal(plainText.getBytes("UTF8"));
            String encryptedText = Base64.getEncoder().encodeToString(textEncrypted);

            System.out.println("\nEncrypted Text: " + encryptedText);

            // Step 6: Initialize cipher for decryption
            desCipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Step 7: Perform decryption
            byte[] textDecrypted = desCipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(textDecrypted, "UTF8");

            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

