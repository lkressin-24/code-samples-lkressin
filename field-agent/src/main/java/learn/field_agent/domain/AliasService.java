package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;

    public AliasService(AliasRepository repository) { this.repository = repository; }

    private List<Alias> findAll() {
        return repository.findAll();
    }

    public List<Alias> findByAgent(int agentId) {
        return repository.findByAgentId(agentId);
    }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("alias id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        alias = repository.add(alias);
        result.setPayload(alias);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("alias id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("alias id %s not found", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Alias> deleteById(int aliasId) {
        Result<Alias> result = new Result<>();

        if (!repository.deleteById(aliasId)) {
            String msg = String.format("alias id %s not found", aliasId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("alias cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is  required", ResultType.INVALID);
        }

        List<Alias> existingAliases = findAll();
        for (Alias a: existingAliases) {
            if (a.getName() == alias.getName()) {
                if (Validations.isNullOrBlank(alias.getPersona())) {
                    result.addMessage("persona is required for duplicate names", ResultType.INVALID);
                }
            }
        }

        return result;
    }
}
