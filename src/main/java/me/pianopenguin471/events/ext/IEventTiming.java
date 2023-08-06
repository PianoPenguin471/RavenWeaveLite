package me.pianopenguin471.events.ext;

public interface IEventTiming {

    EventTiming getTiming();

    default boolean isPre() {
        return getTiming() == EventTiming.PRE;
    }

    default boolean isPost() {
        return getTiming() == EventTiming.POST;
    }

}
