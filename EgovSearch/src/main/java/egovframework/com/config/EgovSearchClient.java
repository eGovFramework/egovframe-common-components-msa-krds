package egovframework.com.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Setter
@Component
@Slf4j
public class EgovSearchClient {

	@Value("${opensearch.protocol}")
    public String protocol;

    @Value("${opensearch.url}")
    public String url;

    @Value("${opensearch.port}")
    public int port;

    @Value("${opensearch.username}")
    public String username;

    @Value("${opensearch.password}")
    public String password;

    @Value("${opensearch.keystore.path}")
    private String keystorePath;

    @Value("${opensearch.keystore.password}")
    private String keystorePassword;

    @Bean
    public OpenSearchClient openSearchClient() {
    	final HttpHost host = new HttpHost(protocol, url, port);
    	final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    	credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials(username, password.toCharArray()));

    	// HTTPS 통신을 위한 KeyStore 환경 설정
        KeyStore keyStore;
        // HTTPS 통신을 위한 SSLContext 환경 설정
        SSLContext sslContext;

        try {
            keyStore = KeyStore.getInstance("JKS"); // cacerts는 JKS 타입
            keyStore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
            sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
                    .build();
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to get KeyStore instance...");
        }

        // ObjectMapper 설정
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO 8601 형식 사용

        JacksonJsonpMapper jacksonJsonpMapper = new JacksonJsonpMapper(objectMapper);

        // Apache HttpClient 5의 Transport를 사용하기 위한 Builder
        final ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(host);
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            final TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
                    .setSslContext(sslContext)
                    .setHostnameVerifier(new NoopHostnameVerifier())
                    .build();
            final PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
                    .setTlsStrategy(tlsStrategy)
                    .build();
            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                    .setConnectionManager(connectionManager);
        });

        // Transport에 Jackson 설정 추가
        final OpenSearchTransport transport = builder.setMapper(jacksonJsonpMapper).build();

        return new OpenSearchClient(transport);
    }

}
