package tools.mtsuite.core.common.model.enums;

public enum TestCaseStatus implements IEnums {
 Pass("Pass"),
 Fail("Fail"),
 Pending("Pending"),
 NA("Not Apply"), // No apply
 CNT("Could not test"); // could not test

 private final String value;

 TestCaseStatus(String value){
  this.value = value;
 }

 public String getValue() {
  return this.value;
 }


}
