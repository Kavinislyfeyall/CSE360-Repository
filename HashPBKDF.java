import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import java.sql.*;

public class HashPBKDF {
    //Set Variables to be used later
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    //This will hash the data and store it into a file.
    public void hashDataWrite(String Input, String salt, String Que1, String Que2, String Que3) {

        //String salt = "1234";
        int iterations = 10000;
        int keyLength = 512;
        char[] passwordChars = Input.toCharArray();
        byte[] saltBytes = salt.getBytes();

        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
        String hashedString = bytesToHex(hashedBytes);

        //Creates a file that will store the hashed string into it.
        PrintWriter pw = null;
        try {
            File file = new File("UserData.txt");
            file.delete();
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            pw.println(Que1);
            pw.println(Que2);
            pw.println(Que3);
            pw.println(hashedString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        //System.out.println(hashedString);
    }

    //This will hash the data and check if it matches the file.
    public boolean hashDataRead(String Input, String salt) {

        //String salt = "1234";
        int iterations = 10000;
        int keyLength = 512;
        char[] passwordChars = Input.toCharArray();
        byte[] saltBytes = salt.getBytes();

        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
        String hashedString = bytesToHex(hashedBytes);

        //Creates a file that will store the hashed string into it.
        Scanner myReader;
        try {
            File file = new File("UserData.txt");
            myReader = new Scanner(file);
            myReader.nextLine();
            myReader.nextLine();
            myReader.nextLine();
            if(myReader.nextLine().matches(hashedString)){
                myReader.close();
                return true;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    //Convert the byte into Hex characters for String.
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    //Hash the password using both it and the salt.
    public static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            //Create a hash using PBKDF2 algorithm.
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
}
