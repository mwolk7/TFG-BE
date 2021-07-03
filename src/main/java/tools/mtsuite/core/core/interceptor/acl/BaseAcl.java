package tools.mtsuite.core.core.interceptor.acl;

public abstract class BaseAcl {
    public abstract Boolean checkEntityVisibility(Long id);
    public abstract Boolean checkEntityVisibility(Object obj);
}
