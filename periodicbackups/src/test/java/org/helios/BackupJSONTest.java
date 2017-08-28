package org.helios;

import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by ebranson1969 on 08/26/2017.
 */
public class BackupJSONTest {
    @Test
    public void saveBackupConfiguration() throws Exception {

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

                Thread.sleep(backupConfiguration.GetSleep() - 1000);

                File kill = new File("kill");
                if(kill.exists()) {
                    kill.delete();
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

    }

}