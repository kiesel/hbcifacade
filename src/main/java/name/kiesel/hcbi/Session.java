/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi;

/**
 *
 * @author alex
 */
public interface Session {

    public void logIn();
    
    public void acquireBalance();

    public void acquireTransactions();
}
