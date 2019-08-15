
package com.lx.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author lx.ds
 */
public class FileStreamingOutput implements StreamingOutput {

    private final File file;
    private final boolean doDelete;

    /**
     * File streaming output to send the file to client
     * @param file
     * @param delete true to delete file after send
     */
    public FileStreamingOutput(File file, boolean delete) {
        this.file = file;
        this.doDelete = delete;
    }

    @Override
    public void write(OutputStream output)
            throws IOException, WebApplicationException {
        FileInputStream input = new FileInputStream(file);
        try {
            int bytes;
            while ((bytes = input.read()) != -1) {
                output.write(bytes);
            }
        } catch (Exception e) {
            throw new WebApplicationException(e);
        } finally {
            if (output != null) output.close();
            input.close();
        }
        
        if (doDelete){
            try {
                file.delete();
            } catch (Exception e) {
                throw new WebApplicationException(e);
            }
        }
    }
}
