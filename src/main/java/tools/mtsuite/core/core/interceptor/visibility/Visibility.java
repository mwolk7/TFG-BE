package tools.mtsuite.core.core.interceptor.visibility;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import tools.mtsuite.core.core.interceptor.CustomInterceptor;
import tools.mtsuite.core.core.interceptor.acl.BaseAcl;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JsonSubTypes.Type(CustomInterceptor.class)
public  @interface Visibility {
    String attr() default "";
    String req() default "";
    String injectable() default "false";
    Class<? extends BaseAcl> acl() default BaseAcl.class;
    Class<? extends Object> obj() default Object.class;

}
