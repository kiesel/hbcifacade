/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi.impl;

import name.kiesel.hcbi.AccountReference;
import org.kapott.hbci.structures.Konto;

/**
 *
 * @author alex
 */
public class HbciAccountReference implements AccountReference {
    private String accountNumber;
    private String bankCode;
    private String name;

    public HbciAccountReference(String accountNumber, String bankCode, String name) {
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.name = name;
    }
    
    public static HbciAccountReference fromKonto(Konto k) {
        return new HbciAccountReference(k.number, k.blz, k.name);
    }

    @Override public String toString() {
        return "AccountReference{" + "accountNumber=" + accountNumber + ", bankCode=" + bankCode + ", name=" + name + '}';
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
