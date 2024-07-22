package eu.escn.validator.client.model;

import java.time.LocalDate;

public class CardVO {

    private String cardNumber;
    private CodeVO cardType;
    private CodeVO cardStatusType;
    private LocalDate expiresAt;
    private PersonVO cardHolder;
    private OrganisationVO issuer;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CodeVO getCardType() {
        return cardType;
    }

    public void setCardType(CodeVO cardType) {
        this.cardType = cardType;
    }

    public CodeVO getCardStatusType() {
        return cardStatusType;
    }

    public void setCardStatusType(CodeVO cardStatusType) {
        this.cardStatusType = cardStatusType;
    }

    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDate expiresAt) {
        this.expiresAt = expiresAt;
    }

    public PersonVO getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(PersonVO cardHolder) {
        this.cardHolder = cardHolder;
    }

    public OrganisationVO getIssuer() {
        return issuer;
    }

    public void setIssuer(OrganisationVO issuer) {
        this.issuer = issuer;
    }
}
