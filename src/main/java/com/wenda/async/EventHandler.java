package com.wenda.async;

import javax.sound.midi.VoiceStatus;
import java.util.List;

/**
 * Created by 49540 on 2017/6/30.
 */
public interface EventHandler {
    void dohandle(EventModel eventModel);

    List<EventType> getSupportEventType();

}
