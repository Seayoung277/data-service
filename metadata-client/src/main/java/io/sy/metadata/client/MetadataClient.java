package io.sy.metadata.client;

import io.sy.metadata.api.exception.HttpRequestException;
import io.sy.metadata.api.model.ObjectDefinition;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class MetadataClient {

    private final HttpClient client;

    public MetadataClient(HttpClient client) {
        this.client = Objects.requireNonNull(client);
    }

    public ObjectDefinition getObjectDefinition(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:6501/metadata/object/" + name))
                .header("Content-Type", "application/json")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return ObjectDefinition.fromJson(response.body());
            } else {
                throw new HttpRequestException(
                        String.format("Expecting status code [200], got [%s] with response body [%s]", response.statusCode(), response.body())
                );
            }
        } catch (IOException | InterruptedException ex) {
            throw new HttpRequestException(
                    String.format("Unexpected error during http callout: [%s]", ex.getMessage()), ex
            );
        }
    }
}
