package tools.mtsuite.core.core.keycloakSync.actor;


import tools.mtsuite.core.core.keycloakSync.dto.EventEnum;
import tools.mtsuite.core.core.keycloakSync.dto.User;

public class SyncActorData {

    private EventEnum event;
    private User user;

    public SyncActorData(EventEnum event) {
        this.event = event;
    }

    public SyncActorData(EventEnum event, User user) {
        this.event = event;
        this.user = user;
    }

    public EventEnum getEvent() {
        return event;
    }

    public void setEvent(EventEnum event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
