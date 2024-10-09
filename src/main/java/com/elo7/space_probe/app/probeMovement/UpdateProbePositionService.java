package com.elo7.space_probe.app.probeMovement;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Probes;
import org.springframework.stereotype.Service;

@Service
public class UpdateProbePositionService {

  private final Probes probes;

    public UpdateProbePositionService(Probes probes) {
        this.probes = probes;
    }

    public Probe execute(Probe probe) {
      return probes.save(probe);
    }

}
