package org.helios;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ebranson1969 on 08/26/2017.
 */
public class Snapshot {
    String sourceDirectory = null;
    String sourceFolder = null;
    String targetDirectory = null;
    String targetFolder = null;

    private Snapshot(){}
    public Snapshot(String sourceDirectoryTemp, String sourceFolderTemp, String targetDirectorTemp, String targetFolderTemp) throws Exception
    {
        this.sourceDirectory = sourceDirectoryTemp;
        this.sourceFolder = sourceFolderTemp;
        this.targetDirectory = targetDirectorTemp;
        this.targetFolder = targetFolderTemp;

        if(!this.sourceDirectory.endsWith("/"))
            sourceDirectory = sourceDirectory + "/" + sourceFolder;
        else
            sourceDirectory = sourceDirectory + sourceFolder;
        File sourceDirectoryFile = new File(sourceDirectory);
        if(!sourceDirectoryFile.exists())
            throw new Exception("Source directory does not exist: " + sourceDirectory);

        if(!targetDirectory.endsWith("/"))
            targetDirectory = targetDirectory + "/" + sourceFolder + "/" + targetFolder;
        else
            targetDirectory = targetDirectory + sourceFolder + "/" + targetFolder;
        File targetDirectoryFile = new File(targetDirectory);
        if(!targetDirectoryFile.exists())
        {
            Path targetPath = Paths.get(targetDirectory);
            Files.createDirectories(targetPath);
        }
    }

    public String TakeSnapshot(String archive) throws Exception
    {
        String thisArchive = archive;
        if(!thisArchive.endsWith(".zip"))
            thisArchive = thisArchive + ".zip";

        String lastBackup = targetDirectory + "/" + thisArchive;
        FileOutputStream fOut = new FileOutputStream(new File(lastBackup));
        BufferedOutputStream bOut = new BufferedOutputStream(fOut);
        ZipArchiveOutputStream zOut = new ZipArchiveOutputStream(bOut);
        addFileToZip(zOut, sourceDirectory, "");

        zOut.finish();
        zOut.close();
        bOut.close();
        fOut.close();

        return lastBackup;
    }

    private void addFileToZip(ZipArchiveOutputStream tOut, String path, String base)
            throws IOException
    {
        File f = new File(path);
        String entryName = base + f.getName();
        ZipArchiveEntry tarEntry = new ZipArchiveEntry(f, entryName);
        tOut.putArchiveEntry(tarEntry);

        if (f.isFile()) {
            IOUtils.copy(new FileInputStream(f), tOut);
            tOut.closeArchiveEntry();
        } else {
            tOut.closeArchiveEntry();
            File[] children = f.listFiles();
            if (children != null) {
                for (File child : children) {
                    addFileToZip(tOut, child.getAbsolutePath(), entryName + "/");
                }
            }
        }
    }

    public void DeleteSnapshot(String archive)
    {
        String thisArchive = archive;
        if(!thisArchive.endsWith(".zip"))
            thisArchive = thisArchive + ".zip";

        thisArchive = targetDirectory + "/" + thisArchive;

        File archiveFile = new File(thisArchive);
        String archiveFilePath = archiveFile.getParent();
        if(archiveFile.exists())
            archiveFile.delete();

        File archiveFilePathFile = new File(archiveFilePath);
        if(archiveFilePathFile.list().length <= 0)
            archiveFilePathFile.delete();
    }
}
