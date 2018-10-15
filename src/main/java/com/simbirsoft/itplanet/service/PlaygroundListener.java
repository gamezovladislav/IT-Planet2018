package com.simbirsoft.itplanet.service;

import com.simbirsoft.itplanet.entity.PlaygroundState;

public interface PlaygroundListener {

    void onChange(PlaygroundState playground);

    void onFinish(PlaygroundState playground);
}
