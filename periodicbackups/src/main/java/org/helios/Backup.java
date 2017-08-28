package org.helios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ebranson1969 on 8/21/2017.
 */
public class Backup {
    private String backupName;
    private PeriodType periodType;
    private String dateTime;

    private Backup() {
    }
    private Backup(String backupName, PeriodType periodType, String dateTime) {
        this.backupName = backupName;
        this.periodType = periodType;
        this.dateTime = dateTime;
    }

    public Backup(String backupName, PeriodType periodType) {
        this.backupName = backupName;
        this.periodType = periodType;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        dateTime = dateFormat.format(date);
    }

    public String getBackupName() {
        return backupName;
    }
    public void setBackupName(String backupName) {
        this.backupName = backupName;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }
    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
