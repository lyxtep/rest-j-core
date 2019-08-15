package com.lx.core.controller;

import com.lx.core.configuration.LXConfig;
import com.lx.core.infrastructure.extensions.ModuleExtensions;
import com.lx.core.repository.common.UidGenerator;
import com.lx.core.repository.entity.LXFile;
import com.lx.core.repository.generic.GenericRepository;
import com.lx.core.repository.generic.RepositoryFactory;
import com.lx.core.response.FileDownloadResponse;
import com.lx.core.response.FileUploadResponse;
import com.lx.core.security.AuthenticatedUser;
import com.lx.core.service.FileService;
import com.lx.core.service.generic.GenericService;
import com.lx.core.utils.StringUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author lx.ds
 */
@Path("/file")
public class FileCtrl {
    
    private @Context UriInfo uri;
    private @Context SecurityContext sc;

    public FileCtrl() {
        //super(LXFile.class);
    }
    
    protected AuthenticatedUser getLoggedUser() {
        return (AuthenticatedUser)sc.getUserPrincipal();
    }

    @GET
    @Path("/{id}/download")
    public Response download(@PathParam("id") String id, @QueryParam("inline") boolean inline) throws Exception {
        
        //try {
            FileDownloadResponse theFileResponse = FileService.getInstance().getFile(id, getLoggedUser());
            
            return Response.ok(theFileResponse.getStream())//"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", String.format("%s; filename=\"%s\"", inline ? "inline" : "attachment", theFileResponse.getFileName()))
                .header("Content-type", "application/pdf")
                .build();
        /*} catch (Exception ex) {
            Logger.getLogger(FileCtrl.class.getName()).log(Level.SEVERE, null, ex);
            throw new HTTPException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }*/
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public void upload(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
        
        String sResponse = "";
        String responseType = null;
        String domain = null;
        //List<FileUploadResponse> response = new ArrayList<>();
        File tempLocation = LXConfig.getInstance().getUploadTempLocation();

        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        final List<FileItem> items = upload.parseRequest(request);
        if (items != null) {
            
            final Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                final FileItem item = iter.next();
                //final String fieldName = item.getFieldName();
                //final String fieldValue = item.getString();

                if ("domain".equalsIgnoreCase(item.getFieldName())) {
                    domain = item.getString();
                }
                
                if ("responseType".equalsIgnoreCase(item.getFieldName())) {
                    responseType = item.getString();
                }
                
                if (!item.isFormField()) {
                    
                    if (StringUtils.isNullOrEmpty(item.getName()) || item.getName().equals("undefined") || item.getName().equals("blob")){
                        continue;
                    }
                    
                    // file field
                    FileUploadResponse aFileResponse = FileService.getInstance().saveFileToTemp(item, tempLocation);
                    //response.add(aFileResponse);
                    
                    if (responseType != null && "json".equalsIgnoreCase(responseType)) {
                        sResponse = String.format("{\"originalFilename\":\"%s\",\"filename\":\"%s\",\"size\":%s}",
                            aFileResponse.getOriginalFilename(),
                            aFileResponse.getFilename(),
                            aFileResponse.getSize()
                        );
                    } else {
                        sResponse = String.format("<script type=\"text/javascript\">(function(){window.parent.postMessage({"
                            + "\"originalFilename\":\"%s\",\"filename\":\"%s\",\"size\":%s"
                            + "},\"%s\")})();</script>",
                        aFileResponse.getOriginalFilename(),
                        aFileResponse.getFilename(),
                        aFileResponse.getSize(),
                        domain
                    );
                    }
                    
                }

            }
        }
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,HEAD,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,Authentication");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setStatus(200);
        response.getWriter().write(sResponse);
        response.getWriter().flush();
        response.getWriter().close();
        //return response;
        
        //return Response.ok(sResponse).build();
    }
    
    
    @POST
    @Path("/2")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String upload2(@FormDataParam("file") InputStream uploadedInputStream,
                             @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {
        String sResponse = "";
        File tempLocation = LXConfig.getInstance().getUploadTempLocation();
        
        // file field
        FileUploadResponse aFileResponse = FileService.getInstance().saveFileToTemp(uploadedInputStream, fileDetail, tempLocation);
        //response.add(aFileResponse);

        sResponse = String.format("{"
                + "\"originalFilename\":\"%s\",\"filename\":\"%s\",\"size\":%s"
                + "}",
            aFileResponse.getOriginalFilename(),
            aFileResponse.getFilename(),
            aFileResponse.getSize()
        );
        
        return sResponse;
    }
    
    @POST
    @Path("/save")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadAndSave(@FormDataParam("file") InputStream uploadedInputStream,
                             @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {
        
        File tempLocation = LXConfig.getInstance().getUploadTempLocation();
        LXFile theLxFile = new LXFile();
        String sResponse;
        File file;
        FileUploadResponse aFileResponse;
        
        // file field
        aFileResponse = FileService.getInstance().saveFileToTemp(uploadedInputStream, fileDetail, tempLocation);
        // move file from temp folder to repository
        file = FileService.getInstance().moveFileFromTempToRepository(aFileResponse.getFilename());

        theLxFile.setName(aFileResponse.getFilename());
        theLxFile.setOriginalName(aFileResponse.getOriginalFilename());
        theLxFile.setSize(file.length());

        //GenericService.getInstance().create(theLxFile, LXFile.class, user);

        sResponse = String.format("{"
                + "\"originalFilename\":\"%s\",\"filename\":\"%s\",\"size\":%s"
                + "}",
            aFileResponse.getOriginalFilename(),
            aFileResponse.getFilename(),
            aFileResponse.getSize()
        );
        
        return sResponse;
    }
}
