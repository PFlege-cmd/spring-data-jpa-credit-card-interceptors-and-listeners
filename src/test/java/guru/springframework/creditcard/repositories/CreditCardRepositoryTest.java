package guru.springframework.creditcard.repositories;

import guru.springframework.creditcard.domain.CreditCard;
import guru.springframework.creditcard.services.EncryptionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    public static final String CREDIT_CARD_NUMBER = "1234567890000";
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testPersistCreditcard(){
        // Arrange
        var creditCard = new CreditCard();
        creditCard.setCreditCardNumber(CREDIT_CARD_NUMBER);
        creditCard.setCvv("qw");
        creditCard.setExpirationDate("02/2099");

        var savedCreditCard = creditCardRepository.saveAndFlush(creditCard);
        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM CREDIT_CARD WHERE ID = " + savedCreditCard.getId());

        String encryptedCreditcardNumber = (String) dbRow.get("credit_card_number");

        // Act
        var fetchedCreditCard = creditCardRepository.findById(savedCreditCard.getId()).orElseGet(() -> null);

        // Assert
        Assertions.assertThat(fetchedCreditCard.getCreditCardNumber()).isNotEqualTo(encryptedCreditcardNumber);

        Assertions.assertThat(encryptedCreditcardNumber).isEqualTo(encryptionService.encrypt(CREDIT_CARD_NUMBER));
    }
}