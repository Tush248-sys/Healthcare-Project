package com.example.healthcare;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static class OtpEntry {
        private final String code;
        private final LocalDateTime expiresAt;

        private OtpEntry(String code, LocalDateTime expiresAt) {
            this.code = code;
            this.expiresAt = expiresAt;
        }
    }

    private final Map<String, OtpEntry> storage = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String generateOtp(String key, int minutesValid) {
        String code = String.format("%06d", random.nextInt(1_000_000));
        storage.put(key, new OtpEntry(code, LocalDateTime.now().plusMinutes(minutesValid)));
        return code;
    }

    public boolean verifyOtp(String key, String submittedCode) {
        if (submittedCode == null) {
            return false;
        }
        OtpEntry entry = storage.get(key);
        if (entry == null) {
            return false;
        }
        if (LocalDateTime.now().isAfter(entry.expiresAt)) {
            storage.remove(key);
            return false;
        }
        boolean match = entry.code.equals(submittedCode.trim());
        if (match) {
            storage.remove(key); // one-time use
        }
        return match;
    }
}
