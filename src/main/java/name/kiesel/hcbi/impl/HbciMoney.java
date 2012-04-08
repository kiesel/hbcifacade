/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi.impl;

import name.kiesel.hcbi.Money;
import org.kapott.hbci.structures.Value;

/**
 *
 * @author alex
 */
public class HbciMoney implements Money {
    private Double amount;
    private String currency;

    private HbciMoney(double a, String curr) {
        this.setAmount(a);
        this.setCurrency(curr);
    }

    public static HbciMoney fromValue(Value v) {
        return new HbciMoney(
            v.getDoubleValue(),
            v.getCurr()
        );
    }
        
    public final void setCurrency(String c) {
        this.currency= c;
    }

    public String getCurrency() {
        return this.currency;
    }

    public final void setAmount(Double d) {
        this.amount= d;
    }

    public Double getAmount() {
        return this.amount;
    }

    @Override
    public String toString() {
        return "[" + String.valueOf(this.amount) + " " + this.currency + "]";
    }
    
    
}
