package ehcruz.s3.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@ApplicationScoped
public class S3Service {

    private final static String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @ConfigProperty(name = "bucket.name")
    String bucket;

    @Inject
    S3Client s3Client;

    public PutObjectResponse uploadFile(String fileName, InputStream content) {
        return s3Client.putObject(buildPutObjectRequest(fileName), RequestBody.fromFile(uploadToTemp(content)));
    }

    private PutObjectRequest buildPutObjectRequest(String fileName) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();
    }

    private File uploadToTemp(InputStream is) {
        File tempPath;
        try {
            tempPath = File.createTempFile("uploadS3Tmp", ".tmp");
            Files.copy(is, tempPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempPath;
    }

}
