/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

/**
 *
 * @author JOHN
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String privilege) {
        super("User requires the '" + privilege + "' privilege");
    }
    
}
