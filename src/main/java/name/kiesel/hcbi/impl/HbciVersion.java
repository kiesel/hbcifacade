/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.kiesel.hcbi.impl;

/**
 *
 * @author alex
 */
public enum HbciVersion {
    V201(201),
    V210(210),
    V220(220),
    V300(300),
    PLUS("plus");
    
    private String version;
    
    private HbciVersion(int ordinal) {
        this(String.valueOf(ordinal));
    }
    
    private HbciVersion(String version) {
        this.version= version;
    }
    
    public String toParam() {
        return this.version;
    }
}
