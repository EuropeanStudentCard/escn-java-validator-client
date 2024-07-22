package eu.escn.validator.client.model;

public class OrganisationVO {

    private String identifier;
    private CodeVO identifierType;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public CodeVO getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(CodeVO identifierType) {
        this.identifierType = identifierType;
    }
}