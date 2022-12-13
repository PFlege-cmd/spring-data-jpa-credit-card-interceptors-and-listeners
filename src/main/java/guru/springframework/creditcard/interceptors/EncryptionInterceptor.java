package guru.springframework.creditcard.interceptors;

import guru.springframework.creditcard.services.EncryptionService;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Component
public class EncryptionInterceptor extends EmptyInterceptor {

    public static final String ON_SAVE = "onSave";
    public static final String ON_FLUSH_DIRTY = "onFlushDirty";
    public static final String ON_LOAD = "onLoad";
    @Autowired
    private EncryptionService encryptionService;

    @Override public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        System.out.println("in OnFlushdirty");

        Object[] newState = processFields(entity, currentState, propertyNames, ON_FLUSH_DIRTY);

        return super.onFlushDirty(entity,id,newState, previousState, propertyNames, types);
    }

    @Override public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("in Onload");

        Object[] newState = processFields(entity, state, propertyNames, ON_LOAD);

        return super.onLoad(entity,id, newState, propertyNames, types);
    }

    @Override public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("in Onsave");

        Object[] newState = processFields(entity, state, propertyNames, ON_SAVE);

        return super.onSave(entity,id,newState, propertyNames, types);
    }

    private Object[] processFields(Object entity, Object[] state, String[] propertyNames, String type){
        var annotatedFields = getAnnotatedFields(entity);

        for (String field: annotatedFields){
            for (int i = 0; i < propertyNames.length; i++){
                if (field.equals(propertyNames[i])){
                    if (StringUtils.hasText(state[i].toString())) {
                        if (ON_SAVE.equals(type) || ON_FLUSH_DIRTY.equals(type)) {
                            state[i] = encryptionService.encrypt(state[i].toString());
                        } else if (ON_LOAD.equals(type)) {
                            state[i] = encryptionService.decrypt(state[i].toString());
                        }
                    }
                }
            }
        }
        return state;
    }

    private List<String> getAnnotatedFields(Object entity){
        List<String> annotatedFields = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()){
            if (!Objects.isNull(field.getAnnotation(EncryptedString.class))){
                annotatedFields.add(field.getName());
            }
        }
        return annotatedFields;
    }
}
