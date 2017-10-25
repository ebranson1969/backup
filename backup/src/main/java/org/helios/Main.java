package org.helios;

import org.helios.BackupConfiguration;
import org.helios.BackupJSON;

import java.io.File;

/**
 * Created by ebranson1969 on 08/27/2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            BackupJSON backupJSON = new BackupJSON();
            BackupConfiguration backupConfiguration = backupJSON.getBackupConfiguration();
            backupJSON.SaveBackupConfiguration();

            String lastBackup = backupConfiguration.AddStartBackup();
            backupJSON.SaveBackupConfiguration();
            if(lastBackup != null)
                System.out.println("Backup occured: " + lastBackup);

            int count = 0;
            while (true) {
                lastBackup = backupConfiguration.AddBackup();
                backupJSON.SaveBackupConfiguration();
                if(lastBackup != null)
                    System.out.println("Backup occured: " + lastBackup);

                //Thread.sleep(backupConfiguration.GetSleep() - 1000);

                File kill = new File("kill");
                if(kill.exists()) {
                    kill.delete();
                    System.out.println("kill file found");
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        System.out.println("Backup session ended");
    }
}
