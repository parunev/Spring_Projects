package com.example.football.service.impl;

import com.example.football.models.dto.PlayerDTOs.PlayerSeedRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.Validation.ValidationUtil;
import com.example.football.util.XmlParser.XmlParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static com.example.football.util.enums.Functions.MODEL_MAPPER;
import static com.example.football.util.enums.Functions.STRING_BUILDER;
import static com.example.football.util.enums.Paths.PLAYER_PATH_FILE;

@AllArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private XmlParser xmlParser;
    private ValidationUtil validationUtil;
    private TownService townService;
    private StatService statService;
    private TeamService teamService;

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYER_PATH_FILE));
    }

    @Override
    public String importPlayers() throws JAXBException {
        xmlParser.fromFile(PLAYER_PATH_FILE, PlayerSeedRootDTO.class)
                .getPlayerSeedDto().stream().filter(playerSeedDTO -> {
                    boolean isValid = validationUtil.isValid(playerSeedDTO);
                    
                    STRING_BUILDER.append(isValid ? String.format("Successfully imported Player %s %s - %s%n",playerSeedDTO.getFirstName(),
                            playerSeedDTO.getLastName(), playerSeedDTO.getPosition())
                            : String.format("Invalid Player%n"));

                    return isValid;

                }).map(playerSeedDTO -> {
                    Player player = MODEL_MAPPER.map(playerSeedDTO, Player.class);
                    player.setStat(statService.getById(playerSeedDTO.getStat().getId()));
                    player.setTeam(teamService.getByName(playerSeedDTO.getTeam().getName()));
                    player.setTown(townService.getTownByName(playerSeedDTO.getTown().getName()));

                    return player;

                }).forEach(playerRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public String exportBestPlayers() {

        List<Player> bestPlayers = playerRepository.getTopPlayers(LocalDate.of(1995, 1,1),
                LocalDate.of(2003,1,1));
        bestPlayers.forEach(player -> {
            STRING_BUILDER.append(String.format("""
                    Player - %s %s%n\tPosition - %s
                    \tTeam - %s
                    \tStadium - %s
                    """,player.getFirstName(),player.getLastName(),player.getPositionType(),player.getTeam().getName(),player.getTeam().getStadiumName()));

        });

        return STRING_BUILDER.toString().trim();
    }
}
