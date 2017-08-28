package org.helios;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.helios.BackupConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by ebranson1969 on 08/26/2017.
 */
public class BackupJSON {
    private static String backupJSONName = "./config/backup.json";
    private BackupConfiguration backupConfiguration = null;


    public BackupJSON() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = null;
            File file = new File(backupJSONName);

            if(!file.exists()) {
                setBackupConfiguration(new BackupConfiguration());
                value = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getBackupConfiguration());
                FileUtils.writeStringToFile(file, value);
            }
            else {
                FileUtils.readFileToString(file, value);
                setBackupConfiguration(mapper.readValue(file, BackupConfiguration.class));
            }
        }
        catch(IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public BackupConfiguration getBackupConfiguration() {
        return backupConfiguration;
    }

    public void setBackupConfiguration(BackupConfiguration backupConfiguration) {
        this.backupConfiguration = backupConfiguration;
    }

    public void SaveBackupConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = null;
            File file = new File(backupJSONName);

            value = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getBackupConfiguration());
            FileUtils.writeStringToFile(file, value);
        }
        catch(IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
