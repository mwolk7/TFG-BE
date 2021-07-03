package tools.mtsuite.core.common.model.enums;

public enum Devices implements IEnums{
 Desktop("Desktop"),
 Mobile("Mobile"),
 Tablet("Tablet"),
 TV("TV"),
 Other("Other");

 private final String value;

 Devices(String value){
  this.value = value;
 }

 public String getValue() {
  return this.value;
 }
}
