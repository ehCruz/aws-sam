package ehcruz.s3.service;

import ehcruz.s3.model.FileObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class FileService {

    @Inject
    S3Service s3Service;

    public boolean createAndSaveFile(FileObject fileObject) {
        InputStream is = new ByteArrayInputStream(fileObject.getContent().getBytes(StandardCharsets.UTF_8));
        return s3Service.uploadFile(fileObject.getKey(), is) != null;
    }
}
