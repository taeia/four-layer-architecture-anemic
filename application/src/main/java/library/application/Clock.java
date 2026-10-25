package library.application;

import java.time.LocalDate;

public class Clock {

    private static LocalDate today = LocalDate.of(2026, 3, 1);

    public static LocalDate today() {
        return today;
    }

    public static void set(LocalDate date) {
        today = date;
    }
}
