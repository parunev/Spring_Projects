package softuni.exam.service.AgentService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentDTOs.AgentsSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.TownService.TownService;
import softuni.exam.util.Validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.enums.Functions.*;
import static softuni.exam.util.enums.Paths.AGENTS_FILE_PATH;

@AllArgsConstructor
@Service
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final TownService townService;
    private final ValidationUtil validationUtil;


    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENTS_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        Arrays.stream(GSON
                .fromJson(readAgentsFromFile(), AgentsSeedDto[].class))
                .filter(agentsSeedDto -> {

                    boolean isValid = validationUtil.isValid(agentsSeedDto);

                    boolean doesntExist = agentRepository
                            .findAgentByFirstName(agentsSeedDto.getFirstName()).isEmpty();

                    if (!doesntExist){isValid = false;}

                    STRING_BUILDER.append(isValid ? String.format("Successfully import agent - %s %s",
                                    agentsSeedDto.getFirstName(), agentsSeedDto.getLastName())
                                    : "Invalid agent").append(System.lineSeparator());

                    return isValid;

                }).map(agentsSeedDto -> {
                    Agent agent = MODEL_MAPPER.map(agentsSeedDto, Agent.class);
                    agent.setTown(townService.findTownByName(agentsSeedDto.getTown()));
                    return agent;

                }).forEach(agentRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public Agent getAgentByName(String name) {
        return agentRepository.findAgentByFirstName(name).orElse(null);
    }
}
