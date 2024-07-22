package eu.escn.validator.client;

import com.fasterxml.jackson.core.type.TypeReference;
import eu.escn.validator.client.model.CardVO;
import eu.escn.validator.client.utils.EscnRestRequestBuilder;
import org.apache.hc.core5.http.ContentType;

import java.io.IOException;
import java.net.URISyntaxException;

public class EscnClientFactory {

    private final String host;

    private EscnClientFactory(String host) {
        this.host = host;
    }

    public static EscnClientFactory create() {
        return new EscnClientFactory("http://router.europeanstudentcard.eu");
    }

    public static EscnClientFactory create(String host) {
        return new EscnClientFactory(host);
    }

    /**
     * Validates the given ESCN (European Student Card Number).
     *
     * @param escn the ESCN to be validated.
     * @return a CardVO object containing the validation result.
     * @throws IOException if an I/O error occurs during the request.
     * @throws URISyntaxException if the URI is invalid.
     */
    public CardVO validateEscn(String escn) throws IOException, URISyntaxException {

        EscnRestRequestBuilder builder = new EscnRestRequestBuilder("GET", host.concat("/api/v1/cards/verify"));

        builder.addHeaders(null, ContentType.APPLICATION_JSON).addPathSegments(escn);

        return builder.buildRequest(new TypeReference<CardVO>() {}).execute();

    }

}