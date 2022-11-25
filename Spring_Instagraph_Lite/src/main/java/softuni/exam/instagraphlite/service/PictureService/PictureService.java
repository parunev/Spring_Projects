package softuni.exam.instagraphlite.service.PictureService;

import softuni.exam.instagraphlite.models.dtos.UserSeedDTO;
import softuni.exam.instagraphlite.models.entities.Picture;

import java.io.IOException;

public interface PictureService {
    boolean areImported();
    String readFromFileContent() throws IOException;
    String importPictures() throws IOException;
    String exportPictures();

    boolean checkIfPictureExistsInDataBase(String profilePicture);

    Picture findPictureByPath(String profilePicture);

}
