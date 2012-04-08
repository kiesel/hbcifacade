/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi;

import java.util.Date;

/**
 *
 * @author alex
 */
public interface Balance {
    public void setAvailable(Money m);
    public Money getAvailable();
    
    public void setTimestamp(Date d);
    public Date getTimestamp();
}
