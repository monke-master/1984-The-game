package com.mygdx.game;


import java.util.Calendar;
import java.util.GregorianCalendar;

public class TheGame {
    static GregorianCalendar date = new GregorianCalendar(1984, Calendar.APRIL, 1);
    static int speed = 1;
    static boolean onPause = false;

    static void nextDay() {
        date.add(Calendar.DAY_OF_MONTH, 1);
    }
    static String getDate() {
        int day = date.get(Calendar.DAY_OF_MONTH);
        String day_str = String.valueOf(day);
        if (day < 10)
            day_str = "0" + day_str;
        int month = date.get(Calendar.MONTH) + 1;
        String month_str = String.valueOf(month);
        if (month < 10)
            month_str = "0" + month_str;
        String year_str = String.valueOf((date.get(Calendar.YEAR)));
        return day_str + "." + month_str + "." + year_str;
    }

    static public void putOnPause() {
        onPause = true;
    }

    static public void resume() {
        onPause = false;
    }

}
