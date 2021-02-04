package learn.field_agent.models;

import java.time.LocalDate;

/*
model to represent relationship between agency and agent
 */
public class AgencyAgent {

    private int agencyId; // identifier for agency
    private String identifier;
    private LocalDate activationDate;
    private boolean active;

    private Agent agent; // agent that works for the agency represented as an object
    private SecurityClearance securityClearance; // security clearance of the agent represented

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public SecurityClearance getSecurityClearance() {
        return securityClearance;
    }

    public void setSecurityClearance(SecurityClearance securityClearance) {
        this.securityClearance = securityClearance;
    }
}
