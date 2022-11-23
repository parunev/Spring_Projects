package softuni.exam.service.TownService;



import softuni.exam.models.entity.Town;

import java.io.IOException;

public interface TownService {

    boolean areImported();

    String readTownsFileContent() throws IOException;
	
	String importCars() throws IOException;

    Town findTownByName(String townName);
}
