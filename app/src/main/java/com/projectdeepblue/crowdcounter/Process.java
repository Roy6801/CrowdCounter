package com.projectdeepblue.crowdcounter;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Process {

        public static String toDatetime(String strunix) {
                long unixsec = Long.parseLong(strunix);
                Date date = new Date(unixsec*1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
                String formattedDate = sdf.format(date);
                return formattedDate;
        }


        public static String toEpoch(String datetime) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = null;
                try {
                        date = df.parse(datetime);
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                long epoch = (Long) (date.getTime()/1000);
                return Long.toString(epoch);
        }
}