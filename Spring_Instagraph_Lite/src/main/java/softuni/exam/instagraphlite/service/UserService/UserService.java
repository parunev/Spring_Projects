package softuni.exam.instagraphlite.service.UserService;

import softuni.exam.instagraphlite.models.entities.User;

import java.io.IOException;

public interface UserService {
    boolean areImported();
    String readFromFileContent() throws IOException;
    String importUsers() throws IOException;
    String exportUsersWithTheirPosts();
    boolean checkIfUserExistsInDataBase(String username);

    User findByUsername(String username);
}
