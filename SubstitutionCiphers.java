package Ciphers;
import java.util.Scanner;

public class SubstitutionCiphers {

	    // === Caesar Cipher (Shift Cipher) ===
	    public static String caesarEncrypt(String text, int shift) {
	        StringBuilder result = new StringBuilder();
	        shift = shift % 26;
	        for (char c : text.toCharArray()) {
	            if (Character.isUpperCase(c)) {
	                result.append((char) ((c - 'A' + shift) % 26 + 'A'));
	            } else if (Character.isLowerCase(c)) {
	                result.append((char) ((c - 'a' + shift) % 26 + 'a'));
	            } else {
	                result.append(c);
	            }
	        }
	        return result.toString();
	    }

	    public static String caesarDecrypt(String text, int shift) {
	        return caesarEncrypt(text, 26 - (shift % 26));
	    }

	    // === Vigenère Cipher (Polyalphabetic) ===
	    public static String vigenereEncrypt(String text, String key) {
	        StringBuilder result = new StringBuilder();
	        key = key.toLowerCase();
	        int keyIndex = 0;

	        for (char c : text.toCharArray()) {
	            int shift = key.charAt(keyIndex % key.length()) - 'a';
	            if (Character.isUpperCase(c)) {
	                result.append((char) ((c - 'A' + shift) % 26 + 'A'));
	                keyIndex++;
	            } else if (Character.isLowerCase(c)) {
	                result.append((char) ((c - 'a' + shift) % 26 + 'a'));
	                keyIndex++;
	            } else {
	                result.append(c);
	            }
	        }
	        return result.toString();
	    }

	    public static String vigenereDecrypt(String text, String key) {
	        StringBuilder result = new StringBuilder();
	        key = key.toLowerCase();
	        int keyIndex = 0;

	        for (char c : text.toCharArray()) {
	            int shift = key.charAt(keyIndex % key.length()) - 'a';
	            if (Character.isUpperCase(c)) {
	                result.append((char) ((c - 'A' - shift + 26) % 26 + 'A'));
	                keyIndex++;
	            } else if (Character.isLowerCase(c)) {
	                result.append((char) ((c - 'a' - shift + 26) % 26 + 'a'));
	                keyIndex++;
	            } else {
	                result.append(c);
	            }
	        }
	        return result.toString();
	    }

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        int choice;

	        do {
	            System.out.println("\n=== Substitution Ciphers ===");
	            System.out.println("1. Caesar Cipher (Shift Cipher)");
	            System.out.println("2. Vigenère Cipher (Polyalphabetic)");
	            System.out.println("3. Exit");
	            System.out.print("Choose an option (1/2/3): ");
	            choice = sc.nextInt();
	            sc.nextLine(); 

	            switch (choice) {
	                case 1:
	                    System.out.println("\n--- Caesar Cipher ---");
	                    System.out.print("Plaintext : ");
	                    String caesarMsg = sc.nextLine();
	                    System.out.print("Enter shift key (number): ");
	                    int shift = sc.nextInt();
	                    sc.nextLine(); 

	                    String caesarEncrypted = caesarEncrypt(caesarMsg, shift);
	                    String caesarDecrypted = caesarDecrypt(caesarEncrypted, shift);

	                    System.out.println("Encrypted: " + caesarEncrypted);
	                    System.out.println("Decrypted: " + caesarDecrypted);
	                    break;

	                case 2:
	                    System.out.println("\n--- Vigenère Cipher ---");
	                    System.out.print("Enter message: ");
	                    String vigMsg = sc.nextLine();
	                    System.out.print("Enter key (word): ");
	                    String key = sc.nextLine();

	                    String vigEncrypted = vigenereEncrypt(vigMsg, key);
	                    String vigDecrypted = vigenereDecrypt(vigEncrypted, key);

	                    System.out.println("Encrypted: " + vigEncrypted);
	                    System.out.println("Decrypted: " + vigDecrypted);
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
