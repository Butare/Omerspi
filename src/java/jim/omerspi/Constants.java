/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

/**
 *
 * @author Jimmy
 */
public class Constants {

    public static final String USER_SESSION_ATTR = "omerspi-user";
    public static final String ITEMREQUEST_SESSION_ATTR = "omerspi-itemrequest";
    public static final String ERROR_SESSION_ATTR = "omerspi-error";
    public static final String INFO_SESSION_ATTR = "omerspi-info";
    public static final String ALERT_SESSION_ATTR = "omerspi-alert";
    public static final String omerspiEmail = "OMERSPI <omerspirequisition@gmail.com>";
    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}
