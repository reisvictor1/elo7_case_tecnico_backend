package com.elo7.space_probe.ui.probeMovement;

import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.ui.planets.PlanetDTO;
import org.springframework.stereotype.Component;

@Component
class PlanetToDtoConverter {
    PlanetDTO convert(Planet planet) {
        return new PlanetDTO(planet.getId(), planet.getName(), planet.getWidth(), planet.getHeight());
    }
}
