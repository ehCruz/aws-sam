package ehcruz.s3;

import ehcruz.s3.model.FileObject;
import ehcruz.s3.service.FileService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/s3")
public class S3SyncClientResource {

    @Inject
    FileService fileService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hello(FileObject fileObject) {
        if (fileService.createAndSaveFile(fileObject)) {
            return Response.created(URI.create(fileObject.getKey())).build();
        }
        return Response.serverError().build();
    }
}