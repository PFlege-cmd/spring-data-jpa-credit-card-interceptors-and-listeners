package guru.springframework.creditcard.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EncryptionServiceTest {

    @Autowired
    private EncryptionService encryptionService;

    @Test
    void testEncryption(){
        // Arrange
        String testMessage1 = "First";
        String testMessage2 = "Second";

        //Act
        String encodedMessage1 = encryptionService.encrypt(testMessage1);
        String encodedMessage2 = encryptionService.encrypt(testMessage2);
        String decodedMessage1 = encryptionService.decrypt(encodedMessage1);
        String decodedMessage2 = encryptionService.decrypt(encodedMessage2);

        //Assert
        Assertions.assertThat(encodedMessage1).isNotEqualTo(encodedMessage2);
        Assertions.assertThat(decodedMessage1).isEqualTo(testMessage1);
        Assertions.assertThat(decodedMessage2).isEqualTo(testMessage2);
    }
}