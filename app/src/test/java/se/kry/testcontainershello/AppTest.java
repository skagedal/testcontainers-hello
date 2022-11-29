package se.kry.testcontainershello;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMSAsyncClient;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void testcontainers_mysql() {
        // Start a container
        try (final var container = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.31"))) {
            container.start();

            String query = "SELECT version()";

            final var connection = DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword());
            final var statement = connection.createStatement();
            final var resultSet = statement.executeQuery(query);

            assertTrue(resultSet.next());
            final var version = resultSet.getString(1);
            System.out.println("Version is: " + resultSet.getString(1));
            assertEquals("8.0.31", version);
        } catch (Exception exception) {
            System.err.println("We failed:");
            System.err.println(exception.getMessage());
            fail(exception);
        }
    }

    @Test
    void testcontainers_localstack_s3() {
        DockerImageName dockerImageName = DockerImageName.parse("localstack/localstack:0.11.3");
        try (final var localstack = new LocalStackContainer(dockerImageName)
            .withServices(LocalStackContainer.Service.S3)) {
            localstack.start();

            S3Client s3 = S3Client
                .builder()
                .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.S3))
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())
                    )
                )
                .region(Region.of(localstack.getRegion()))
                .build();

            assertEquals(List.of(), s3.listBuckets().buckets());
        } catch (Exception exception) {
            System.err.println("We failed:");
            System.err.println(exception.getMessage());
            fail(exception);
        }
    }

    @Test
    void testcontainers_localstack_kms() {
        DockerImageName dockerImageName = DockerImageName.parse("localstack/localstack:0.11.3");
        try (final var localstack = new LocalStackContainer(dockerImageName)
            .withServices(LocalStackContainer.Service.KMS)
            .withClasspathResourceMapping("kms-seed.yaml", "/init/seed.yaml", BindMode.READ_ONLY)) {
            localstack.start();

            final var kmsClient = AWSKMSAsyncClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("foo", "bar")))
                .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                        "http://localhost:" + localstack.getFirstMappedPort(), Regions.EU_WEST_1.getName()))
                .build();

            System.out.println(kmsClient.createKey().getKeyMetadata());
        } catch (Exception exception) {
            System.err.println("We failed:");
            System.err.println(exception.getMessage());
            fail(exception);
        }
    }

}
