package tricolor.no1.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tricolor.no1.Handler.LoginHandler;

//@Configuration
public class HandlerConfig implements WebMvcConfigurer {



    public void addInterceptors(InterceptorRegistry registry)
    {
           registry.addInterceptor(new LoginHandler()).addPathPatterns("/**");
    }

}
