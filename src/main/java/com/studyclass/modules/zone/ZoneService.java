package com.studyclass.modules.zone;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @PostConstruct
    public void initZoneData() throws IOException {
        if (zoneRepository.count() == 0){
            ClassPathResource resource = new ClassPathResource("zones_kr.csv");
            List<Zone> zoneList = Files.readAllLines((Path) resource.getInputStream(), StandardCharsets.UTF_8).stream()
                        .map(line -> {
                            String[] split = line.split(",");
                            return Zone.builder().city(split[0]).localNameOfCity(split[1]).province(split[2]).build();
                        }).collect(Collectors.toList());
            zoneRepository.saveAll(zoneList);
        }
    }
}
