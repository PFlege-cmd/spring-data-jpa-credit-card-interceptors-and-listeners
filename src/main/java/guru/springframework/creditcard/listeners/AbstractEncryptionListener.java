package guru.springframework.creditcard.listeners;

import guru.springframework.creditcard.interceptors.EncryptedString;
import guru.springframework.creditcard.services.EncryptionService;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class AbstractEncryptionListener {

    private final EncryptionService encryptionService;

    public AbstractEncryptionListener(EncryptionService encryptionService){
        this.encryptionService = encryptionService;
    }

    public void encrypt(Object[] state, String[] propertyNames, Object entity){
        ReflectionUtils.doWithFields(entity.getClass(),
                field -> encryptField(field, state, propertyNames),
                 this::isFieldEncrypted);
    }

    private void encryptField(Field field, Object[] state, String[] propertyNames){
        int idx = getPropertyIndex(field, propertyNames);
        Object currentValue = state[idx];
        state[idx] = encryptionService.encrypt(currentValue.toString());
    }

    public void decrypt(Object entity){
        ReflectionUtils.doWithFields(entity.getClass(), field -> decryptField(field, entity), this::isFieldEncrypted);
    }

    private void decryptField(Field field, Object entity){
        field.setAccessible(true);
        Object value = ReflectionUtils.getField(field, entity);
        ReflectionUtils.setField(field, entity, encryptionService.decrypt(value.toString()));
    }

    private boolean isFieldEncrypted(Field field){
        return Objects.nonNull(AnnotationUtils.findAnnotation(field, EncryptedString.class));
    }

    private int getPropertyIndex(Field field, String[] propertyNames) {
        for (int i = 0; i < propertyNames.length; i++){
            if (field.getName().equals(propertyNames[i])){
                return i;
            }
        }

        throw new IllegalArgumentException("Property not found: " + field.getName());
    }
}
