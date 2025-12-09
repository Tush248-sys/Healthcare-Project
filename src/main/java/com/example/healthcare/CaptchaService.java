package com.example.healthcare;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // avoid confusing chars
    private static final int DEFAULT_LENGTH = 6;

    private final SecureRandom random = new SecureRandom();

    public String generateCaptchaCode() {
        StringBuilder sb = new StringBuilder(DEFAULT_LENGTH);
        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            int idx = random.nextInt(CHARS.length());
            sb.append(CHARS.charAt(idx));
        }
        return sb.toString();
    }

    public boolean isValid(String expected, String provided) {
        if (expected == null || provided == null) {
            return false;
        }
        return expected.equalsIgnoreCase(provided.trim());
    }
}