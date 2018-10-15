package com.simbirsoft.itplanet.service;

import com.simbirsoft.itplanet.entity.PathNode;
import com.simbirsoft.itplanet.entity.PlaygroundState;

import java.util.Queue;

public interface PlaygroundStrategy {

    Queue<PathNode> findPath(PlaygroundState playground);

}
