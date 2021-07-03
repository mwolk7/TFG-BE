package tools.mtsuite.core.core.keycloakSync.dto;


public class Role {

    String id;
    String codice;

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

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", codice='" + codice + '\'' +
                '}';
    }
}
