package org.helios;

import java.util.Date;

/**
 * Created by ebranson1969 on 8/21/2017.
 */
public class Period {
    private PeriodType periodType = PeriodType.START;
    private boolean takeBackup = true;
    private int howManyToKeep = 28;
    private int countOfBackupType = 0;
    private long lastBackup = 0;

    public Period(){};
    public Period(PeriodType periodType,  boolean takeBackup, int howManyToKeep)
    {
        this.periodType = periodType;
        this.takeBackup = takeBackup;
        this.howManyToKeep = howManyToKeep;
    }

    public void AddToCount()
    {
        countOfBackupType++;
    }
    public void DeleteFromCount()
    {
        countOfBackupType--;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }
    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public boolean getTakeBackup() {
        return takeBackup;
    }
    public void setTakeBackup(boolean takeBackup) {
        this.takeBackup = takeBackup;
    }

    public int getHowManyToKeep() {
        return howManyToKeep;
    }
    public void setHowManyToKeep(int howManyToKeep) {
        this.howManyToKeep = howManyToKeep;
    }

    public int getCountOfBackupType() {
        return countOfBackupType;
    }
    public void setCountOfBackupType(int countOfBackupType) {
        this.countOfBackupType = countOfBackupType;
    }

    public long getLastBackup() {
        return lastBackup;
    }
    public void setLastBackup(long lastBackup) {
        this.lastBackup = lastBackup;
    }

}
