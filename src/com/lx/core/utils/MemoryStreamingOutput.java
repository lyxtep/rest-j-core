package com.lx.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author lx.ds
 */
public class MemoryStreamingOutput implements StreamingOutput {

    private final ByteArrayOutputStream stream;

    /**
     * File streaming output to send the memory stream to client
     * @param stream
     */
    public MemoryStreamingOutput(OutputStream stream) {
        this.stream = (ByteArrayOutputStream)stream;
    }

    @Override
    public void write(OutputStream output)
            throws IOException, WebApplicationException {
        
        try {
            IOUtils.copy(new ByteArrayInputStream(stream.toByteArray()), output);
        } catch (Exception e) {
            throw new WebApplicationException(e);
        } finally {
            if (output != null) output.close();
            stream.flush();
            stream.close();
        }
    }
}
