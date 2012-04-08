/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi.impl;

import java.util.*;
import name.kiesel.hcbi.Account;
import name.kiesel.hcbi.Session;
import org.kapott.hbci.GV.HBCIJob;
import org.kapott.hbci.GV_Result.GVRKUms;
import org.kapott.hbci.GV_Result.GVRKUms.UmsLine;
import org.kapott.hbci.GV_Result.GVRSaldoReq;
import org.kapott.hbci.GV_Result.GVRSaldoReq.Info;
import org.kapott.hbci.callback.HBCICallbackConsole;
import org.kapott.hbci.manager.HBCIHandler;
import org.kapott.hbci.manager.HBCIUtils;
import org.kapott.hbci.passport.AbstractHBCIPassport;
import org.kapott.hbci.passport.HBCIPassport;
import org.kapott.hbci.status.HBCIExecStatus;
import org.kapott.hbci.structures.Konto;

/**
 *
 * @author alex
 */
class HbciSession implements Session {
    private HbciAccount acct;
    private HbciVersion protocolVersion= HbciVersion.V300;

    private static class Callback extends HBCICallbackConsole {
        private HbciSession session;
        private static final String PASSPHRASE  = "238dsflqJSSD:__sda3";
        
        public Callback(HbciSession session) {
            this.session= session;
        }

        @Override
        public synchronized void status(HBCIPassport passport, int statusTag, Object[] o) {
            // Intentionally empty
        }

        @Override
        public void callback(HBCIPassport passport, int reason, String msg, int datatype, StringBuffer retData) {
            HBCIUtils.log("[LOG] " + msg + " / Reason: " + reason + " / datatype: " + datatype, HBCIUtils.LOG_DEBUG);

            switch (reason) {
                case NEED_BLZ: 
                    retData.append(this.session.acct.getBankCode()); 
                    break;
                    
                case NEED_CUSTOMERID:
                    if (null != this.session.acct.getCredentials().getCustomerId()) {
                        retData.append(this.session.acct.getCredentials().getCustomerId());
                    }
                    break;
                    
                case NEED_USERID:
                    if (null != this.session.acct.getCredentials().getUserId()) {
                        retData.append(this.session.acct.getCredentials().getUserId());
                    }
                    break;
                    
                case NEED_PT_PIN:
                    retData.append(this.session.acct.getCredentials().getPin());
                    break;
                
                case NEED_PASSPHRASE_SAVE:
                case NEED_PASSPHRASE_LOAD:
                    retData.append(PASSPHRASE);
                    break;
                    
                case NEED_PT_SECMECH:
                    retData.append("mobileTAN");
                    break;
                    
                case NEED_COUNTRY:
                case NEED_HOST:
                case NEED_CONNECTION:
                case CLOSE_CONNECTION:
                default:
                    // Intentionally empty!
            }
            
            HBCIUtils.log("Returning " + retData.toString(), HBCIUtils.LOG_DEBUG);
        }
    }

    public HbciSession(HbciAccount a) {
        this.acct = a;
    }

    @Override public void logIn() {
        HBCIHandler handle = this.createHbciHandler();
        this.printSupportedGVs(handle);
    }

    private HBCIHandler createHbciHandler() {
        this.initialize();
        HBCIPassport passport = AbstractHBCIPassport.getInstance();
        
        HBCIHandler handle = new HBCIHandler(this.acct.getVersion().toParam(), passport);
        return handle;
    }
    
    public void clearCachedDetails(HBCIPassport passport) {
        passport.clearBPD();
        passport.clearUPD();
    }

    private void initialize() {
        HBCIUtils.init(null, new Callback(this));

        // Set basic parameters
        HBCIUtils.setParam("client.passport.hbciversion.default", this.acct.getVersion().toParam());
        HBCIUtils.setParam("client.connection.localPort", null);
        HBCIUtils.setParam("log.loglevel.default", "3");
        HBCIUtils.setParam("kernel.rewriter", HBCIUtils.getParam("kernel.rewriter"));

        // Configure for PinTan
        HBCIUtils.setParam("client.passport.default", "PinTan");
        HBCIUtils.setParam("client.passport.PinTan.filename", "my_passport_pintan.dat");
        HBCIUtils.setParam("client.passport.PinTan.checkcert", "1");
        HBCIUtils.setParam("client.passport.PinTan.certfile", null);
        HBCIUtils.setParam("client.passport.PinTan.init", "1");
    }

    private void printSupportedGVs(HBCIHandler handle) {
        Properties gvcodes = getGVCodes(handle.getPassport());

        String[] codes = (String[]) gvcodes.keySet().toArray(new String[0]);
        Arrays.sort(codes);

        System.out.println();
        System.out.println("lowlevel GVs supported by institute and HBCI4Java:");
        for (int i = 0; i < codes.length; i++) {
            String gvcode = codes[i];
            String name = gvcodes.getProperty(gvcode);
            if (!name.startsWith("Template")) {
                System.out.println("  " + gvcode + " (" + name + ")");
            }
        }
        System.out.println();
        System.out.println("lowlevel GVs supported by institute but unknown to HBCI4Java:");
        for (int i = 0; i < codes.length; i++) {
            String gvcode = codes[i];
            String name = gvcodes.getProperty(gvcode);
            if (name.startsWith("Template")) {
                System.out.println("  " + gvcode);
            }
        }
    }

    private Properties getGVCodes(HBCIPassport passport) {
        Properties ret = new Properties();
        Properties bpd = passport.getBPD();

        for (Enumeration e = bpd.propertyNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();

            if (key.startsWith("Params")
                    && key.endsWith(".SegHead.code")) {
                String gvcode = bpd.getProperty(key);

                int dotPos = key.indexOf('.');
                int dotPos2 = key.indexOf('.', dotPos + 1);

                String gvname = key.substring(dotPos + 1, dotPos2);
                int len = gvname.length();
                int versionPos = -1;

                for (int i = len - 1; i >= 0; i--) {
                    char ch = gvname.charAt(i);
                    if (!(ch >= '0' && ch <= '9')) {
                        versionPos = i + 1;
                        break;
                    }
                }

                String version = gvname.substring(versionPos);
                if (version.length() != 0) {
                    gvname = gvname.substring(0, versionPos - 3); // remove version and "Par"
                }
                ret.setProperty(gvcode, gvname);
            }
        }

        return ret;
    }
    
    public Konto findAccount(HBCIHandler handle) {
        Konto[] accounts= handle.getPassport().getAccounts();
        
        for (Konto account : accounts) {
            if (this.acct.getBankCode().equals(account.blz) &&
                    this.acct.getAccountNumber().equals(account.number))
                return account;
        }
        
        throw new IllegalStateException("Unable to find requested account " + this.acct.getAccountNumber());
    }

    public void acquireBalance() {
        HBCIHandler handle= this.createHbciHandler();
        
        Konto k= this.findAccount(handle);
        System.out.println("Have account: " + k);
        
        HBCIJob job= handle.newJob("SaldoReq");
        job.setParam("my", k);
        
        job.addToQueue();
        
        HBCIExecStatus ret= handle.execute();
        
        GVRSaldoReq result= (GVRSaldoReq)job.getJobResult();
        
        if (!result.isOK()) {
            throw new IllegalStateException("Fetching balance failed: " + result.getJobStatus().getErrorString() + " / " + result.getGlobStatus().getErrorString());
        }
        
        for (Info info : result.getEntries()) {
            if (!k.equals(info.konto)) continue;
            
            this.acct.getBalance().setAvailable(HbciMoney.fromValue(info.ready.value));
            this.acct.getBalance().setTimestamp(info.ready.timestamp);
        }
        
        System.out.println(result);
    }

    public void acquireTransactions() {
        HBCIHandler handle= this.createHbciHandler();
        
        Konto k= this.findAccount(handle);
        System.out.println("Using account " + k);
        
        HBCIJob job= handle.newJob("KUmsAll");
        job.setParam("my", k);
        
        job.addToQueue();
        
        HBCIExecStatus ret= handle.execute();
        
        GVRKUms result= (GVRKUms)job.getJobResult();
        if (!result.isOK()) {
            throw new IllegalStateException("Fetching balance failed: " + result.getJobStatus().getErrorString() + " / " + result.getGlobStatus().getErrorString());
        }
        
        List lines= result.getFlatData();
        for (Iterator i= lines.iterator(); i.hasNext(); ) {
            this.acct.addTransaction(HbciTransaction.fromUmsLine((UmsLine)i.next()));
        }
        
        System.out.println(result);
    }
}
