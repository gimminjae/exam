package com.examback.common.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;

@Component
public class ObjectUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CHARACTERS_ONLY_NUMBER = "0123456789";
    private static final int LENGTH = 8;

    public static String generateRandomString() {
        return generateRandomStringByInput(CHARACTERS);
    }
    public static <T> T isNullExceptionElseReturnObJect(Optional<T> optionalT) {
        T t = optionalT.orElse(null);

        if(t == null) {
            throw new NullPointerException("%s 데이터가 존재하지 않습니다.".formatted(t.getClass()));
        }
        return t;
    }
    public static <T> T isNullExceptionElseReturnObJect(Optional<T> optionalT, String message) {
        T t = optionalT.orElse(null);

        if(t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
    public static String divide(float numerator, float denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }

        float result = numerator / denominator;
        DecimalFormat decimalFormat = new DecimalFormat("#.####"); // Format to 4 decimal places
        return decimalFormat.format(result);
    }

    public static String generateRandomStringOnlyNumber() {
        return generateRandomStringByInput(CHARACTERS_ONLY_NUMBER);
    }
    private static String generateRandomStringByInput(String stringType) {
        StringBuilder sb = new StringBuilder(LENGTH);
        Random random = new Random();

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(stringType.length());
            char randomChar = stringType.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
