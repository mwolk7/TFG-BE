package tools.mtsuite.core.core.keycloakSync.dto;


public enum EventEnum {
    // USER
    USER("USER"),
    // GROUP
    GROUP("GROUP"),
    // FULL
    FULL("FULL"),
    // CLIENT
    LOGIN("LOGIN"),
    LOGOUT("LOGOUT"),
    UPDATE_EMAIL("UPDATE_EMAIL"),
    UPDATE_PROFILE("UPDATE_PROFILE"),
    UPDATE_PASSWORD("UPDATE_PASSWORD"),
    CLIENT_REGISTER("CLIENT_REGISTER"),
    CLIENT_UPDATE("CLIENT_UPDATE"),
    CLIENT_DELETE("CLIENT_DELETE"),
    REFRESH_TOKEN("REFRESH_TOKEN"),
    RESTART_AUTHENTICATION("RESTART_AUTHENTICATION"),
    TOKEN_EXCHANGE("TOKEN_EXCHANGE");

    private String sigla;

    private EventEnum(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }
}
