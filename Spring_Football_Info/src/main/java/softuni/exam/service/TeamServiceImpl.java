package softuni.exam.service;

import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureDTOs.PictureSeedRootDTO;
import softuni.exam.domain.dtos.TeamDTOs.TeamSeedDTO;
import softuni.exam.domain.dtos.TeamDTOs.TeamSeedRootDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidationUtil.ValidatorUtil;
import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.util.Enums.Paths.PICTURES_FILE;
import static softuni.exam.util.Enums.Paths.TEAMS_FILE;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;

    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public String importTeams() throws JAXBException {
        TeamSeedRootDTO teamRootDto = xmlParser.fromFile(TEAMS_FILE, TeamSeedRootDTO.class);


        for (TeamSeedDTO teamSeedDTO : teamRootDto.getTeams()) {

            Team team = MODEL_MAPPER.map(teamSeedDTO, Team.class);
            Picture picture = this.pictureRepository.findByUrl(teamSeedDTO.getPicture().getUrl());

            team.setPicture(picture);

            if (!this.validatorUtil.isValid(team)){
                STRING_BUILDER.append("Invalid team").append(System.lineSeparator());
                continue;
            }

            this.teamRepository.saveAndFlush(team);
            STRING_BUILDER.append(String.format("Successfully imported team - %s", team.getName())).append(System.lineSeparator());

        }

        return STRING_BUILDER.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE));
    }
}
