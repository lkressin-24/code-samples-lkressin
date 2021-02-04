package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<SecurityClearance> actual = repository.findAll();
        assertNotNull(actual);
        assertTrue(actual.size() >= 2);
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        SecurityClearance topSecret = new SecurityClearance(2, "Top Secret");

        SecurityClearance actual = repository.findById(1);
        assertEquals(secret, actual);

        actual = repository.findById(2);
        assertEquals(topSecret, actual);

        actual = repository.findById(3000);
        assertEquals(null, actual);
    }

    @Test
    void shouldAdd() {
        SecurityClearance clearance = new SecurityClearance();
        clearance.setName("General");
        SecurityClearance actual = repository.add(clearance);

        assertNotNull(clearance);
        assertEquals(3, actual.getSecurityClearanceId());
        assertEquals("General", actual.getName());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance updated = repository.findById(1);
        updated.setName("Confidential");

        assertTrue(repository.update(updated));
        assertEquals(1, updated.getSecurityClearanceId());
        assertEquals("Confidential", updated.getName());
    }

    @Test
    void shouldNotUpdateNonExisting() {
        SecurityClearance updated = new SecurityClearance();
        updated.setSecurityClearanceId(3000);

        assertFalse(repository.update(updated));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(2));
    }

    @Test
    void shouldNotDeleteNonExisting() {
        assertFalse(repository.deleteById(3000));
    }
}