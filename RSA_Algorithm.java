package Ciphers;

import java.math.BigInteger;
import java.util.Scanner;

public class RSA_Algorithm {

    // Function to calculate gcd
    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Step 1: Choose two prime numbers
        int p = 3;
        int q = 11;

        // Step 2: Compute n = p * q
        int n = p * q;

        // Step 3: Compute φ(n) = (p - 1) * (q - 1)
        int phi = (p - 1) * (q - 1);

        // Step 4: Choose e such that gcd(e, phi) = 1
        int e = 2;
        while (gcd(e, phi) != 1) {
            e++;
        }

        // Step 5: Compute d such that (d * e) % phi = 1
        int d = 0;
        for (int i = 1; i < phi; i++) {
            if ((e * i) % phi == 1) {
                d = i;
                break;
            }
        }

        // Display public and private keys
        System.out.println("Public Key: {" + e + ", " + n + "}");
        System.out.println("Private Key: {" + d + ", " + n + "}");

        // Step 6: Input message
        System.out.print("\nEnter a number message to encrypt: ");
        int msg = sc.nextInt();

        System.out.println("Original Message: " + msg);

        // Step 7: Encryption -> C = (msg^e) mod n using BigInteger
        BigInteger M = BigInteger.valueOf(msg);
        BigInteger N = BigInteger.valueOf(n);
        BigInteger ciphertext = M.pow(e).mod(N);

        System.out.println("Encrypted Message: " + ciphertext);

        // Step 8: Decryption -> M = (C^d) mod n
        BigInteger decrypted = ciphertext.pow(d).mod(N);

        System.out.println("Decrypted Message: " + decrypted);

        sc.close();
    }
}
