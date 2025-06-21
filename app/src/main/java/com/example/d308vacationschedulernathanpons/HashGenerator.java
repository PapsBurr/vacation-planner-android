package com.example.d308vacationschedulernathanpons;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashGenerator {

    public Map<String, String> generateHash(String unHashedPassword) throws NoSuchAlgorithmException {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String saltString = encoder.encodeToString(salt);

            KeySpec spec = new PBEKeySpec(unHashedPassword.toCharArray(), saltString.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] passwordHash = factory.generateSecret(spec).getEncoded();


            Map<String, String> hashSaltMap = new HashMap<>();
            hashSaltMap.put("passwordHash", encoder.encodeToString(passwordHash));
            hashSaltMap.put("salt", encoder.encodeToString(salt));

            return hashSaltMap;
        }
        catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean AuthorizeUser(String password, String salt, String passwordHash) {

        try {
            Base64.Encoder encoder = Base64.getEncoder();

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] confPasswordHash = factory.generateSecret(spec).getEncoded();

            Log.d("password", password);
            Log.d("passwordHash", passwordHash);
            Log.d("confPasswordHash", encoder.encodeToString(confPasswordHash));
            Log.d("salt", salt);
            Log.d("saltBytes", encoder.encodeToString(salt.getBytes()));

            return passwordHash.equals(encoder.encodeToString(confPasswordHash));

        }
        catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
