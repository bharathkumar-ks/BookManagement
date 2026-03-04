package com.kce.book.util;

import java.util.Scanner;

public class InputUtil {
    private static Scanner sc = new Scanner(System.in);
    public static String getString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }
    public static int getInt(String message) {
        System.out.print(message);
        return sc.nextInt();
    }
}