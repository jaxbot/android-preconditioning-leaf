package me.jaxbot.wear.preconditioningleaf;

/**
 * Created by jonathan on 1/11/15.
 */
public class ScheduledStart {
    public String name;
    public long eventId;
    public long time;

    public ScheduledStart(String name, long eventId, long time) {
        this.name = name;
        this.eventId = eventId;
        this.time = time;
    }
}
