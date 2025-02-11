package guru.springframework.creditcard.config;

import guru.springframework.creditcard.interceptors.EncryptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

// s@Configuration
public class InterceptorConfiguration implements HibernatePropertiesCustomizer {

//    @Autowired
//    private EncryptionInterceptor encryptionInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        //hibernateProperties.put("hibernate.session_factory.interceptor", encryptionInterceptor);
    }
}
