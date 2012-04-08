/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi;

/**
 *
 * @author alex
 */
public interface AccountReference {
    public void setAccountNumber(String acct);
    public String getAccountNumber();
    
    public void setBankCode(String code);
    public String getBankCode();
    
    public void setName(String n);
    public String getName();
}
