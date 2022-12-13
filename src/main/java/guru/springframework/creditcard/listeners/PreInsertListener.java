package guru.springframework.creditcard.listeners;

import guru.springframework.creditcard.services.EncryptionService;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreInsertListener extends AbstractEncryptionListener implements PreInsertEventListener {
    public PreInsertListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        System.out.println("In preInsert");
        this.encrypt(preInsertEvent.getState(), getPropertyNames(preInsertEvent), preInsertEvent.getEntity());

        return false;
    }

    private static String[] getPropertyNames(PreInsertEvent preInsertEvent) {
        return preInsertEvent.getPersister().getPropertyNames();
    }
}
