package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasJdbcTemplateRepositoryTest {

    //auto-injects the Alias repository
    @Autowired
    AliasJdbcTemplateRepository repository;

    //auto-injects the known good state for testing
    @Autowired
    KnownGoodState knownGoodState;

    //call the set() method in knownGoodState to set it
    @BeforeEach
    void setup() { knownGoodState.set(); }

    @Test
    void shouldFindAll() {
        List<Alias> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 3);
        assertEquals("Mr. Darcy", all.get(0).getName());
    }

    @Test
    void shouldFindById() {
        Alias actual = repository.findById(1);
        assertNotNull(actual);
        assertEquals("Mr. Darcy", actual.getName());
        assertEquals("dashingly handsome and wealthy", actual.getPersona());
    }

    @Test
    void shouldFindByAgentId() {
        List<Alias> aliases = repository.findByAgentId(6);
        assertNotNull(aliases);
        assertTrue(aliases.size() >= 2);
    }

    @Test
    void shouldAdd() {
        Alias alias = makeAlias();
        Alias actual = repository.add(alias);
        assertNotNull(actual);
        assertEquals(4, actual.getAliasId());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        alias.setAliasId(2);
        alias.setName("Orange");
        assertTrue(repository.update(alias));
    }

    @Test
    void shouldNotUpdateNonExisting() {
        Alias alias = makeAlias();
        alias.setAliasId(3000);

        assertFalse(repository.update(alias));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(3));
    }

    @Test
    void shouldNotDeleteByNonExisting() {
        assertFalse(repository.deleteById(3000));
    }

    Alias makeAlias() {
        Alias alias = new Alias();
        alias.setName("Test Name");
        alias.setPersona("Test Persona");
        alias.setAgentId(4);
        return alias;
    }
}