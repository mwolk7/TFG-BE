package tools.mtsuite.core.core.keycloakSync.dto;


public class Group {

    String id;
    String codice;
    String codiceParent;
    String description;
    String createdTimestamp; //Data_ora_inserimento
    String modifyTimestamp; //Data_ora_ultima_modifica

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCodiceParent() {
        return codiceParent;
    }

    public void setCodiceParent(String codiceParent) {
        this.codiceParent = codiceParent;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getModifyTimestamp() {
        return modifyTimestamp;
    }

    public void setModifyTimestamp(String modifyTimestamp) {
        this.modifyTimestamp = modifyTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", codice='" + codice + '\'' +
                ", codiceParent='" + codiceParent + '\'' +
                ", createdTimestamp='" + createdTimestamp + '\'' +
                ", modifyTimestamp='" + modifyTimestamp + '\'' +
                '}';
    }
}
