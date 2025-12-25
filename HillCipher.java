package Ciphers;
import java.util.*;

public class HillCipher {
	
	    private static int[][] keyMatrix = new int[2][2];
	    private static int[][] inverseMatrix = new int[2][2];

	    // Function to calculate determinant
	    private static int determinant(int[][] matrix) {
	        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
	    }

	    // Function to find modular inverse of determinant
	    private static int modInverse(int a, int m) {
	        a = a % m;
	        for (int x = 1; x < m; x++) {
	            if ((a * x) % m == 1) return x;
	        }
	        return -1;
	    }

	    // Function to calculate inverse matrix modulo 26
	    private static boolean findInverseMatrix() {
	        int det = determinant(keyMatrix);
	        if (det < 0) det += 26;

	        int detInv = modInverse(det, 26);
	        if (detInv == -1) return false;

	        inverseMatrix[0][0] = ( keyMatrix[1][1] * detInv) % 26;
	        inverseMatrix[1][1] = ( keyMatrix[0][0] * detInv) % 26;
	        inverseMatrix[0][1] = (-keyMatrix[0][1] * detInv) % 26;
	        inverseMatrix[1][0] = (-keyMatrix[1][0] * detInv) % 26;

	        for (int i = 0; i < 2; i++) {
	            for (int j = 0; j < 2; j++) {
	                if (inverseMatrix[i][j] < 0)
	                    inverseMatrix[i][j] += 26;
	            }
	        }
	        return true;
	    }

	    // Encrypt function
	    private static String encrypt(String plaintext) {
	        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
	        if (plaintext.length() % 2 != 0) plaintext += "X";

	        StringBuilder ciphertext = new StringBuilder();
	        for (int i = 0; i < plaintext.length(); i += 2) {
	            int[] vector = {plaintext.charAt(i) - 'A', plaintext.charAt(i + 1) - 'A'};
	            int[] result = new int[2];
	            for (int row = 0; row < 2; row++) {
	                result[row] = (keyMatrix[row][0] * vector[0] + keyMatrix[row][1] * vector[1]) % 26;
	            }
	            ciphertext.append((char) (result[0] + 'A'));
	            ciphertext.append((char) (result[1] + 'A'));
	        }
	        return ciphertext.toString();
	    }

	    // Decrypt function
	    private static String decrypt(String ciphertext) {
	        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
	        StringBuilder plaintext = new StringBuilder();
	        for (int i = 0; i < ciphertext.length(); i += 2) {
	            int[] vector = {ciphertext.charAt(i) - 'A', ciphertext.charAt(i + 1) - 'A'};
	            int[] result = new int[2];
	            for (int row = 0; row < 2; row++) {
	                result[row] = (inverseMatrix[row][0] * vector[0] + inverseMatrix[row][1] * vector[1]) % 26;
	            }
	            plaintext.append((char) (result[0] + 'A'));
	            plaintext.append((char) (result[1] + 'A'));
	        }
	        return plaintext.toString();
	    }

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.println("=== Hill Cipher (2x2) ===");
	        System.out.println("Enter 2x2 key matrix (row by row, numbers 0-25):");
	        for (int i = 0; i < 2; i++) {
	            for (int j = 0; j < 2; j++) {
	                keyMatrix[i][j] = sc.nextInt();
	            }
	        }

	        if (!findInverseMatrix()) {
	            System.out.println("Invalid key matrix! Determinant has no modular inverse.");
	            return;
	        }

	        sc.nextLine(); 
	        System.out.print("Enter plaintext: ");
	        String plaintext = sc.nextLine();

	        String cipher = encrypt(plaintext);
	        System.out.println("Encrypted: " + cipher);

	        String decrypted = decrypt(cipher);
	        System.out.println("Decrypted: " + decrypted);

	        sc.close();
	    }
	}
