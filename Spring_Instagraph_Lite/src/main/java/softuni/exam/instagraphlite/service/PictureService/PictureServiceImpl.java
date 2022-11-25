package softuni.exam.instagraphlite.service.PictureService;

import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.PictureSeedDTO;
import softuni.exam.instagraphlite.models.dtos.UserSeedDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.util.Validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.instagraphlite.util.Enums.Functions.*;
import static softuni.exam.instagraphlite.util.Enums.Paths.PICTURE_PATH_FILE;

@Service
public class PictureServiceImpl implements PictureService{

    private final PictureRepository pictureRepository;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURE_PATH_FILE));
    }

    @Override
    public String importPictures() throws IOException {
        if (areImported()){
            return "Pictures are already imported!";
        }


        Arrays.stream(GSON.fromJson(readFromFileContent(), PictureSeedDTO[].class))
                .filter(pictureSeedDTO -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDTO);
                    boolean entityExistsTrue = checkIfPictureExistsInDataBase(pictureSeedDTO.getPath());


                    STRING_BUILDER.append(isValid
                                    ? String.format("Successfully imported Picture, with size %.2f"
                                    , pictureSeedDTO.getSize()) : "Invalid Picture")
                            .append(System.lineSeparator());

                    return (isValid && !entityExistsTrue);

                }).map(pictureSeedDTO -> MODEL_MAPPER.map(pictureSeedDTO, Picture.class))
                .forEach(this.pictureRepository::save);

        return STRING_BUILDER.toString();
    }

    public boolean checkIfPictureExistsInDataBase(String path) {
        return this.pictureRepository.existsByPath(path);
    }

    @Override
    public Picture findPictureByPath(String profilePicture) {
        return this.pictureRepository.findByPath(profilePicture).orElse(null);
    }

    @Override
    public String exportPictures() {
        List<Picture> allBySizeGreaterThanOrderBySize = this.pictureRepository.findAllBySizeGreaterThanOrderBySize(30000.0);
        allBySizeGreaterThanOrderBySize.forEach(picture -> {
            STRING_BUILDER.append(String.format("%.2f - %s", picture.getSize(), picture.getPath())).append(System.lineSeparator());
        });

        return STRING_BUILDER.toString();
    }
}
