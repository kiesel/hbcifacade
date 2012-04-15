/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hbci.impl;

import name.kiesel.hbci.Credentials;

/**
 *
 * @author alex
 */
public final class HbciCredentials implements Credentials {
    private String customerId   = null;
    private String userId       = null;
    private String pin          = null;

    public HbciCredentials(String customerId) {
        this.setCustomerId(customerId);
    }
    
    public HbciCredentials() {
    }

    @Override public String getCustomerId() {
        return customerId;
    }

    @Override public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override public String getPin() {
        return pin;
    }

    @Override public void setPin(String pin) {
        this.pin = pin;
    }

    @Override public String getUserId() {
        return userId;
    }

    @Override public void setUserId(String userId) {
        this.userId = userId;
    }
}
