package com.itsmohitgoel.beatbuff.utils;

/**
 * Utility class handling time conversions
 */
public class TimeConverter {

    /**
     * Converts milliseconds to minutes and seconds individually
     * and together
     * @param milliSeconds
     * @return String representing time eg 70000 to 01:10
     */
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
