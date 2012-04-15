/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hbci;

/**
 *
 * @author alex
 */
public interface Account {
    public void setAccountNumber(String acct);
    public String getAccountNumber();
    
    public void setBankCode(String code);
    public String getBankCode();
    
    public void setCredentials(Credentials c);
    public Credentials getCredentials();
    
    public Balance getBalance();
    
    public void addTransaction(Transaction t);
    public Transactions getTransactions();
}
