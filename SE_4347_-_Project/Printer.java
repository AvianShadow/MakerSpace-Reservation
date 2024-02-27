package com.SQLInjection.Model;


public class Printer {
	private int pid;
	private int sid;
    private double filamentAmt;
    private String filament;
    
    public int getPid() {
        return pid;
    }
    
    public void setPid(int pid) {
        this.pid = pid;
    }
    
    public int getSid() {
        return sid;
    }
    
    public void setSid(int sid) {
        this.sid = sid;
    }
    
    public double getFilamentAmt() {
        return filamentAmt;
    }
    
    public void setFilamentAmt(double filamentAmt) {
        this.filamentAmt = filamentAmt;
    }
    
    public String getFilament() {
        return filament;
    }
    
    public void setFilament(String filament) {
        this.filament = filament;
    }
}
