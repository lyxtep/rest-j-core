package com.lx.core.report;

import com.lx.core.utils.FileStreamingOutput;
import com.lx.core.utils.MemoryStreamingOutput;
import com.lx.core.utils.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author lx.ds
 */
public class ReportUtils {

    // Singleton pattern
    private static ReportUtils instance = null;
    
    // Singleton pattern
    private ReportUtils() {
        
    }

    // Singleton pattern
    public static synchronized ReportUtils getInstance() {
        if (instance == null) {
            instance = new ReportUtils();
        }

        return instance;
    }
    
    public Response getFileResponse(String fileType, String fileName, ITemplate template) throws Exception {
        return getFileResponse(fileType, fileName, "attachment", template);
    }
    
    public Response getFileResponse(String fileType, String fileName, String responseType, ITemplate template) throws Exception {
        
        StreamingOutput stream;
        String dispositionType;
        String contentType;
        
        if ("pdf".equalsIgnoreCase(fileType)) {
            
            stream = new MemoryStreamingOutput(template.saveAsPdf());
            contentType = "application/pdf";
            
        } else if ("doc".equalsIgnoreCase(fileType)) {
            
            stream = new MemoryStreamingOutput(template.saveAsDoc());
            contentType = "application/msword";
            
        } else if ("docx".equalsIgnoreCase(fileType)) {
            
            stream = new MemoryStreamingOutput(template.saveAsDoc());
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        if ("attachment".equalsIgnoreCase(responseType) || StringUtils.isNullOrEmpty(responseType)) {
            dispositionType = "attachment";
        } else if ("inline".equalsIgnoreCase(responseType)) {
            dispositionType = "inline";
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
            
        return Response.ok(stream)
                .header("Content-Disposition", String.format("%s; filename=\"%s\"", dispositionType, fileName))
                .header("Content-type", contentType)
                .build();
    }
    
    public Response getFileResponse(File file, String responseType) throws Exception {
        String dispositionType;
        String contentType;
        
        if (!file.exists()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        if (file.getName().endsWith("pdf")) {
            
            contentType = "application/pdf";
            
        } else if (file.getName().endsWith("doc")) {
            
            contentType = "application/msword";
            
        } else if (file.getName().endsWith("docx")) {
            
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        if ("attachment".equalsIgnoreCase(responseType) || StringUtils.isNullOrEmpty(responseType)) {
            dispositionType = "attachment";
        } else if ("inline".equalsIgnoreCase(responseType)) {
            dispositionType = "inline";
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
            
        return Response.ok(new FileStreamingOutput(file, false))
                .header("Content-Disposition", String.format("%s; filename=\"%s\"", dispositionType, file.getName()))
                .header("Content-type", contentType)
                .build();
    }
    
    public void saveToFile(ITemplate template, File file) throws Exception {
        
        try (FileOutputStream output = new FileOutputStream(file)) {
            
            ByteArrayOutputStream stream;
            
            if (file.getName().endsWith("pdf")) {
                
                stream = (ByteArrayOutputStream)template.saveAsPdf();
                
            } else if (file.getName().endsWith("doc")) {
                
                stream = (ByteArrayOutputStream)template.saveAsDoc();
                
            } else if (file.getName().endsWith("docx")) {
                
                stream = (ByteArrayOutputStream)template.saveAsDoc();
                
            } else {
                throw new Exception("Invalid export file extension!");
            }
            
            IOUtils.copy(new ByteArrayInputStream(stream.toByteArray()), output);
            
            output.flush();
        }
    }
}
