package learn.field_agent.models;

import java.util.ArrayList;
import java.util.List;

public class Agency {

    private int agencyId; // unique identifier for agency
    private String shortName;
    private String longName;
    private List<Location> locations = new ArrayList<>(); // list of locations
    private List<AgencyAgent> agents = new ArrayList<>(); // list of agents that work for the agency represented as AgencyAgent objects

    public Agency() {
    }

    public Agency(int agentId, String shortName, String longName) {
        this.agencyId = agentId;
        this.shortName = shortName;
        this.longName = longName;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<AgencyAgent> getAgents() {
        return new ArrayList<>(agents);
    }

    public void setAgents(List<AgencyAgent> agents) {
        this.agents = agents;
    }
}
