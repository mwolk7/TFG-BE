package tools.mtsuite.core.common.model.enums;

public enum Roles implements IEnums{
    Owner("Owner"),
    Developer("Developer"),
    Maintainer("Maintainer"),
    Reporter("Reporter");

    private final String value;

    Roles(String value){
     this.value = value;
    }

    public String getValue() {
     return this.value;
    }
}
