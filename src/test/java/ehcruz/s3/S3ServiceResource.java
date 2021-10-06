package ehcruz.s3;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class S3ServiceResource implements QuarkusTestResourceLifecycleManager {

    private final static String BUCKET_NAME = "quarkus.s3.example";
    private final DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:0.11.3");

    private LocalStackContainer localStackContainer;
    private S3Client s3Client;

    @Override
    public Map<String, String> start() {
        DockerClientFactory.instance().client();
        try {
            setUpLocalStack();
            s3Client = S3Client.builder()
                    .endpointOverride(new URI(endpoint()))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("accesskey", "secretKey")))
                    .httpClientBuilder(UrlConnectionHttpClient.builder())
                    .region(Region.US_EAST_1).build();

            s3Client.createBucket(b -> b.bucket(BUCKET_NAME));
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize S3 with localstack container", e);
        }
        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.s3.endpoint-override", endpoint());
        properties.put("quarkus.s3.aws.region", "us-east-1");
        properties.put("quarkus.s3.aws.credentials.type", "static");
        properties.put("quarkus.s3.aws.credentials.static-provider.access-key-id", "accessKey");
        properties.put("quarkus.s3.aws.credentials.static-provider.secret-access-key", "secretKey");
        properties.put("bucket.name", BUCKET_NAME);
        return properties;
    }

    @Override
    public void stop() {
        if (localStackContainer != null) {
            localStackContainer.close();
        }
    }
    private void setUpLocalStack() {
        localStackContainer = new LocalStackContainer(localstackImage)
                .withServices(Service.S3);
        localStackContainer.start();
    }

    private String endpoint() {
        return String.format("http://%s:%s", localStackContainer.getContainerIpAddress(), localStackContainer.getMappedPort(Service.S3.getPort()));
    }
}
