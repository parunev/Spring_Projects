package com.example.football.service.impl;

import com.example.football.models.dto.StatDTOs.StatSeedRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.Validation.ValidationUtil;
import com.example.football.util.XmlParser.XmlParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.football.util.enums.Functions.MODEL_MAPPER;
import static com.example.football.util.enums.Functions.STRING_BUILDER;
import static com.example.football.util.enums.Paths.STAT_PATH_FILE;


@AllArgsConstructor
@Service
public class StatServiceImpl implements StatService {

    private StatRepository statRepository;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STAT_PATH_FILE));
    }

    @Override
    public String importStats() throws JAXBException {

        xmlParser.fromFile(STAT_PATH_FILE, StatSeedRootDTO.class)
                .getStatSeedDTOSet().stream().filter(statSeedDTO -> {
                    boolean isValid = validationUtil.isValid(statSeedDTO);

                    STRING_BUILDER.append(isValid ? String.format("Successfully import Stat %.2f - %.2f - %.2f%n"
                                    ,statSeedDTO.getPassing(), statSeedDTO.getShooting()
                                    , statSeedDTO.getEndurance()) : String.format("Invalid stat"))
                            .append(System.lineSeparator());

                    return isValid;

                }).map(statSeedDTO -> MODEL_MAPPER.map(statSeedDTO, Stat.class))
                .forEach(statRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public Stat getById(Long id) {
        return this.statRepository.getById(id);
    }
}
