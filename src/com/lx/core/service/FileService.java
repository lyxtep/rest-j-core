package com.lx.core.service;

import com.lx.core.configuration.LXConfig;
import com.lx.core.repository.common.UidGenerator;
import com.lx.core.repository.entity.LXFile;
import com.lx.core.response.FileDownloadResponse;
import com.lx.core.response.FileUploadResponse;
import com.lx.core.security.IUserIdentity;
import com.lx.core.service.generic.GenericService;
import com.lx.core.utils.FileStreamingOutput;
import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author lx.ds
 */
public class FileService {

    // Singleton pattern
    private static FileService instance = null;

    // Singleton pattern
    private FileService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized FileService getInstance() {
        if (instance == null) {
            instance = new FileService();
        }

        return instance;
    }
    
    public synchronized FileDownloadResponse getFile(String id, IUserIdentity user) throws Exception {
        FileDownloadResponse result = new FileDownloadResponse();
        String folder = LXConfig.getInstance().getRepository();
        
        LXFile fileData = GenericService.getInstance().getById(LXFile.class, id, user);
        if (fileData == null) {
            throw new FileNotFoundException("Invalid id of the file");
        }
        result.setFileName(fileData.getOriginalName());
        
        File theFile = new File(folder, fileData.getName());
        if (!theFile.exists()) {
            throw new FileNotFoundException();
        }
        result.setStream(new FileStreamingOutput(theFile, false));
        
        return result;
    }
    
    public synchronized FileUploadResponse saveFileToTemp(FileItem srcFile, File destDir) throws Exception {
        FileUploadResponse aFileData = new FileUploadResponse();
        int idx = srcFile.getName().lastIndexOf("\\");
        
        // file field
        final File file = new File(destDir, UidGenerator.getInstance().getUid());
        srcFile.write(file);
        
        aFileData.setFilename(file.getName());
        aFileData.setOriginalFilename(idx == -1 ? srcFile.getName() : srcFile.getName().substring(idx + 1));
        aFileData.setSize(file.length());
        
        return aFileData;
    }
    
    public synchronized FileUploadResponse saveFileToTemp(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, File destDir) throws Exception {
        FileUploadResponse aFileData = new FileUploadResponse();
        int idx = fileDetail.getFileName().lastIndexOf("\\");
        
        // file field
        final File file = new File(destDir, UidGenerator.getInstance().getUid());
        
        OutputStream out;
        int read = 0;
        byte[] bytes = new byte[1024];

        out = new FileOutputStream(file);
        while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
        
        aFileData.setFilename(file.getName());
        aFileData.setOriginalFilename(idx == -1 ? fileDetail.getFileName() : fileDetail.getFileName().substring(idx + 1));
        aFileData.setSize(file.length());
        
        return aFileData;
    }
    
    public synchronized File moveFileFromTempToRepository(String file) throws Exception {
        
        File theFile = new File(LXConfig.getInstance().getUploadTempLocation(), file);

        if (!theFile.exists() || !theFile.isFile()) {
            throw new FileNotFoundException("Invalid temporary location or filename");
        }
        
        FileUtils.moveFileToDirectory(theFile, LXConfig.getInstance().getRepositoryLocation(), false);
        
        return new File(LXConfig.getInstance().getRepositoryLocation(), file);
    }
    
    public synchronized File moveFileFromRepositoryToDeleted(String file) throws Exception {
        
        File theFile = new File(LXConfig.getInstance().getRepositoryLocation(), file);

        if (!theFile.exists() || !theFile.isFile()) {
            throw new FileNotFoundException("Invalid temporary location or filename");
        }
        
        FileUtils.moveFileToDirectory(theFile, LXConfig.getInstance().getDeletedRepLocation(), false);
        
        return new File(LXConfig.getInstance().getDeletedRepLocation(), file);
    }
    
    public synchronized void deleteFileFromRepository(String file) throws Exception {
        
        File theFile = new File(LXConfig.getInstance().getRepositoryLocation(), file);

        if (theFile.exists() && theFile.isFile()) {
            FileUtils.deleteQuietly(theFile);
        }
    }
}
