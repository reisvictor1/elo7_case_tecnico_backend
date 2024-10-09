package com.elo7.space_probe.ui.probeMovement;

import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.ui.planets.PlanetDTO;
import com.elo7.space_probe.ui.probes.ProbeDTO;
import org.springframework.stereotype.Component;

@Component
class ProbeMovementToDtoConverter {
    PlanetDTO convertPlanet(Planet planet) {
        return new PlanetDTO(planet.getId(), planet.getName(), planet.getWidth(), planet.getHeight());
    }

    ProbeDTO convertProbe(Probe probe) {
        return new ProbeDTO(probe.getId(), probe.getName(), probe.getXPosition(), probe.getYPosition(), probe.getDirection(), probe.getPlanetId());
    }

}
