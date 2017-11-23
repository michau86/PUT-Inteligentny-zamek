package inteligenty_zamek.app_ik.API;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class CyptographyApi {
    private static String key="kluuucz";

    static String pHex="87A8E61DB4B6663CFFBBD19C651959998CEEF608660DD0F2" +
            "5D2CEED4435E3B00E00DF8F1D61957D4FAF7DF4561B2AA30" +
            "16C3D91134096FAA3BF4296D830E9A7C209E0C6497517ABD" +
            "5A8A9D306BCF67ED91F9E6725B4758C022E0B1EF4275BF7B" +
            "6C5BFC11D45F9088B941F54EB1E59BB8BC39A0BF12307F5C" +
            "4FDB70C581B23F76B63ACAE1CAA6B7902D52526735488A0E" +
            "F13C6D9A51BFA4AB3AD8347796524D8EF6A167B5A41825D9" +
            "67E144E5140564251CCACB83E6B486F6B3CA3F7971506026" +
            "C0B857F689962856DED4010ABD0BE621C3A3960A54E710C3" +
            "75F26375D7014103A4B54330C198AF126116D2276E11715F" +
            "693877FAD7EF09CADB094AE91E1A1597";
    static String gHex="3FB32C9B73134D0B2E77506660EDBD484CA7B18F21EF2054" +
            "07F4793A1A0BA12510DBC15077BE463FFF4FED4AAC0BB555" +
            "BE3A6C1B0C6B47B1BC3773BF7E8C6F62901228F8C28CBB18" +
            "A55AE31341000A650196F931C77A57F2DDF463E5E9EC144B" +
            "777DE62AAAB8A8628AC376D282D6ED3864E67982428EBC83" +
            "1D14348F6F2F9193B5045AF2767164E1DFC967C1FB3F2E55" +
            "A4BD1BFFE83B9C80D052B985D182EA0ADB2A3B7313D3FE14" +
            "C8484B1E052588B9B7D2BBD2DF016199ECD06E1557CD0915" +
            "B3353BBB64E0EC377FD028370DF92B52C7891428CDC67EB6" +
            "184B523D1DB246C32F63078490F00EF8D647D148D4795451" +
            "5E2327CFEF98C582664B4C0F6CC41659";


    static BigInteger p = new BigInteger(pHex,16);
    static BigInteger g = new BigInteger(gHex,16);
    static int l = 2048;
    static KeyPair keypair;
    static KeyAgreement KeyAgree;
    ////////do usuniecia
    static KeyPair keypair2;
    static KeyAgreement KeyAgree2;
    ////////////
    public static String DHCreateValuetoSend()
    {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
            keyGen.initialize(new DHParameterSpec(p, g, l));
            keypair = keyGen.generateKeyPair();

            KeyAgree= KeyAgreement.getInstance("DiffieHellman");
            KeyAgree.init(keypair.getPrivate());

        }catch (Exception e){}
        Log.i("HHHH","klucze z valuetosend");
    return Base64.encodeToString(keypair.getPublic().getEncoded(), Base64.DEFAULT);
    }

/////////////do usuniecia
    public static String DHCreateValuetoSend2()
    {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
            keyGen.initialize(new DHParameterSpec(p, g, l));
            keypair2 = keyGen.generateKeyPair();

            KeyAgree2= KeyAgreement.getInstance("DiffieHellman");
            KeyAgree2.init(keypair.getPrivate());

        }catch (Exception e){}

        return Base64.encodeToString(keypair.getPublic().getEncoded(), Base64.DEFAULT);
    }
//////////////////

    public static void DHgetSecret(String publickeyPair){

        byte[] encodedKey     = Base64.decode(publickeyPair, Base64.DEFAULT);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        try {
            KeyAgree.doPhase(originalKey, true);
        }catch(Exception e){}

        Log.i("HHHH","sekret");

    }
/////////////do usuniecia
    public static void DHgetSecret2(String publickeyPair){

        byte[] encodedKey     = Base64.decode(publickeyPair, Base64.DEFAULT);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        try {
            KeyAgree2.doPhase(originalKey, true);
        }catch(Exception e){}
    }


//////////////////////////
    public static void symulation()
    {
        String aX,bX;
        ///wygenerowanie publicznej wartosci
        aX= DHCreateValuetoSend();
        bX= DHCreateValuetoSend2();

        //wygenerowanie wspólnego sekretu
        DHgetSecret(bX);
        DHgetSecret2(aX);

        MessageDigest hash;
        try {

            hash  = MessageDigest.getInstance("SHA-256");


            String mesa=encrypt("czesc jestem",(hash.digest(KeyAgree.generateSecret())));
            String mesb=encrypt("wesoły romek",(hash.digest(KeyAgree2.generateSecret())));


            Log.i("HHHH",decrypt( mesa, (hash.digest(KeyAgree2.generateSecret()) )));

            Log.i("HHHH",decrypt( mesb, (hash.digest(KeyAgree.generateSecret()) )));




        }catch (Exception ex){}


    }

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));
        byte[] signature = privateSignature.sign();
        return Base64.encodeToString(signature,Base64.DEFAULT);
    }

    public static String encrypt(String value)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        byte[] value_bytes = value.getBytes("UTF-8");
        byte[] key_bytes = getKeyBytes(key);
        return Base64.encodeToString(encrypt(value_bytes, key_bytes, key_bytes), 0);
    }

    public static String encrypt(String value,byte[] secretKey)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        byte[] value_bytes = value.getBytes("UTF-8");
        Log.i("HHHH","7,75");
        return Base64.encodeToString(encrypt(value_bytes, secretKey, new byte[16]), 0);
    }


    public static byte[] encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        Log.i("HHHH","7,85");
        // setup AES cipher in CBC mode with PKCS #5 padding
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Log.i("HHHH","7,95");
        // encrypt

        new SecretKeySpec(paramArrayOfByte2, "AES");
        Log.i("HHHH","hmmm1");
        new IvParameterSpec(paramArrayOfByte3);
        Log.i("HHHH","hmmm2");
        localCipher.init(1, new SecretKeySpec(paramArrayOfByte2, "AES"), new IvParameterSpec(paramArrayOfByte3));
        Log.i("HHHH","7,99");
        return localCipher.doFinal(paramArrayOfByte1);
    }



    public static String decrypt(String value,byte[] secretKey)
            throws GeneralSecurityException, IOException
    {
        byte[] value_bytes = Base64.decode(value, 0);

        return new String(decrypt(value_bytes, secretKey, new byte[16]), "UTF-8");
    }


    public static String decrypt(String value)
            throws GeneralSecurityException, IOException
    {
        byte[] value_bytes = Base64.decode(value, 0);
        byte[] key_bytes = getKeyBytes(key);
        return new String(decrypt(value_bytes, key_bytes, key_bytes), "UTF-8");
    }

    public static byte[] decrypt(byte[] ArrayOfByte1, byte[] ArrayOfByte2, byte[] ArrayOfByte3)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        // setup AES cipher in CBC mode with PKCS #5 padding
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // decrypt
        localCipher.init(2, new SecretKeySpec(ArrayOfByte2, "AES"), new IvParameterSpec(ArrayOfByte3));
        return localCipher.doFinal(ArrayOfByte1);
    }

    private static byte[] getKeyBytes(String paramString)
            throws UnsupportedEncodingException
    {
        byte[] arrayOfByte1 = new byte[16];
        byte[] arrayOfByte2 = paramString.getBytes("UTF-8");
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, Math.min(arrayOfByte2.length, arrayOfByte1.length));
        return arrayOfByte1;
    }

    public static byte[] getHash(String password) {
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
    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }

}
