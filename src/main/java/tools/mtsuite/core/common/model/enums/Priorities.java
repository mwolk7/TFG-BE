package tools.mtsuite.core.common.model.enums;

public enum Priorities implements IEnums {
 Critical("Critical"),
 High("High"),
 Medium("Medium"),
 Low("Low");

 private final String value;

 Priorities(String value){
  this.value = value;
 }

 public String getValue() {
  return this.value;
 }

}
