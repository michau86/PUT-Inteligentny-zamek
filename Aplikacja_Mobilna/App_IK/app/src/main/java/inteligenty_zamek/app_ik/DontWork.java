package inteligenty_zamek.app_ik;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Damian on 26.10.2017.
 */

public final class DontWork {
    public static SecretKey generateKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return  new SecretKeySpec("safdsags".getBytes(), "AES");
    }

    //szyfrowanie
    public static byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {

        String key = "Bar12345Bar12345Bar12345Bar12345";

        SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }
    //deszyfrowanie
    public static String decryptMsg(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException
    {
        if(cipherText!=null)
        {
            Log.i("decrypt","aaaaaaa1");
        }


        String key = "Bar12345Bar12345Bar12345Bar12345";
        SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Log.i("decrypt","aaaaaaa2");

        Cipher cipher = Cipher.getInstance("AES");
        Log.i("decrypt","aaaaaaa3");

        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        Log.i("decrypt","aaaaaaa4");
        byte [] a=cipher.doFinal(cipherText);


        if(a!=null){Log.i("decryptb","aaaaaaaaaaaaaaaaaaaaaaa");}

        String decryptString = new String(a, "UTF-8");
        Log.i("decrypta",decryptString);
        return decryptString;
    }


    public PrivateKey getPrivKey(String userLogin)
    {
        /*
        String privatekeystring= fileReadWriteApi.readFromFile("*"+userLogin);
        String privatekeystringDecrytp="x";
        try {
            CyptographyApi as=new CyptographyApi();
            privatekeystringDecrytp = as.decrypt(privatekeystring);
        }catch(Exception e){}

        byte[] encodedKey     = Base64.decode(privatekeystringDecrytp , Base64.DEFAULT);
        PrivateKey priv=null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
            priv = kf.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        }catch(Exception e){return null ;}
        return priv;
        */
        return null;
    }



    private final static String HEX = "0123456789ABCDEF";

    //funkcje odpowiedzialne za szyfrowanie i deszyfrowanie (asymetryczne)
    public static String encryption(String strNormalText){
        String seedValue = "sdfushdfgkjklvsdlmiosaddfyemno";
        String normalTextEnc="";
        try {
            normalTextEnc = encrypt(seedValue, strNormalText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }
    public static String decryption(String strEncryptedText){
        String seedValue = "sdfushdfgkjklvsdlmiosaddfyemno";
        String strDecryptedText="";
        try {
            strDecryptedText = decrypt(seedValue, strEncryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDecryptedText;
    }
    private static String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }
    private static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG","Crypto");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    private static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }
    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }
}
