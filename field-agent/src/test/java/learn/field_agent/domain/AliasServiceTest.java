package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasServiceTest {

    @MockBean
    AliasRepository repository;

    @Autowired
    AliasService service;

    @Test
    void shouldFindByAgent() {
        List<Alias> all = createTestData();
        List<Alias> agent = new ArrayList<>();
        agent.add(all.get(2));

        when(repository.findByAgentId(8)).thenReturn(agent);

        List<Alias> actual = service.findByAgent(8);

        assertEquals(1, actual.size());
        assertEquals("Black Widow", actual.get(0).getName());
    }

    @Test
    void shouldNotFindAnyByNonExistingAgent() {
        assertEquals(0,service.findByAgent(30).size());
    }

    @Test
    void shouldAdd() {
        Alias aliasIn = makeAlias();
        Alias aliasOut = new Alias(5, "Test Name", "Test Persona", 3);
        when(repository.add(aliasIn)).thenReturn(aliasOut);

        Result<Alias> result = service.add(aliasIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(aliasOut, result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<Alias> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithoutName() {
        Alias alias = new Alias();
        alias.setPersona("Test Persona");
        alias.setAgentId(3);

        Result<Alias> result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithIdSet() {
        Alias alias = new Alias(2, "Test Name", "Test Persona", 3);
        Result<Alias> result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        alias.setAliasId(3);

        when(repository.update(alias)).thenReturn(true);

        Result<Alias> result = service.update(alias);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutIdSet() {
        Alias alias = new Alias();
        alias.setName("Test Name");
        alias.setPersona("Test Persona");
        alias.setAgentId(3);

        when(repository.update(alias)).thenReturn(false);

        Result<Alias> result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNonExisting() {
        Alias alias = new Alias();
        alias.setAliasId(3000);
        alias.setName("Test Name");

        Result<Alias> result = service.update(alias);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutName() {
        Alias alias = new Alias();
        alias.setAliasId(2);

        Result<Alias> result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(3)).thenReturn(true);
        assertTrue(service.deleteById(3).isSuccess());
    }

    @Test
    void shouldNotDeleteNonExisting() {
        when(repository.deleteById(3000)).thenReturn(false);
        assertFalse(service.deleteById(3000).isSuccess());
    }

    private List<Alias> createTestData() {
        List<Alias> all = new ArrayList<>();
        all.add(new Alias(1, "Mr.Darcy", "dashingly handsome and wealthy", 6));
        all.add(new Alias(2, "Rose", "yound widow with good connections", 1));
        all.add(new Alias(3, "Black Widow", "specially trained", 8));
        all.add(new Alias(4, "Him", "that one guy", 6));
        return all;
    }

    private Alias makeAlias() {
        return new Alias(0, "Test Name", "Test Persona", 3);
    }
}