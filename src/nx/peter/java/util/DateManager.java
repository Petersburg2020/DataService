package nx.peter.java.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateManager {

    public static Date getDate() {
        return new Date();
    }

    public static String getCurrentDate(CharSequence format) {
        return getFormattedDate(getDate(), format);
    }

    public static String getCurrentDate() {
        return getCurrentDate(DateFormatter.DEFAULT);
    }

    protected static SimpleDateFormat getDateFormat(CharSequence format) {
        return new SimpleDateFormat(format.toString(), Locale.US);
    }

    public static String getFormattedDate(Date date,CharSequence format) {
        return getDateFormat(format).format(date);
    }

    public static Date parseDate(CharSequence date, CharSequence format) {
        try {
            return getDateFormat(format).parse(date.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(CharSequence date) {
        return parseDate(date, DateFormatter.DEFAULT);
    }

    public static class DateFormatter {
        public static final String DD_MM_YYYY = "dd/MM/yyyy";
        public static final String DD_MM_YY = "dd/MM/yy";
        public static final String DD_MMM_YYYY = "dd MMM yyyy";
        public static final String DEFAULT = DD_MMM_YYYY;
    }

}
