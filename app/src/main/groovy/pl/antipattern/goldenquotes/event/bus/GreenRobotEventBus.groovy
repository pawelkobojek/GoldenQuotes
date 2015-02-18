package pl.antipattern.goldenquotes.event.bus;

class GreenRobotEventBus implements EventBus {

    private final de.greenrobot.event.EventBus bus = de.greenrobot.event.EventBus.getDefault()

    @Override
    void post(Object event) {
        bus.post(event)
    }

    @Override
    void register(Object subscriber) {
        bus.register(subscriber)
    }

    @Override
    void unregister(Object subscriber) {
        bus.unregister(subscriber)
    }
}
