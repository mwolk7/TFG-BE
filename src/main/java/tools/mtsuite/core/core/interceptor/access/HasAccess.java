package tools.mtsuite.core.core.interceptor.access;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import tools.mtsuite.core.core.interceptor.CustomInterceptor;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JsonSubTypes.Type(CustomInterceptor.class)
public  @interface HasAccess {
    PermissionEntity entity();
    PermissionAction[] actions();
}
