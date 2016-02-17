package Calendar.MainCallendarPage;


import java.util.GregorianCalendar;

public class MyCalendar {

    public static String checkDayOfWeekMatches(int dayOfMonth, int day, GregorianCalendar c) {

        if(day >= c.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)+1)
            return "-";

        int dayWeek = c.get(GregorianCalendar.DAY_OF_WEEK);
        if(dayOfMonth < 8) {
            if (dayOfMonth == dayWeek)
                return day + "";
        } else
            if((dayOfMonth%7 == 0? dayOfMonth%7+7: dayOfMonth%7 ) == dayWeek)
                return day+"";
        return "-";
    }




}
