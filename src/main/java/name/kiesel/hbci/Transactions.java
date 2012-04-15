/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hbci;

import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author alex
 */
public interface Transactions {
    public void addTransaction(Transaction t);
    public Iterator<Transaction> getIterator();
    public Date latestTransaction();
}
