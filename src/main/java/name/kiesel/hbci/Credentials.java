/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hbci;

/**
 *
 * @author alex
 */
public interface Credentials {
    public void setCustomerId(String customerId);
    public String getCustomerId();
    
    public void setUserId(String userId);
    public String getUserId();
    
    public void setPin(String p);
    public String getPin();
}
