package com.elo7.space_probe.ui.probeMovement;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.ui.probes.ProbeDTO;
import org.springframework.stereotype.Component;

@Component
class ProbeToDtoConverter {
    ProbeDTO convert(Probe probe) {
        return new ProbeDTO(probe.getId(), probe.getName(), probe.getXPosition(), probe.getYPosition(), probe.getDirection(), probe.getPlanetId());
    }
}

