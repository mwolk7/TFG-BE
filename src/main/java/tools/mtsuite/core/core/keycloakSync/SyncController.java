package tools.mtsuite.core.core.keycloakSync;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tools.mtsuite.core.core.keycloakSync.dto.EventEnum;
import tools.mtsuite.core.core.keycloakSync.dto.User;


@RestController
public class SyncController {

    @Autowired
    private SyncService syncService;

    @PostMapping(value ="/kc/sync/event/{event}")
    public Boolean syncEvent(@PathVariable EventEnum event) {
        return syncService.syncEvent(event);
    }

    @PostMapping(value ="/kc/sync/user")
    public Boolean syncUser(@RequestBody User user) {

        if(user == null) {
            return false;
        }

        return syncService.syncUser(user);
    }

    @PostMapping(value ="/kc/sync/full")
    public Boolean syncFull() {
        return syncService.syncFull();
    }

}
