package tools.mtsuite.core.common.model.enums;

public enum OS implements IEnums {
 Windows("Windows"),
 MacOS("MacOS"),
 iOS("iOS"),
 Android("Android"),
 Other("Other");

 private final String value;

 OS(String value){
  this.value = value;
 }

 public String getValue() {
  return this.value;
 }

}
