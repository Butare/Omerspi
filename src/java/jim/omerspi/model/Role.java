package jim.omerspi.model;
// Generated May 14, 2013 4:28:15 PM by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Role generated by hbm2java
 */
public class Role implements java.io.Serializable {

    public static final String SUPERUSER = "Administrator";
    private String role;
    private String roleDescription;
    private Set<User> users = new HashSet<User>(0);
    private Set<User> users_1 = new HashSet<User>(0);
    private Set<Privilege> privileges = new HashSet<Privilege>(0);

    public Role() {
    }

    public Role(String role, String roleDescription) {
        this.role = role;
        this.roleDescription = roleDescription;
    }

    public Role(String role, String roleDescription, Set<User> users, Set<User> users_1, Set<Privilege> privileges) {
        this.role = role;
        this.roleDescription = roleDescription;
        this.users = users;
        this.users_1 = users_1;
        this.privileges = privileges;
    }
    
    public boolean isSuperUser() {
        return SUPERUSER.equals(this.role);
    }

    public boolean hasPrivilege(String privilegeName) {

        if (isSuperUser()) {
            return true;
        }

        if (privileges != null) {
            for (Privilege p : privileges) {
                if (p.getPrivilege().equals(privilegeName)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    
    

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleDescription() {
        return this.roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers_1() {
        return this.users_1;
    }

    public void setUsers_1(Set<User> users_1) {
        this.users_1 = users_1;
    }

    public Set<Privilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
}
