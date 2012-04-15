/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hbci.impl;

import name.kiesel.hbci.Transaction;
import name.kiesel.hbci.Account;
import name.kiesel.hbci.Session;
import name.kiesel.hbci.Transactions;
import name.kiesel.hbci.Balance;
import name.kiesel.hbci.Credentials;

/**
 *
 * @author alex
 */
public final class HbciAccount implements Account {
    private String account;
    private String bankCode;
    private Credentials credentials;
    private Balance balance= null;
    private Transactions transactions= null;
    private String url= null;
    private HbciVersion version= HbciVersion.V300;

    public HbciAccount(final String acct, final String code) {
        this.setAccountNumber(acct);
        this.setBankCode(code);
        this.transactions= new HbciTransactions();
    }

    public void setAccountNumber(String acct) {
        this.account= acct;
    }

    public String getAccountNumber() {
        return this.account;
    }

    public void setBankCode(String code) {
        this.bankCode= code;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setCredentials(Credentials c) {
        this.credentials= c;
    }

    public Credentials getCredentials() {
        if (null == this.credentials) {
            this.credentials= new HbciCredentials();
        }
        return this.credentials;
    }

    public Session createHbciSession() {
        return new HbciSession(this);
    }

    public HbciVersion getVersion() {
        return version;
    }

    public void setVersion(HbciVersion version) {
        this.version = version;
    }

    public Balance getBalance() {
        if (null == this.balance) {
            this.balance= new HbciBalance();
        }
        
        return this.balance;
    }
    
    public void addTransaction(Transaction t) {
        this.transactions.addTransaction(t);
    }
    
    public Transactions getTransactions() {
        return this.transactions;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
    @Override public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append(this.getClass().getName()).append("(").append(this.hashCode()).append(") {\n");
        sb.append("  [account  ] ").append(this.getAccountNumber()).append("\n");
        sb.append("  [bankCode ] ").append(this.getBankCode()).append("\n");
        if (null != this.balance) {
            sb.append("  [balance  ] ").append(this.balance.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
