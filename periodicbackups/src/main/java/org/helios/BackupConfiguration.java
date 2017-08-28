package org.helios;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ebranson1969 on 8/22/2017.
 */
public class BackupConfiguration {
    private String backupTargetDirectory = "G:/Minecraft/backups";
    private String backupSourceDirectory = "C:/Users/ebranson1969/AppData/Roaming/.minecraftServer_forge-1.12-14.21.1.2415";
    private String folderToBackup = "A Better Survival Server";

    private int count = 0;

    private List<Period> periods;
    private List<Backups> backups;

    @JsonIgnore
    private Snapshot snapshot = null;

    public BackupConfiguration() throws Exception
    {

        if(periods == null) {
            periods = new ArrayList<Period>();
            for (PeriodType p : PeriodType.values()) {
                switch (p) {
                    case START:
                        periods.add(new Period(PeriodType.START, true, 28));
                        break;
                    case TWENTYFOURHOURS:
                        periods.add(new Period(PeriodType.TWENTYFOURHOURS, false, 0));
                        break;
                    case TWELVEHOURS:
                        periods.add(new Period(PeriodType.TWELVEHOURS, false, 0));
                        break;
                    case EIGHTHOURS:
                        periods.add(new Period(PeriodType.EIGHTHOURS, true, 24));
                        break;
                    case FOURHOURS:
                        periods.add(new Period(PeriodType.FOURHOURS, true, 12));
                        break;
                    case TWOHOURS:
                        periods.add(new Period(PeriodType.TWOHOURS, false, 0));
                        break;
                    case HOURLY:
                        periods.add(new Period(PeriodType.HOURLY, true, 48));
                        break;
                    case QUARTERHOUR:
                        periods.add(new Period(PeriodType.QUARTERHOUR, true, 24));
                        break;
                    case TENMINUTES:
                        periods.add(new Period(PeriodType.TENMINUTES, false, 0));
                        break;
                    case FIVEMINUTES:
                        periods.add(new Period(PeriodType.FIVEMINUTES, false, 0));
                        break;
                    case TWOMINUTE30SECONDS:
                        periods.add(new Period(PeriodType.TWOMINUTE30SECONDS, true, 48));
                        break;
                    case MINUTE:
                        periods.add(new Period(PeriodType.MINUTE, false, 0));
                        break;
                    case THIRTYSECONDS:
                        periods.add(new Period(PeriodType.THIRTYSECONDS, false, 0));
                        break;

//                    // The item below are for testing purposes only, using them would probably greatly degrade system performance
//                    case TWENTYSECONDS:
//                        periods.add(new Period(PeriodType.TWENTYSECONDS, true, 4));
//                        break;
//                    case TENSECONDS:
//                        periods.add(new Period(PeriodType.TENSECONDS, true, 4));
//                        break;
//                    case FIVESECONDS:
//                        periods.add(new Period(PeriodType.FIVESECONDS, true, 4));
//                        break;
//                    case ONESECOND:
//                        periods.add(new Period(PeriodType.ONESECOND, false, 4));
//                        break;
                }
            }
        }

//        if(backups == null)
//            backups = new ArrayList<Backups>();
//        backups.add(new Backups(backupFolderName));
    }

    public String AddStartBackup() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        Date date = new Date();
        String backupFolderName = dateFormat.format(date);
        snapshot = new Snapshot(backupSourceDirectory, folderToBackup, backupTargetDirectory, backupFolderName);

        String lastBackup = null;

        //for(Period p : periods) {
        //    p.setLastBackup(0);
        //}

        if(backups == null)
            backups = new ArrayList<Backups>();
        backups.add(new Backups(backupFolderName));

        Period p = periods.get(0);
        lastBackup = snapshot.TakeSnapshot(folderToBackup + "_" + count);
        GetCurrentBackup().AddBackup(folderToBackup + "_" + count + ".zip", p.getPeriodType());
        p.AddToCount();
        count++;
        if(p.getCountOfBackupType() > p.getHowManyToKeep())
            DeleteBackup(p);

        return lastBackup;
    }
    public String AddBackup() throws Exception
    {
        String lastBackup = null;
        long now = System.currentTimeMillis() / 1000;
        boolean bFoundBackup = false;
        Period periodToDelete = null;
        for(Period p : periods)
        {
            if(p.getPeriodType() == PeriodType.START)
                continue;
            if (p.getTakeBackup()) {
                if (bFoundBackup) {
                    p.setLastBackup(now + p.getPeriodType().getSecondsPerPeriond());
                } else {
                    if (now >= p.getLastBackup()) {
                        bFoundBackup = true;
                        lastBackup = snapshot.TakeSnapshot(folderToBackup + "_" + count);
                        GetCurrentBackup().AddBackup(folderToBackup + "_" + count + ".zip", p.getPeriodType());
                        p.setLastBackup(now + p.getPeriodType().getSecondsPerPeriond());
                        p.AddToCount();
                        if(p.getCountOfBackupType() > p.getHowManyToKeep())
                            periodToDelete = p;
                        count++;
                    }
                }
            }
        }
        if(periodToDelete != null)
            DeleteBackup(periodToDelete);

        return lastBackup;
    }

    public void DeleteBackup(Period p) throws Exception
    {
        for(int i = 0; i < backups.size(); i++)
        {
            Backups bkups = backups.get(i);
            for(int j = 0; j < bkups.getArchives().size(); j++)
            {
                Backup bkup = bkups.getArchives().get(j);
                if(bkup.getPeriodType() == p.getPeriodType())
                {
                    Snapshot snapshotTemp = new Snapshot(backupSourceDirectory, folderToBackup, backupTargetDirectory, bkups.getBackupFolderName());
                    snapshotTemp.DeleteSnapshot(bkup.getBackupName());
                    bkups.getArchives().remove(j);
                    p.DeleteFromCount();
                    return;
                }
            }
        }

    }

    private Backups GetCurrentBackup()
    {
        return backups.get(backups.size()-1);
    }

    public String getBackupTargetDirectory() {
        return backupTargetDirectory;
    }
    public void setBackupTargetDirectory(String backupTargetDirectory) {
        this.backupTargetDirectory = backupTargetDirectory;
    }

    public String getBackupSourceDirectory() {
        return backupSourceDirectory;
    }
    public void setBackupSourceDirectory(String backupSourceDirectory) {
        this.backupSourceDirectory = backupSourceDirectory;
    }

    public String getFolderToBackup() {
        return folderToBackup;
    }
    public void setFolderToBackup(String folderToBackup) {
        this.folderToBackup = folderToBackup;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public List<Backups> getBackups() {
        return backups;
    }
    public void setBackups(List<Backups> backups) {
        this.backups = backups;
    }

    public List<Period> getPeriods() {
        return periods;
    }
    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public long GetSleep()
    {
        for(int i = periods.size() - 1; i >= 0; i--)
        {
            if(periods.get(i).getTakeBackup())
                return periods.get(i).getPeriodType().getSecondsPerPeriond() * 1000;
        }

        return 0;
    }
}
