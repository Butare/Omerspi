/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.tags;


import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;
import jim.omerspi.Context;

/**
 *
 * @author JOHN
 */
public class RequireTag extends ConditionalTagSupport {

    public String privilege;

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    protected boolean condition() throws JspTagException {
        return Context.hasPrivilege(pageContext.getSession(), privilege);
    }
}
