/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import name.kiesel.hcbi.Transaction;
import name.kiesel.hcbi.Transactions;

/**
 *
 * @author alex
 */
class HbciTransactions implements Transactions {
    private List<Transaction> transactions;
    
    public HbciTransactions() {
        this.transactions= new ArrayList<Transaction>();
    }

    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }

    public Iterator<Transaction> getIterator() {
        return this.transactions.iterator();
    }

    public Date latestTransaction() {
        Date earliest= null;
        
        Iterator<Transaction> it= this.getIterator();
        while (it.hasNext()) {
            Transaction t= it.next();
            
            if (null == earliest || earliest.after(t.getBookingDate())) {
                earliest= t.getBookingDate();
            }
        }
        
        return earliest;
    }

    @Override public String toString() {
        StringBuilder sb= new StringBuilder();
        for (Iterator<Transaction> it= this.getIterator(); it.hasNext();) {
            sb.append(it.next().toString());
        }
        
        return sb.toString();
    }
    
}
