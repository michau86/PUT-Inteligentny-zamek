package inteligenty_zamek.app_ik;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SessionContainer extends Application {

    public String session;
    public User user;

    public PrivateKey getPrivatekye() {
        return privatekye;
    }

    public void setPrivatekye(PrivateKey privatekye) {
        this.privatekye = privatekye;
    }

    public PrivateKey privatekye;
    public Certyficat[] getCertyficatlist() {
        return certyficatlist;
    }

    public void setCertyficatlist(Certyficat[] certyficatlist) {
        this.certyficatlist = certyficatlist;
    }

    public Certyficat[] certyficatlist;
    public String getSerwerIP() {
        return serwerIP;
    }

    public void setSerwerIP(String serwerIP) {
        this.serwerIP = serwerIP;
    }

    public String serwerIP="192.168.8.100";
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public byte[] getHash(String password) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }
    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }



    //metody odpowiedzialne za zapisywanie dfo pliku


    public void writeToFile(String data,Context context,String filepath) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filepath, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


   // funkcja odpowiedzialna za odczytywanie z pliku

    public String readFromFile(Context context, String filepath) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filepath);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return "NULL";
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public static String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    public static String decrypt(String seed, String encrypted) throws Exception {
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

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }

    public String encryption(String strNormalText){
        String seedValue = "sdfushdfgkjklvsdlmiosaddfyemno";
        String normalTextEnc="";
        try {
            normalTextEnc = encrypt(seedValue, strNormalText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }
    public String decryption(String strEncryptedText){
        String seedValue = "sdfushdfgkjklvsdlmiosaddfyemno";
        String strDecryptedText="";
        try {
            strDecryptedText = decrypt(seedValue, strEncryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDecryptedText;
    }




}
