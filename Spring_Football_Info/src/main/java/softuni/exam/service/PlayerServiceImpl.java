package softuni.exam.service;

import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PlayerSeedDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.Enums.Position;
import softuni.exam.util.ValidationUtil.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.util.Enums.Functions.*;
import static softuni.exam.util.Enums.Paths.PLAYERS_FILE;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final ValidatorUtil validatorUtil;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository
            , PictureRepository pictureRepository, ValidatorUtil validatorUtil) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPlayers() throws IOException {
        PlayerSeedDTO[] players = GSON.fromJson(readPlayersJsonFile(), PlayerSeedDTO[].class);

        for (PlayerSeedDTO p : players) {

            Player player = MODEL_MAPPER.map(p,Player.class);
            Picture pic = MODEL_MAPPER.map(p.getPicture(), Picture.class);
            Team t = MODEL_MAPPER.map(p.getTeam(), Team.class);
            Position position = p.getPosition();

            Picture picture = this.pictureRepository.findByUrl(pic.getUrl());
            Team team = this.teamRepository.findByName(t.getName());
            player.setPosition(position);
            player.setPicture(picture);
            player.setTeam(team);

            if (!this.validatorUtil.isValid(player) || picture == null || team == null){
                STRING_BUILDER.append("Invalid player").append(System.lineSeparator());
                continue;
            }

            this.playerRepository.saveAndFlush(player);
            STRING_BUILDER.append(String.format("Successfully imported player: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator());
        }

        return STRING_BUILDER.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        List<Player> players = this.playerRepository.exportPlayersWhereSalaryBiggerThan();

        for (Player player : players) {
            STRING_BUILDER.append(String.format("Player name: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator())
                    .append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator())
                    .append(String.format("\tSalary: %s", player.getSalary())).append(System.lineSeparator());
            Team team = teamRepository.findById(player.getTeam().getId()).orElse(null);
            STRING_BUILDER.append(String.format("\tTeam name: %s",team.getName())).append(System.lineSeparator());
        }
        return STRING_BUILDER.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        List<Player> players = this.playerRepository.exportPlayersInATeam();

        STRING_BUILDER.append("Team: North Hub").append(System.lineSeparator());

        for (Player player : players) {
            STRING_BUILDER.append(String.format("\tPlayer name:  %s %s - %s",
                            player.getFirstName(),player.getLastName(),player.getPosition() )).append(System.lineSeparator())
                    .append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator());
        }
        return STRING_BUILDER.toString().trim();
    }
}

