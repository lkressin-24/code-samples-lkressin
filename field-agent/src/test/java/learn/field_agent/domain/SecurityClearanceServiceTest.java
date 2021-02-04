package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceServiceTest {

    @MockBean
    SecurityClearanceRepository securityClearanceRepository;

    @MockBean
    AgencyAgentRepository agencyAgentRepository;

    @Autowired
    SecurityClearanceService service;

    @Test
    void shouldFindAll() {
        when(securityClearanceRepository.findAll()).thenReturn(createTestData());
        List<SecurityClearance> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldFindById() {
        List<SecurityClearance> all = createTestData();
        SecurityClearance clearance = all.get(0);

        when(securityClearanceRepository.findById(1)).thenReturn(clearance);
        SecurityClearance actual = service.findById(1);
        assertEquals(1, actual.getSecurityClearanceId());
        assertEquals("Secret", actual.getName());
    }

    @Test
    void shouldNotFindByExistingId() {
        assertNull(service.findById(300));
    }

    @Test
    void shouldAdd() {
        SecurityClearance clearanceIn = makeSecurityClearance();
        SecurityClearance clearanceOut = new SecurityClearance(3, "General");
        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        Result<SecurityClearance> result = service.add(clearanceIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(clearanceOut, result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<SecurityClearance> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithoutName() {
        SecurityClearance clearance = new SecurityClearance();
        clearance.setName("");

        Result<SecurityClearance> result = service.add(clearance);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithIdSet() {
        SecurityClearance clearance = new SecurityClearance(3, "General");
        Result<SecurityClearance> result = service.add(clearance);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance clearance = makeSecurityClearance();
        clearance.setSecurityClearanceId(2);

        when(securityClearanceRepository.update(clearance)).thenReturn(true);

        Result<SecurityClearance> result = service.update(clearance);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutId() {
        SecurityClearance clearance = makeSecurityClearance();

        Result<SecurityClearance> result = service.update(clearance);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNonExisting() {
        SecurityClearance clearance = makeSecurityClearance();
        clearance.setSecurityClearanceId(3000);

        Result<SecurityClearance> result = service.update(clearance);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldDelete() {
        when(securityClearanceRepository.deleteById(2)).thenReturn(true);
        assertTrue(service.deleteById(2).isSuccess());
    }

    @Test
    void shouldNotDeleteReferenced() {
        when(securityClearanceRepository.deleteById(1)).thenReturn(false);
        assertFalse(service.deleteById(1).isSuccess());
    }

    @Test
    void shouldNotDeleteByNonExistingId() {
        when(securityClearanceRepository.deleteById(3000)).thenReturn(false);
        assertFalse(service.deleteById(3000).isSuccess());
    }

    private List<SecurityClearance> createTestData() {
        List<SecurityClearance> all = new ArrayList<>();
        all.add(new SecurityClearance(1, "Secret"));
        all.add(new SecurityClearance(2, "Top Secret"));
        return all;
    }

    private SecurityClearance makeSecurityClearance() {
        return new SecurityClearance(0, "General");
    }

}