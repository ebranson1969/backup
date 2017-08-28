package org.helios;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebranson1969 on 8/21/2017.
 */
public class Backups {
    private String backupFolderName;
    private List<Backup> archives;

    private Backups(){}
    public Backups(String backupFolderName, List<Backup> archives) {
        this.backupFolderName = backupFolderName;
        this.archives = archives;
    }
    public Backups(String backupFolderName)
    {
        this.backupFolderName = backupFolderName;
    }

    public String getBackupFolderName() {
        return backupFolderName;
    }
    public void setBackupFolderName(String backupFolderName) {
        this.backupFolderName = backupFolderName;
    }

    public List<Backup> getArchives() {
        return archives;
    }
    public void setArchives(List<Backup> archives) {
        this.archives = archives;
    }

    public void AddBackup(String backupName, PeriodType periodType)
    {
        if(archives == null) {
            archives = new ArrayList<Backup>();
            archives.add(new Backup(backupName, PeriodType.START));
        }
        else {
            archives.add(new Backup(backupName, periodType));
        }
    }

}
