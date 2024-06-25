package io.sy.data.config;

import io.sy.metadata.client.MetadataClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class MetadataClientConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public MetadataClient metadataClient(HttpClient httpClient) {
        return new MetadataClient(httpClient);
    }
}
