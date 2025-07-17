package com.example.demo.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        var key = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("✅ SECURE JWT SECRET (copy to properties):");
        System.out.println(base64Key);
    }
}
