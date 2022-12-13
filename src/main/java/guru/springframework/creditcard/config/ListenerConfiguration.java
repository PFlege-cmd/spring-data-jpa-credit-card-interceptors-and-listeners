package guru.springframework.creditcard.config;

import guru.springframework.creditcard.listeners.PostLoadListener;
import guru.springframework.creditcard.listeners.PreInsertListener;
import guru.springframework.creditcard.listeners.PreUpdateListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

//@Component
public class ListenerConfiguration implements BeanPostProcessor {

    PreInsertListener preInsertListener;
    PreUpdateListener preUpdateListener;
    PostLoadListener postLoadListener;

    public ListenerConfiguration(PreInsertListener preInsertListener, PreUpdateListener preUpdateListener, PostLoadListener postLoadListener) {
        this.preInsertListener = preInsertListener;
        this.preUpdateListener = preUpdateListener;
        this.postLoadListener = postLoadListener;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof LocalContainerEntityManagerFactoryBean){
//            LocalContainerEntityManagerFactoryBean lemf =  (LocalContainerEntityManagerFactoryBean) bean;
//            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) lemf.getNativeEntityManagerFactory();
//            EventListenerRegistry registry = sessionFactory
//                    .getServiceRegistry()
//                    .getService(EventListenerRegistry.class);
//
//            registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
//            registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
//            registry.appendListeners(EventType.POST_LOAD, postLoadListener);
        }

        return bean;
    }
}
