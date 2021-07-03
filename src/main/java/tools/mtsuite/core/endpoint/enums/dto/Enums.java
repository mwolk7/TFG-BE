package tools.mtsuite.core.endpoint.enums.dto;

import tools.mtsuite.core.common.model.enums.*;

public enum Enums {
    BROWSERS(Browsers.class),
    DEVICES(Devices.class),
    OS(OS.class),
    PRIORITIES(Priorities.class),
    TESTCASES_STATUS(TestCaseStatus.class),
    ROLES(Roles.class),
    TESTSUITE_STATUS(TestSuiteStatus.class);

    private final Class value;

    Enums(Class value){
        this.value = value;
    }

    public Class getValue() {
        return this.value;
    }
}
