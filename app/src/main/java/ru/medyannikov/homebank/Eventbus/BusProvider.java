package ru.medyannikov.homebank.Eventbus;

import de.greenrobot.event.EventBus;

/**
 * Created by Vladimir on 24.10.2015.
 */
public class BusProvider {
    private final static EventBus bus = new EventBus();

    public static EventBus getInstance(){
        return bus;
    }
}
