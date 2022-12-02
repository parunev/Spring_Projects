package softuni.exam.service;

import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureDTOs.PictureSeedRootDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidationUtil.ValidatorUtil;
import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.util.Enums.Paths.PICTURES_FILE;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;

    public PictureServiceImpl(PictureRepository pictureRepository, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.pictureRepository = pictureRepository;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importPictures() throws JAXBException {
        xmlParser.fromFile(PICTURES_FILE, PictureSeedRootDTO.class)
                .getPictures().stream().filter(pictureSeedDTO -> {
                    boolean isValid = validatorUtil.isValid(pictureSeedDTO);

                    STRING_BUILDER.append(isValid ? String.format("Successfully imported picture - %s", pictureSeedDTO.getUrl())
                            : "Invalid picture").append(System.lineSeparator());

                    return isValid;

                }).map(pictureSeedDTO -> MODEL_MAPPER.map(pictureSeedDTO, Picture.class)).forEach(this.pictureRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE));
    }
}
