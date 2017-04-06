package seedu.jobs.model;

import java.util.Objects;

public class LoginInfo {
    
    private String email;
    private String password;
    
    
    public LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public LoginInfo(){
        
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LoginInfo)) { //this handles null as well.
            return false;
        }
        return this.email.equals(((LoginInfo)other).getEmail()) && this.password.equals(((LoginInfo)other).getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public String toString() {
        return "email : " + email + " password : " + password;
    }
}
