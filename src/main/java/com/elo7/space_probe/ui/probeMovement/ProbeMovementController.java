package com.elo7.space_probe.ui.probeMovement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.ui.probes.ProbeDTO;
import com.elo7.space_probe.ui.planets.PlanetDTO;
import com.elo7.space_probe.app.planets.FindAllPlanetService;
import com.elo7.space_probe.app.planets.FindPlanetService;
import com.elo7.space_probe.app.probes.FindAllProbeService;
import com.elo7.space_probe.app.probes.FindProbeService;

import com.elo7.space_probe.app.probeMovement.UpdateProbePositionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import com.elo7.space_probe.ui.planets.PlanetDTO;

@RestController
@RequestMapping("v1/movement")
class ProbeMovementController {
  private final FindProbeService findProbeService;
  private final FindPlanetService findPlanetService;
  private final ProbeMovementToDtoConverter probeMovementToDtoConverter;
  private final UpdateProbePositionService updateProbePositionService;

  ProbeMovementController(FindProbeService findProbeService, FindPlanetService findPlanetService, ProbeMovementToDtoConverter probeMovementToDtoConverter, UpdateProbePositionService updateProbePositionService) {
    this.findProbeService = findProbeService;
    this.findPlanetService = findPlanetService;
    this.probeMovementToDtoConverter = probeMovementToDtoConverter;
    this.updateProbePositionService = updateProbePositionService;
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  ProbeDTO update(@PathVariable("id") Integer id, @RequestBody ProbeMovementDTO probeMovementDTO){
    Probe probe = findProbeService.execute(id).orElse(null);
    PlanetDTO planet = findPlanetService.execute(probe.getPlanetId()).map(probeMovementToDtoConverter::convertPlanet).orElse(null);

    Integer x = probe.getXPosition();
    Integer y = probe.getYPosition();
    String probe_direction = probe.getDirection();
    Integer planet_x_size = planet.width();
    Integer planet_y_size = planet.height();

    char[] moves = probeMovementDTO.move().toCharArray();

    for (int i = 0; i < moves.length; i++) {
      switch (probe_direction) {
          case "N" -> {
            switch(moves[i]) {
              case 'M' -> {
                if(x - 1 >= 0) x = x - 1;
              }
              case 'L' -> {
                probe_direction = "W";
              }

              case 'R' -> {
                probe_direction = "E";
              }
            }
          }
          case "W" -> {
            switch(moves[i]) {
              case 'M' -> {
                if(y - 1 >= 0) y = y - 1;
              }
              case 'L' -> {
                probe_direction = "S";
              }

              case 'R' -> {
                probe_direction = "N";
              }
            }
          }

          case "S" -> {
             switch(moves[i]) {
              case 'M' -> {
                if(x + 1 < planet_x_size) x = x + 1;
              }
              case 'L' -> {
                probe_direction = "S";
              }

              case 'R' -> {
                probe_direction = "N";
              }
            }
          }

          case "E" -> {
             switch(moves[i]) {
              case 'M' -> {
                if(y + 1 < planet_y_size) y = y + 1;
              }
              case 'L' -> {
                probe_direction = "N";
              }

              case 'R' -> {
                probe_direction = "S";
              }
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