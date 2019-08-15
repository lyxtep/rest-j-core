
package com.lx.core.report;

import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public interface ITemplate {
    
    OutputStream saveAsPdf() throws Exception;

    OutputStream saveAsDoc() throws Exception;

    <TData extends Object> ITemplate fill(String key, TData data);

    <TData extends Object> ITemplate fill(String key, Class<TData> clazz, List<TData> data);

    void setValue(String name, String value);
}
