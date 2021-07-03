package tools.mtsuite.core.core.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.interceptor.access.HasAccess;
import tools.mtsuite.core.core.interceptor.acl.BaseAcl;
import tools.mtsuite.core.core.interceptor.visibility.Visibility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class CustomInterceptor implements HandlerInterceptor{

    @Autowired
    private InterceptorService interceptorService;

    @Autowired
    private AclFactory aclFactory;

    //This method is executed before accessing the interface. We only need to write the business logic to verify the login status here to verify the login status before the user calls the specified interface.
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handlerObj) throws Exception {
        if (handlerObj instanceof HandlerMethod) {
            HandlerMethod handler =  ((HandlerMethod) handlerObj) ;

         /**
          * HasAccess Interceptor -> check against the user permissions
          * @Param HasAccess.class
         **/
            if(handler.hasMethodAnnotation(HasAccess.class)){
                HasAccess hasAccess = handler.getMethodAnnotation(HasAccess.class);
                // Check login if you have login validation annotations
                if (null != hasAccess) {
                    Boolean accessAllowed = interceptorService.hasAccess();
                    //If session No, not logged in.
                    if (!accessAllowed) {
                        throw new BadRequestException("403", "You don't have access to this method.");
                    }
                }

            }

            /**
             * Visibility Interceptor -> check if the user has visibility to access the requested object.
             * @Param Visibility.class
             **/
            if(handler.hasMethodAnnotation(Visibility.class)){
                Visibility visibility = handler.getMethodAnnotation(Visibility.class);
                Boolean hasVisibility = false;

                if(!visibility.attr().isEmpty()) {
                    Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                    if (pathVariables.get(visibility.attr()) instanceof String){
                        Long attr = Long.valueOf((String) pathVariables.get(visibility.attr()));
                        Class<? extends BaseAcl> acl = visibility.acl();
                        BaseAcl aclFactored = aclFactory.getAcl(acl);
                        if(aclFactored == null){
                            throw new BadRequestException("400", "Acl factor error");
                        }
                        hasVisibility = aclFactored.checkEntityVisibility((attr));
                    } else {
                        throw new BadRequestException("400", "Bad format param attr");
                    }
                } else if(!visibility.req().isEmpty()){
                    return true;
                }
                if(!hasVisibility){
                    if(!visibility.injectable().equals("false")){
                        request.setAttribute(visibility.injectable(),true);
                    }else{
                        throw new BadRequestException("403", "You don't have access to this method.");
                    }

                }
            }
        }
        return true;
    }


    public void postHandle( HttpServletRequest request, HttpServletResponse response,
                            Object handlerObj, ModelAndView modelAndView) throws Exception {

    }
}
