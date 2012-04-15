/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hbci.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import name.kiesel.hbci.AccountReference;
import name.kiesel.hbci.Money;
import name.kiesel.hbci.Transaction;
import org.kapott.hbci.GV_Result.GVRKUms.UmsLine;

/**
 *
 * @author alex
 */
public class HbciTransaction implements Transaction {
    private Money balance;
    private Date bookingdate;
    private Date valuta;
    private String text;
    private ArrayList<String> usage;
    private AccountReference counterAccount;

    public HbciTransaction() {
    }

    public static Transaction fromUmsLine(UmsLine umsLine) {
        HbciTransaction t= new HbciTransaction();
        t.setBookingDate(umsLine.bdate);
        t.setBalance(HbciMoney.fromValue(umsLine.value));
        t.setValuta(umsLine.valuta);
        t.setText(umsLine.text);
        t.setUsageLines(umsLine.usage);

        if (null != umsLine.other) {
            t.setCounterAccount(HbciAccountReference.fromKonto(umsLine.other));
        }
        
        return t;
    }

    public void setBalance(Money m) {
        this.balance= m;
    }

    public Money getBalance() {
        return this.balance;
    }

    public void setBookingDate(Date d) {
        this.bookingdate= d;
    }

    public Date getBookingDate() {
        return this.bookingdate;
    }

    public void setValuta(Date d) {
        this.valuta= d;
    }

    public Date getValuta() {
        return this.valuta;
    }

    public void setUsageLines(List<String> l) {
        this.usage= new ArrayList<String>();
        
        for (Iterator<String> i= l.iterator(); i.hasNext(); ) {
            this.usage.add(i.next());
        }
    }

    public List<String> getUsageLines() {
        return this.usage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override public String toString() {
        StringBuilder sb= new StringBuilder();
        
        sb.append("Transaction(").append(this.hashCode()).append(") {\n");
        sb.append("  [      valuta ] ").append(this.valuta.toString()).append("\n");
        sb.append("  [     account ] ").append(null != this.counterAccount ? this.counterAccount.toString() : '-').append("\n");
        sb.append("  [ bookingdate ] ").append(this.bookingdate.toString()).append("\n");
        sb.append("  [        text ] ").append(this.text).append("\n");
        sb.append("  [       usage ] {\n");
        for (Iterator<String> it= this.usage.iterator(); it.hasNext(); ) {
            sb.append("    > ").append(it.next()).append("\n");
        }
        sb.append("  }\n}\n");
        
        return sb.toString();
    }

    public void setCounterAccount(AccountReference a) {
        this.counterAccount= a;
    }

    public AccountReference getCounterAccount() {
        return this.counterAccount;
    }

    
}
