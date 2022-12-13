package guru.springframework.creditcard.domain;

import guru.springframework.creditcard.config.ApplicationContextHelper;
import guru.springframework.creditcard.services.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {

    EncryptionService encryptionService;

    private void getEncryptionService(){
        this.encryptionService = ApplicationContextHelper.getBean(EncryptionService.class);
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        getEncryptionService();
        return this.encryptionService.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        getEncryptionService();
        return this.encryptionService.decrypt(s);
    }
}
