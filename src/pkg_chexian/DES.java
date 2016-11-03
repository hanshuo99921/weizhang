package pkg_chexian;
import com.sun.crypto.provider.SunJCE;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DES
{

 private static String strDefaultKey = "hnzt";
 private Cipher encryptCipher;
 private Cipher decryptCipher;

 public static String byteArr2HexStr(byte arrB[])
     throws Exception
 {
     int iLen = arrB.length;
     StringBuffer sb = new StringBuffer(iLen * 2);
     for(int i = 0; i < iLen; i++)
     {
         int intTmp;
         for(intTmp = arrB[i]; intTmp < 0; intTmp += 256);
         if(intTmp < 16)
             sb.append("0");
         sb.append(Integer.toString(intTmp, 16));
     }

     return sb.toString();
 }

 public static byte[] hexStr2ByteArr(String strIn)
     throws Exception
 {
     byte arrB[] = strIn.getBytes();
     int iLen = arrB.length;
     byte arrOut[] = new byte[iLen / 2];
     for(int i = 0; i < iLen; i += 2)
     {
         String strTmp = new String(arrB, i, 2);
         arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
     }

     return arrOut;
 }

 public DES()
     throws Exception
 {
     this(strDefaultKey);
 }

 public DES(String strKey)
     throws Exception
 {
     encryptCipher = null;
     decryptCipher = null;
     Security.addProvider(new SunJCE());
     Key key = getKey(strKey.getBytes());
     encryptCipher = Cipher.getInstance("DES");
     encryptCipher.init(1, key);
     decryptCipher = Cipher.getInstance("DES");
     decryptCipher.init(2, key);
 }

 public byte[] encrypt(byte arrB[])
     throws Exception
 {
     return encryptCipher.doFinal(arrB);
 }

 public String encrypt(String strIn)
     throws Exception
 {
     return byteArr2HexStr(encrypt(strIn.getBytes()));
 }

 public byte[] decrypt(byte arrB[])
     throws Exception
 {
     return decryptCipher.doFinal(arrB);
 }

 public String decrypt(String strIn)
     throws Exception
 {
     return new String(decrypt(hexStr2ByteArr(strIn)));
 }

 private Key getKey(byte arrBTmp[])
     throws Exception
 {
     byte arrB[] = new byte[8];
     for(int i = 0; i < arrBTmp.length && i < arrB.length; i++)
         arrB[i] = arrBTmp[i];

     Key key = new SecretKeySpec(arrB, "DES");
     return key;
 }

 public static void main(String args[])
 {
     String strOriginal = "1111";
     String strOp = "-de";
     if(args.length == 2)
     {
         strOp = args[0];
         strOriginal = args[1];
     } else
     {
         System.out.println("Wrong Parameter count , try use \"java DES -de|-en 'the string you want to be Encrypted'\"");
         System.out.println("Now do Encrypt with \"1111\"");
         try
         {
             DES des = new DES();
             System.out.println("***** ¼ÓÃÜ²âÊÔ *****");
             String dd = des.enTest("66009999");
             System.out.println("***** ½âÃÜ²âÊÔ *****");
             des.deTest(dd);
         }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
         return;
     }
 }

 public String enTest(String strOriginal)
 {
     String strEncrypt = "";
     try
     {
        // System.out.println("Plain   String: " + strOriginal);
         strEncrypt = encrypt(strOriginal);
        // System.out.println("Encrypted String: " + strEncrypt);
     }
     catch(Exception ex)
     {
         ex.printStackTrace();
     }
     return strEncrypt;
 }

 public String deTest(String strOriginal)
 {
     String strPlain = "";
     try
     {
        // System.out.println("Encrypted String: " + strOriginal);
         //System.out.println("Encrypted String length = " + strOriginal.length());
         strPlain = decrypt(strOriginal);
         //System.out.println("Plain String: " + strPlain);
     }
     catch(Exception ex)
     {
         ex.printStackTrace();
     }
     return strPlain;
 }

}
