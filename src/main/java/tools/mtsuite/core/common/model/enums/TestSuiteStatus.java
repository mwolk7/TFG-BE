package tools.mtsuite.core.common.model.enums;

public enum TestSuiteStatus implements IEnums{
 Pending("Pending"),
 Running("Running"),
 Waiting("Waiting"),
 Finished("Finished"),
 Cancelled("Cancelled");

 private final String value;

 TestSuiteStatus(String value){
  this.value = value;
 }

 public String getValue() {
  return this.value;
 }

}
