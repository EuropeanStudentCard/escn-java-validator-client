package eu.escn.validator.client.exceptions;

public class ApiException extends RuntimeException {
    private final ErrorResponse errorResponse;
    private final String url;

    public ApiException(ErrorResponse errorResponse, String url) {
        super(errorResponse.getMessage() + " (" + url + ")");
        this.errorResponse = errorResponse;
        this.url = url;
    }

    // Getters for errorResponse and url
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public String getUrl() {
        return url;
    }
}