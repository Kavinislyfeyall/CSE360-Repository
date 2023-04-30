
//By: Kavinkumaar Jaganathan, Group M11
// CSE360 Effort Logger Deliverable
// Encryption Functionality Class

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


public class Encryption
{
    private static SecretKey secKey;
    private static byte[] keyB;
    
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
