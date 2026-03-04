package com.kce.book.util;

public class ValidationUtil {

    public static boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }
    public static boolean isValidCopies(int copies) {
        return copies > 0;
    }
}