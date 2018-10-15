package com.simbirsoft.itplanet.service;

import com.simbirsoft.itplanet.entity.PlaygroundState;

import java.util.Optional;

public interface PlaygroundLoader {

    Optional<PlaygroundState> load();
}
