package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.Alias;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository securityClearanceRepository;
    private final AgencyAgentRepository agencyAgentRepository;

    public SecurityClearanceService(SecurityClearanceRepository securityClearanceRepository, AgencyAgentRepository agencyAgentRepository) {
        this.securityClearanceRepository = securityClearanceRepository;
        this.agencyAgentRepository = agencyAgentRepository;
    }

    public List<SecurityClearance> findAll() { return securityClearanceRepository.findAll(); }

    public SecurityClearance findById(int securityClearanceId) {
        return securityClearanceRepository.findById(securityClearanceId);
    }

    public Result<SecurityClearance> add(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() != 0) {
            result.addMessage("security clearance id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        securityClearance = securityClearanceRepository.add(securityClearance);
        result.setPayload(securityClearance);
        return result;
    }

    public Result<SecurityClearance> update(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() <= 0) {
            result.addMessage("security clearance id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!securityClearanceRepository.update(securityClearance)) {
            String msg = String.format("security clearance id %s not found", securityClearance.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<SecurityClearance> deleteById(int securityClearanceId) {
        Result<SecurityClearance> result = validateNotReferenced(securityClearanceRepository.findById(securityClearanceId));
        if (!result.isSuccess()) {
            return result;
        }

        if (!securityClearanceRepository.deleteById(securityClearanceId)) {
            String msg = String.format("security clearance id %s not found", securityClearanceId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = new Result<>();

        if (securityClearance == null) {
            result.addMessage("security clearance cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(securityClearance.getName())) {
            result.addMessage("security clearance name is required", ResultType.INVALID);
        }

        if (isRepeat(securityClearance)) {
            result.addMessage("security clearance name cannot be duplicated", ResultType.INVALID);
        }

        return result;
    }

    private Result<SecurityClearance> validateNotReferenced(SecurityClearance clearance) {
        Result<SecurityClearance> result = new Result<>();

        List<AgencyAgent> agents = agencyAgentRepository.findAll();
        for (AgencyAgent aa: agents) {
            if (aa.getSecurityClearance().getSecurityClearanceId() == clearance.getSecurityClearanceId()) {
                result.addMessage("security clearance is referenced by an agent", ResultType.INVALID);
            }
        }

        return result;
    }

    private boolean isRepeat(SecurityClearance securityClearance) {
        List<SecurityClearance> clearances = findAll();
        for (SecurityClearance sc: clearances) {
            if (sc.getName().equalsIgnoreCase(securityClearance.getName())) {
                return true;
            }
        }
        return false;
    }
}
