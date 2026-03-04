package com.kce.book.util;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static LocalDate calculateDueDate(LocalDate borrowDate) {
        return borrowDate.plusDays(14);
    }
    public static long calculateOverdueDays(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isAfter(dueDate)) {
            return ChronoUnit.DAYS.between(dueDate, returnDate);
        }
        return 0;
    }
}