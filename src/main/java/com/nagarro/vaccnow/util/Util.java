package com.nagarro.vaccnow.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class Util {

    public static Time calculateEndTime(Time startTimeObj) throws ParseException {
        DateFormat format = new SimpleDateFormat("HH:mm");
        LocalTime localtime = startTimeObj.toLocalTime();
        localtime = localtime.plusMinutes(15);
        String output = localtime.toString();
        Date parse = format.parse(output);
        long time = parse.getTime();
        return new Time(time);
    }

    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new
                    NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

    /**
     * @param startTime: Time Range Start
     * @param endTime:   Time Range End
     * @param timeStr:   Time which needs to be checked
     * @return
     */
    public static boolean timeBetweenTwoTimeslots(Time startTime, Time endTime, String timeStr) {
        try {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(startTime.getTime());
            calendar1.add(Calendar.DATE, 1);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(endTime.getTime());
            calendar2.add(Calendar.DATE, 1);

            Date d = new SimpleDateFormat("HHmm").parse(timeStr);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getProperty(String key) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream =
                Util.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(inputStream);

        return properties.getProperty(key);
    }
}
