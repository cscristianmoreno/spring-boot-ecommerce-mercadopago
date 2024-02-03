package com.ecommerce.app.utils;

import java.time.Instant;

public abstract class InstantUtil {
    
    public static long getCurrentMillis() {
        return Instant.now().toEpochMilli();
    }

    
    public static long setCurrentMillis(int expire) {
        Long millis = Instant.now().toEpochMilli() + (expire * 1000);
        return millis;
    }
}
