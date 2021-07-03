package tools.mtsuite.core.common.model.enums;

public enum Browsers implements IEnums{
    Chrome("Chrome"),
    Safari("Safari"),
    Edge("Edge"),
    Firefox("Firefox"),
    Opera("Opera"),
    InternetExplorer("Internet Explorer"),
    Other("Other");

    private final String value;

    Browsers(String value){
     this.value = value;
    }

    public String getValue() {
     return this.value;
    }
}
