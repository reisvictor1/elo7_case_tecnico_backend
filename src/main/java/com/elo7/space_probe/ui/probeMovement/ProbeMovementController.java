package com.elo7.space_probe.ui.probeMovement;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.ui.probes.ProbeDTO;
import com.elo7.space_probe.ui.planets.PlanetDTO;
import com.elo7.space_probe.app.planets.FindPlanetService;
import com.elo7.space_probe.app.probes.FindAllProbeService;
import com.elo7.space_probe.app.probes.FindProbeService;
import com.elo7.space_probe.ui.ErrorMessageDTO;
import com.elo7.space_probe.ui.probeMovement.ProbeMovementInputException;
import com.elo7.space_probe.ui.GlobalExceptionHandler;

import com.elo7.space_probe.app.probeMovement.UpdateProbePositionService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

import com.elo7.space_probe.ui.planets.PlanetDTO;

@RestController
@RequestMapping("v1/movement")
class ProbeMovementController {
  private final FindProbeService findProbeService;
  private final FindAllProbeService findAllProbeService;
  private final FindPlanetService findPlanetService;
  private final ProbeMovementToDtoConverter probeMovementToDtoConverter;
  private final UpdateProbePositionService updateProbePositionService;
  private final GlobalExceptionHandler globalExceptionHandler;

  ProbeMovementController(FindProbeService findProbeService, FindAllProbeService findAllProbeService, FindPlanetService findPlanetService, ProbeMovementToDtoConverter probeMovementToDtoConverter, UpdateProbePositionService updateProbePositionService, GlobalExceptionHandler globalExceptionHandler) {
    this.findProbeService = findProbeService;
    this.findPlanetService = findPlanetService;
    this.probeMovementToDtoConverter = probeMovementToDtoConverter;
    this.updateProbePositionService = updateProbePositionService;
    this.findAllProbeService = findAllProbeService;
    this.globalExceptionHandler = globalExceptionHandler;
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  ProbeDTO update(@PathVariable("id") Integer id, @RequestBody ProbeMovementDTO probeMovementDTO) throws ProbeMovementInputException{
    
    char[] moves = probeMovementDTO.move().toCharArray();
    Probe probe = findProbeService.execute(id).orElse(null);

    PlanetDTO planet = findPlanetService.execute(probe.getPlanetId()).map(probeMovementToDtoConverter::convertPlanet).orElse(null);

    List<Probe> probesResponse = findAllProbeService.execute();
    List<ProbeDTO> probes = probesResponse.stream().filter(p -> p.getPlanetId() == probe.getPlanetId()).map(probeMovementToDtoConverter::convertProbe).toList();

    Integer x = probe.getXPosition();
    Integer y = probe.getYPosition();
    String probe_direction = probe.getDirection();
    Integer planet_x_size = planet.width();
    Integer planet_y_size = planet.height();

    

    for (int i = 0; i < moves.length; i++) {
      switch (probe_direction) {
          case "N" -> {
            switch(moves[i]) {
              case 'M' -> {
                if(x - 1 >= 0) {
                  for(int j = 0; j < probes.size(); j++) {
                    ProbeDTO temp_probe = probes.get(j);
                    if(temp_probe.x() == x - 1) throw new ProbeMovementInputException("Probe Collision");
                  }
                 
                  x = x - 1;
                }
              }
              case 'L' -> {
                probe_direction = "W";
              }

              case 'R' -> {
                probe_direction = "E";
              }
              default -> throw new ProbeMovementInputException("Invalid Move");
            }
          }
          case "W" -> {
            switch(moves[i]) {
              case 'M' -> {
                if(y - 1 >= 0) {
                  for(int j = 0; j < probes.size(); j++) {
                    ProbeDTO temp_probe = probes.get(j);
                    if(temp_probe.y() == y - 1) throw new ProbeMovementInputException("Probe Collision");
                  }
                  y = y - 1;
                } 
              }
              case 'L' -> {
                probe_direction = "S";
              }

              case 'R' -> {
                probe_direction = "N";
              }
              default -> throw new ProbeMovementInputException("Invalid Move");
            }
          }

          case "S" -> {
             switch(moves[i]) {
              case 'M' -> {
                if(x + 1 < planet_x_size) {
                  for(int j = 0; j < probes.size(); j++) {
                    ProbeDTO temp_probe = probes.get(j);
                    if(temp_probe.x() == x + 1) throw new ProbeMovementInputException("Probe Collision");
                  }
                  x = x + 1;
                }  
              }
              case 'L' -> {
                probe_direction = "S";
              }

              case 'R' -> {
                probe_direction = "N";
              }
              default -> throw new ProbeMovementInputException("Invalid Move");
            }
          }

          case "E" -> {
             switch(moves[i]) {
              case 'M' -> {
                if(y + 1 < planet_y_size) {
                  for(int j = 0; j < probes.size(); j++) {
                    ProbeDTO temp_probe = probes.get(j);
                    if(temp_probe.y() == y + 1) throw new ProbeMovementInputException("Probe Collision");
                  }
                  y = y + 1;
                } 
              }
              case 'L' -> {
                probe_direction = "N";
              }

              case 'R' -> {
                probe_direction = "S";
              }
              default -> throw new ProbeMovementInputException("Invalid Move");
            }
          }
          default ->  throw new AssertionError();
      }
    }

    probe.setDirection(probe_direction);
    probe.setPosition(x, y);
    Probe probeUpdated = updateProbePositionService.execute(probe);
    return probeMovementToDtoConverter.convertProbe(probeUpdated);
  }
  

}