package pl.antipattern.goldenquotes.event.bus

import groovy.transform.CompileStatic;

@CompileStatic
interface EventBus {
    void post(Object event)

    void register(Object subscriber)

    void unregister(Object subscriber)
}
