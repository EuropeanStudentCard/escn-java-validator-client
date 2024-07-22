package eu.escn.validator.client.exceptions;

public class EscnClientException extends RuntimeException {

    public EscnClientException(String message) {
        super(message);
    }

    public EscnClientException(Exception e) {
        super(e);
    }

}
