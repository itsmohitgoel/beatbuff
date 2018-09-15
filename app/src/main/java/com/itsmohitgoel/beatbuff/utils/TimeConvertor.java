package com.itsmohitgoel.beatbuff.utils;

public class TimeConvertor {
    public static String milliSecondsToTimer(int milliSeconds) {
        StringBuffer timerFormatString = new StringBuffer();
        StringBuffer minutesString;
        StringBuffer secondsString;

        int minutes = (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (minutes < 10) {
            minutesString = new StringBuffer("0").append(minutes);
        }else {
            minutesString = new StringBuffer().append(minutes);
        }

        if (seconds < 10) {
            secondsString =  new StringBuffer("0").append(seconds);
        }else {
            secondsString =  new StringBuffer().append(seconds);
        }

        timerFormatString = timerFormatString
                .append(minutesString)
                .append(":")
                .append(secondsString);

        return timerFormatString.toString();
    }
}
