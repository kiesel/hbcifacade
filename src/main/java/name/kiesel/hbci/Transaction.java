/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hbci;

import java.util.Date;
import java.util.List;

/**
 *
 * @author alex
 */
public interface Transaction {
    public void setBalance(Money m);
    public Money getBalance();
    
    public void setCounterAccount(AccountReference a);
    public AccountReference getCounterAccount();
    
    public void setBookingDate(Date d);
    public Date getBookingDate();
    
    public void setValuta(Date d);
    public Date getValuta();
    
    public void setUsageLines(List<String> l);
    public List<String> getUsageLines();
}
