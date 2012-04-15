package name.kiesel.hbcifacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import name.kiesel.hbci.Account;
import name.kiesel.hbci.impl.HbciAccount;
import name.kiesel.hbci.impl.HbciCredentials;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println("Starting ...");
        String acct, code, name;
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Account: ");
        acct= br.readLine();
        
        System.out.print("Bank code: ");
        code= br.readLine();
        
        HbciAccount a= new HbciAccount(acct, code);
        a.setCredentials(new HbciCredentials());
        
        System.out.print("PIN: ");
        a.getCredentials().setPin(br.readLine());
        
//        a.openHbciSession().logIn();
//        a.openHbciSession().acquireBalance();
        a.createHbciSession().acquireTransactions();
        
        System.out.println("Account: " + a);
        System.out.println("Transactions: " + a.getTransactions());
    }
}
