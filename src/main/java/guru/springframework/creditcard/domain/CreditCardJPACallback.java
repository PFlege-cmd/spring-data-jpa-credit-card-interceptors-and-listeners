package guru.springframework.creditcard.domain;

import guru.springframework.creditcard.config.ApplicationContextHelper;
import guru.springframework.creditcard.services.EncryptionService;
import jakarta.persistence.*;

public class CreditCardJPACallback {

    private EncryptionService encryptionService;

    @PrePersist
    @PreUpdate
    public void preCRUDOperation(CreditCard creditCard){
        getEncryptionService();
        creditCard.setCreditCardNumber(this.encryptionService.encrypt(creditCard.getCreditCardNumber()));
        System.out.println("In Pre-persist JPA-Callback!");
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void postCRUDOperation(CreditCard creditCard){
        getEncryptionService();
        creditCard.setCreditCardNumber(this.encryptionService.decrypt(creditCard.getCreditCardNumber()));
        System.out.println("In Post-persist JPA-Callback!");
    }

    private void getEncryptionService(){
        EncryptionService encryptionService =
                ApplicationContextHelper.getBean(EncryptionService.class);
        this.encryptionService = encryptionService;
    }
}
