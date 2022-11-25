package softuni.exam.instagraphlite.service.PostService;

import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.PostDTOs.PostSeedRootDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.PictureService.PictureService;
import softuni.exam.instagraphlite.service.UserService.UserService;
import softuni.exam.instagraphlite.util.Validation.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.instagraphlite.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.instagraphlite.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.instagraphlite.util.Enums.Paths.POST_PATH_FILE;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserService userService;
    private final PictureService pictureService;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PostServiceImpl(PostRepository postRepository, UserService userService,
                           PictureService pictureService, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.pictureService = pictureService;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POST_PATH_FILE));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {

        xmlParser.fromFile(POST_PATH_FILE, PostSeedRootDTO.class)
                .getPosts().stream().filter(postSeedDTO -> {
                    boolean isValid = validationUtil.isValid(postSeedDTO);
                    boolean userExists = this.userService.checkIfUserExistsInDataBase(postSeedDTO.getUser().getUsername());
                    boolean pictureExists = this.pictureService.checkIfPictureExistsInDataBase(postSeedDTO.getPicture().getPath());
                    boolean check = (isValid && userExists && pictureExists);

                    STRING_BUILDER.append(check ? String.format("Successfully imported post, made by %s", postSeedDTO.getUser().getUsername())
                            : "Invalid post!").append(System.lineSeparator());

                    return check;

                }).map(postSeedDTO -> {
                    Post post = MODEL_MAPPER.map(postSeedDTO, Post.class);
                    User userByUsername = this.userService.findByUsername(postSeedDTO.getUser().getUsername());

                    if (userByUsername != null){
                        post.setUser(userByUsername);
                    }else{
                        System.out.println("No such user in database!");
                    }

                    Picture pictureByPath = this.pictureService.findPictureByPath(postSeedDTO.getPicture().getPath());
                    if (pictureByPath != null){
                        post.setPicture(pictureByPath);
                    }else{
                        System.out.println("No such a picture in database!");
                    }

                    return post;

                }).forEach(this.postRepository::save);

        return STRING_BUILDER.toString();
    }
}
