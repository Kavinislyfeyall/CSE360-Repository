
//By: Kavinkumaar Jaganathan, Group M11
// CSE360 Effort Logger Deliverable
// Encryption Functionality Class


// all imported libraries that will be made use of in order to gain the encryption
//and decryption functionality that is wanted by our client
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;
import java.util.Base64;

//Class that can be instantiated in order to gain access to encryption and decryption functionalities 
//that are needed for the effort logger
public class Encryption
{
    private static SecretKey secKey;
    private static byte[] keyB;
    
    //psuedo constructor class that initialized the secret key that will be sued in order to
    //encrypt and decrypt all of the data within the excel file.
    public void Encryption(String str)
    {
        MessageDigest msgDg;
        keyB = str.getBytes(StandardCharsets.UTF_8);
        try
        {
            msgDg = MessageDigest.getInstance("SHA-1");
            keyB = msgDg.digest(keyB);
            keyB = Arrays.copyOf(keyB, 16);
            secKey = new SecretKeySpec(keyB, "AES");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    // A function that takes in a piece of data in the form of a string and returns an encrypted version of the data 
    //based off of the secret key. This addresses our client's need for security by making sure that the user has the option to encrypt all
    //of the data that they generate, so that even if a hacker gains access to the excel file's password protection, they will not
    //be able to decipher the data inside of the file at all. This also eliminates the risk of the file storage not being safe enough. Because even of the storage
    //password is bypassed, we have another layer of security on top of this.
     public String encryptData(String dataEnc, String sK) {
        try {
            Encryption(sK);
            Cipher encCyph = Cipher.getInstance("AES");
            encCyph.init(Cipher.ENCRYPT_MODE, secKey);
            //byte[] encD = decCyph.doFinal(Base64.getDecoder().decode(dataEnc));
            String retV = Base64.getEncoder().encodeToString(encCyph.doFinal(dataEnc.getBytes("UTF-8")));
            return retV;
        } catch (Exception e) {
            System.out.println("Encryption Error!");
        }
        return "";
    }
    //a function that taken in a piece of data in the form of a string and returns a decrypted version of the data
    //based off of the secret key that we initialized.
    public String decryptData(String dataDec, String sK) {
        try {
            Encryption(sK);
            Cipher decCyph = Cipher.getInstance("AES");
            decCyph.init(Cipher.DECRYPT_MODE, secKey);
            byte[] decD = decCyph.doFinal(Base64.getDecoder().decode(dataDec));
            String retV = new String(decD);
            return retV;
        } catch (Exception e) {
            System.out.println("Decryption Error!");
        }
        return "";
    }

    
}
