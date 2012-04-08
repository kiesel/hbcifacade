/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi;

/**
 *
 * @author alex
 */
public interface Money {
    public void setCurrency(String c);
    public String getCurrency();
    
    public void setAmount(Double d);
    public Double getAmount();
}
