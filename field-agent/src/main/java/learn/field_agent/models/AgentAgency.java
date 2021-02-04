package learn.field_agent.models;

import java.time.LocalDate;

/*
model that represents the relationship between an agent and an agency they work for
 */
public class AgentAgency {

    private int agentId; // unique identifier of the agent
    private String identifier;
    private LocalDate activationDate;
    private boolean active;

    private Agency agency; // agency they work for - represented as an object
    private SecurityClearance securityClearance; // security clearance of agent within that agency

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
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

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public SecurityClearance getSecurityClearance() {
        return securityClearance;
    }

    public void setSecurityClearance(SecurityClearance securityClearance) {
        this.securityClearance = securityClearance;
    }
}
