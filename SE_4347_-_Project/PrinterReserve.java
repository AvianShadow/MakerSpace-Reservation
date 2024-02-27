package com.SQLInjection.Model;

public class PrinterReserve {
	private int pid;
	private int sid;
    private double filamentAmt;
    private String filament;
	private String name;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
    
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

