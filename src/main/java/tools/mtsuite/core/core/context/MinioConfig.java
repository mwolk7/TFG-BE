package tools.mtsuite.core.core.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.minio.MinioClient;

@Configuration
@EnableTransactionManagement
@Scope("singleton")
public class MinioConfig {

    @Value("${it.atscom.epec.core.minio.protocol}")
    private String minioProtocol;
    @Value("${it.atscom.epec.core.minio.URL}")
    private String minioUrl;
    @Value("${it.atscom.epec.core.minio-port}")
    private String minioPort;
    @Value("${it.atscom.epec.core.minio-user}")
    private String minioUser;
    @Value("${it.atscom.epec.core.minio-pass}")
    private String minioPass;

    @Bean(name="initMinioClient")
    public MinioClient generateMinioClient() {
        try {
            String minioURL = minioProtocol+ "://"+minioUrl+":"+minioPort;
            MinioClient client = new MinioClient(minioURL, minioUser, minioPass);
            //Delete this method if u change to HTTPS
            client.ignoreCertCheck();
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
	
	
	@Bean(name="testMinioClient")
    public MinioClient playMinioClient() {
        try {
            MinioClient client = new MinioClient("https://play.minio.io:9000", "Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
	
	
}
