package guru.springframework.creditcard.listeners;

import guru.springframework.creditcard.services.EncryptionService;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreUpdateListener extends AbstractEncryptionListener implements PreUpdateEventListener {
    public PreUpdateListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
        System.out.println("In preUpdate");

        this.encrypt(preUpdateEvent.getState(), getPropertyNames(preUpdateEvent), preUpdateEvent.getEntity());

        return false;
    }

    private static String[] getPropertyNames(PreUpdateEvent preUpdateEvent) {
        return preUpdateEvent.getPersister().getPropertyNames();
    }
}
