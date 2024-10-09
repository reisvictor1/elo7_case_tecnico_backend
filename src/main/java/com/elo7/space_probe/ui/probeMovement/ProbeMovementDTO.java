package com.elo7.space_probe.ui.probeMovement;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ProbeMovementDTO(@JsonProperty("move") String move) {}