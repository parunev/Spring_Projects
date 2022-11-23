package softuni.exam.service.AgentService;


import softuni.exam.models.entity.Agent;

import java.io.IOException;

public interface AgentService {

    boolean areImported();

    String readAgentsFromFile() throws IOException;
	
	String importAgents() throws IOException;

    Agent getAgentByName(String name);
}
