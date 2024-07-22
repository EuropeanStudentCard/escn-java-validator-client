package eu.escn.validator.client;

import eu.escn.validator.client.exceptions.ApiException;
import eu.escn.validator.client.exceptions.EscnClientException;
import eu.escn.validator.client.model.CardVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EscnClientFactoryIntegrationTest {

    @Test
    public void validateEscnShouldReturnValidResponseWhenProvidedEscnExists() throws Exception {
        CardVO returnTest = EscnClientFactory.create().validateEscn("cardNum1");
        Assertions.assertNotNull(returnTest);
    }

    @Test
    public void validateEscnShouldReturnAndExceptionWhenProvidedEscnDontExist() throws Exception {
        Assertions.assertThrows(ApiException.class, () -> EscnClientFactory.create().validateEscn("nonExistingId"));
    }

    @Test
    public void validateEscnShouldThrowAnEscnExceptionWhenHostIsInvalid() throws Exception {
        Assertions.assertThrows(EscnClientException.class, () -> EscnClientFactory.create("http://wrong.host").validateEscn("cardNum1"));
    }
}