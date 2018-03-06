/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author JOHN
 */
public class OmerspiUtils {

    public static Date getFirstMomentOfDay(Date day) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(day);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);
        return calender.getTime();
    }
    
    public static Date getLastMomentOfDay(Date day) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(day);
        calender.set(Calendar.HOUR_OF_DAY, 23);
        calender.set(Calendar.MINUTE, 59);
        calender.set(Calendar.SECOND, 59);
        calender.set(Calendar.MILLISECOND, 999);
        return calender.getTime();
    }

    public static void setErrorMessage(HttpSession session, String message) {
        session.setAttribute(Constants.ERROR_SESSION_ATTR, message);
    }

    public static void setInfoMessage(HttpSession session, String message) {
        session.setAttribute(Constants.INFO_SESSION_ATTR, message);
    }

    // Rwanda Currency format method
    public static String getRwandaCurrencyFormat(long amount) {
        return String.format("%,d", amount);
    }

    //Check for number
    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //Check for character
    public static boolean isCharacter(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //Check for special characters.
    public static boolean hasSpecial(String str) {

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }

    }

    //Check for valid email address
    public static boolean isEmailValid(String str) {
        Pattern p = Pattern.compile(Constants.EMAIL_PATTERN);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
