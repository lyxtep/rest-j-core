package com.lx.core.response;

import com.lx.core.utils.FileStreamingOutput;

/**
 *
 * @author lx.ds
 */
public class FileDownloadResponse {

    private FileStreamingOutput stream;
    
    private String fileName;

    /**
     * @return the stream
     */
    public FileStreamingOutput getStream() {
        return stream;
    }

    /**
     * @param stream the stream to set
     */
    public void setStream(FileStreamingOutput stream) {
        this.stream = stream;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
