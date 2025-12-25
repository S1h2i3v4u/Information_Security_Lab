package Ciphers;

import java.util.*;

public class TranspositionCiphers {

    // === 1. Rail Fence Cipher ===
    public static String railFenceEncrypt(String text, int key) {
        if (key <= 1) return text; // no encryption if key = 1
        StringBuilder[] rail = new StringBuilder[key];
        for (int i = 0; i < key; i++) rail[i] = new StringBuilder();

        int row = 0;
        boolean down = true;
        for (char c : text.toCharArray()) {
            rail[row].append(c);
            if (row == 0) down = true;
            else if (row == key - 1) down = false;
            row += down ? 1 : -1;
        }

        StringBuilder encrypted = new StringBuilder();
        for (StringBuilder sb : rail) encrypted.append(sb);
        return encrypted.toString();
    }

    public static String railFenceDecrypt(String cipher, int key) {
        if (key <= 1) return cipher;
        boolean[][] marker = new boolean[key][cipher.length()];
        int row = 0;
        boolean down = true;

        // mark positions
        for (int i = 0; i < cipher.length(); i++) {
            marker[row][i] = true;
            if (row == 0) down = true;
            else if (row == key - 1) down = false;
            row += down ? 1 : -1;
        }

        // fill letters
        char[][] rail = new char[key][cipher.length()];
        int index = 0;
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < cipher.length(); j++) {
                if (marker[i][j] && index < cipher.length()) {
                    rail[i][j] = cipher.charAt(index++);
                }
            }
        }

        // read zig-zag
        StringBuilder decrypted = new StringBuilder();
        row = 0; down = true;
        for (int i = 0; i < cipher.length(); i++) {
            decrypted.append(rail[row][i]);
            if (row == 0) down = true;
            else if (row == key - 1) down = false;
            row += down ? 1 : -1;
        }
        return decrypted.toString();
    }

    // === 2. Row-Column Transposition ===
    public static String rowColumnEncrypt(String text, int key) {
        int length = text.length();
        int rows = (int) Math.ceil((double) length / key);
        char[][] matrix = new char[rows][key];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key; j++) {
                if (index < length) matrix[i][j] = text.charAt(index++);
                else matrix[i][j] = 'X'; // padding
            }
        }

        StringBuilder encrypted = new StringBuilder();
        for (int j = 0; j < key; j++) {
            for (int i = 0; i < rows; i++) {
                encrypted.append(matrix[i][j]);
            }
        }
        return encrypted.toString();
    }

    public static String rowColumnDecrypt(String cipher, int key) {
        int length = cipher.length();
        int rows = (int) Math.ceil((double) length / key);
        char[][] matrix = new char[rows][key];

        int index = 0;
        for (int j = 0; j < key; j++) {
            for (int i = 0; i < rows; i++) {
                if (index < length) matrix[i][j] = cipher.charAt(index++);
            }
        }

        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key; j++) {
                decrypted.append(matrix[i][j]);
            }
        }

        return decrypted.toString().replaceAll("X+$", ""); // remove padding
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Transposition Ciphers ===");
            System.out.println("1. Rail Fence Cipher");
            System.out.println("2. Row-Column Transposition");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1/2/3): ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Rail Fence Cipher ---");
                    System.out.print("Enter message: ");
                    String railMsg = sc.nextLine();
                    System.out.print("Enter key (number of rails): ");
                    int railKey = sc.nextInt();
                    sc.nextLine();

                    String railEncrypted = railFenceEncrypt(railMsg, railKey);
                    String railDecrypted = railFenceDecrypt(railEncrypted, railKey);

                    System.out.println("Encrypted: " + railEncrypted);
                    System.out.println("Decrypted: " + railDecrypted);
                    break;

                case 2:
                    System.out.println("\n--- Row-Column Transposition ---");
                    System.out.print("Enter message: ");
                    String rowMsg = sc.nextLine();
                    System.out.print("Enter key (number of columns): ");
                    int colKey = sc.nextInt();
                    sc.nextLine();

                    String rowEncrypted = rowColumnEncrypt(rowMsg, colKey);
                    String rowDecrypted = rowColumnDecrypt(rowEncrypted, colKey);

                    System.out.println("Encrypted: " + rowEncrypted);
                    System.out.println("Decrypted: " + rowDecrypted);
                    break;

                case 3:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1, 2, or 3.");
            }
        } while (choice != 3);

        sc.close();
    }
}
