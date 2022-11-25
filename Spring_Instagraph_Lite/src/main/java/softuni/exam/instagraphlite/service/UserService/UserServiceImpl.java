package softuni.exam.instagraphlite.service.UserService;

import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.UserSeedDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService.PictureService;
import softuni.exam.instagraphlite.util.Validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static softuni.exam.instagraphlite.util.Enums.Functions.*;
import static softuni.exam.instagraphlite.util.Enums.Paths.USER_PATH_FILE;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, PictureService pictureService, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.pictureService = pictureService;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USER_PATH_FILE));
    }

    @Override
    public String importUsers() throws IOException {
        Arrays.stream(GSON
                        .fromJson(readFromFileContent(), UserSeedDTO[].class))
                .filter(userSeedDTO -> {
                    boolean isValid = validationUtil.isValid(userSeedDTO);
                    boolean pictureExists = this.pictureService.checkIfPictureExistsInDataBase(userSeedDTO.getProfilePicture());
                    boolean userExists = checkIfUsersExistsInDataBase(userSeedDTO.getUsername());
                    boolean check = (isValid && pictureExists && !userExists);

                    STRING_BUILDER.append(check
                                    ? String.format("Successfully imported user: %s", userSeedDTO.getUsername())
                                    : "Invalid user!").append(System.lineSeparator());

                    return check;

                }).map(userSeedDTO -> {
                    User user = MODEL_MAPPER.map(userSeedDTO, User.class);
                    Picture picture = this.pictureService.findPictureByPath(userSeedDTO.getProfilePicture());
                    if (picture != null){
                        user.setProfilePicture(picture);
                    }

                    return user;

                })
                .forEach(userRepository::save);

        return STRING_BUILDER.toString();
    }

    private boolean checkIfUsersExistsInDataBase(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        List<User> allUsersByPostCountDescThenByUserId = this.userRepository.findAllUsersByPostCountDescThenByUserId();

        allUsersByPostCountDescThenByUserId.forEach(user -> {
            STRING_BUILDER.append(String.format("User %s", user.getUsername())).append(System.lineSeparator());
            STRING_BUILDER.append(String.format("Post count %d", user.getPosts().size())).append(System.lineSeparator());
            STRING_BUILDER.append("Post details:").append(System.lineSeparator());

            user.getPosts().stream().sorted(Comparator.comparingDouble(p -> p.getPicture().getSize()))
                    .forEach(post -> {
                        STRING_BUILDER.append(String.format("   Caption: %s", post.getCaption())).append(System.lineSeparator());
                        STRING_BUILDER.append(String.format("   Picture size: %.2f", post.getPicture().getSize())).append(System.lineSeparator());
                    });
        });

        return STRING_BUILDER.toString();
    }

    @Override
    public boolean checkIfUserExistsInDataBase(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }
}
