//package residentevil.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import residentevil.interceptors.PreAuthenticateInterceptor;
//
//@Configuration
//public class WebMvcConfiguration implements WebMvcConfigurer {
//
//    private PreAuthenticateInterceptor preAuthenticateInterceptor;
//
//    @Autowired
//    public WebMvcConfiguration(PreAuthenticateInterceptor preAuthenticateInterceptor) {
//        this.preAuthenticateInterceptor = preAuthenticateInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(this.preAuthenticateInterceptor);
//    }
//}
