package eu.escn.validator.client.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.escn.validator.client.exceptions.ApiException;
import eu.escn.validator.client.exceptions.ErrorResponse;
import eu.escn.validator.client.exceptions.EscnClientException;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class EscnRestRequestBuilder {

    private final CloseableHttpClient httpClient;
    private final ClassicRequestBuilder requestBuilder;
    private final URIBuilder uriBuilder;
    private final ObjectMapper objectMapper;

    public EscnRestRequestBuilder(String httpMethod, String url) throws URISyntaxException {
        this.httpClient = HttpClients.createDefault();
        this.uriBuilder = new URIBuilder(url);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        switch (httpMethod) {
            case "GET":
                this.requestBuilder = ClassicRequestBuilder.get();
                break;
            case "POST":
                this.requestBuilder = ClassicRequestBuilder.post();
                break;
            case "PUT":
                this.requestBuilder = ClassicRequestBuilder.put();
                break;
            case "DELETE":
                this.requestBuilder = ClassicRequestBuilder.delete();
                break;
            default:
                throw new IllegalArgumentException("HTTP method not supported");
        }
    }

    public EscnRestRequestBuilder addQueryParam(String param, Object value) {
        this.uriBuilder.addParameter(param, value.toString());
        return this;
    }

    public EscnRestRequestBuilder addQueryParams(Map<String, String> params) {
        params.forEach(this.uriBuilder::addParameter);
        return this;
    }

    public EscnRestRequestBuilder addPathSegments(String... segments) {
        for (String segment : segments) {
            this.uriBuilder.appendPathSegments(segment);
        }
        return this;
    }

    public EscnRestRequestBuilder addHeaders(Map<String, String> params) {
        params.forEach(this.requestBuilder::addHeader);
        return this;
    }

    public EscnRestRequestBuilder addHeaders(ContentType contentType, ContentType accept) {
        if (contentType != null) {
            this.requestBuilder.addHeader(HttpHeaders.CONTENT_TYPE, contentType.toString());
        }
        if (accept != null) {
            this.requestBuilder.addHeader(HttpHeaders.ACCEPT, accept.toString());
        }
        return this;
    }

    public EscnRestRequestBuilder addBody(Object body, ContentType contentType) throws IOException {
        if (body != null) {
            String jsonBody = objectMapper.writeValueAsString(body);
            StringEntity entity = new StringEntity(jsonBody, contentType);
            this.requestBuilder.setEntity(entity);
        }
        return this;
    }

    public <T> ESCExecute<T> buildRequest(TypeReference<T> responseType) {
        return () -> {
            try {
                URI uri = this.uriBuilder.build();
                this.requestBuilder.setUri(uri);
                return executeRequest(responseType, uri.toString());
            } catch (IOException | ParseException | URISyntaxException e) {
                throw new EscnClientException(e);
            }
        };
    }

    private <T> T executeRequest(TypeReference<T> responseType, String url) throws IOException, ParseException {

        HttpClientResponseHandler<T> responseHandler = response -> handleResponse(response, responseType, url);

        ClassicHttpRequest request = this.requestBuilder.build();

        return httpClient.execute(request, responseHandler);
    }

    private <T> T handleResponse(ClassicHttpResponse response, TypeReference<T> responseType, String url) throws IOException, ParseException {
        int statusCode = response.getCode();
        HttpEntity entity = response.getEntity();

        if (entity == null) {
            throw new EscnClientException("Response contains no content");
        }

        String responseBody = EntityUtils.toString(entity);

        if (statusCode == HttpStatus.SC_OK) {
            return objectMapper.readValue(responseBody, responseType);
        } else {
            return handleErrorResponse(responseBody, url);
        }
    }

    private <T> T handleErrorResponse(String responseBody, String url) throws IOException {
        ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
        throw new ApiException(errorResponse, url);
    }


    public interface ESCExecute<T> {
        T execute() throws IOException;
    }
}
