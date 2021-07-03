package tools.mtsuite.core.core.keycloakSync;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.core.context.SpringExtension;
import tools.mtsuite.core.core.keycloakSync.actor.SyncActorData;
import tools.mtsuite.core.core.keycloakSync.dto.EventEnum;
import tools.mtsuite.core.core.keycloakSync.dto.User;
import tools.mtsuite.core.core.keycloakSync.utils.KeycloakConnectionSync;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;


@Service
public class SyncService {
    private static String SYNC_RUNNER_ACTOR_NAME = "SyncRunnerActor";
    private static String SYNC_RUNNER_ACTOR_ID = "keyCloakSyncActor";
    /* ---- Member Variables ---- */

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private KeycloakConnectionSync keycloakConnection;


    /* System variables */

    @Value("${syncservice.mineventtime}")
    private Long minTimeBetweenSyncEventStr;


    // Queue
    private final Queue<SyncActorData> pila = new LinkedList<SyncActorData>();

    // Runner
    private ActorRef syncRunner;
    private Boolean isRunning = false;

    //TEST
    private ActorRef testActor;

    // Timer Block (seconds)
    private Long lastSyncTimeStamp = 0L;





    /**
     * Add event to sync
     * @param event
     * @return
     */
    public Boolean syncEvent(EventEnum event) {
        // Verify queue block
        if( (Instant.now().getEpochSecond() - lastSyncTimeStamp) < Long.valueOf(minTimeBetweenSyncEventStr) ) {
          return false;
        }

        this.lastSyncTimeStamp = Instant.now().getEpochSecond();

        // Add to queue
        pila.add(new SyncActorData(event));

        // Execute queue
        runQueue();

        return true;
    }

    /**
     * ADD FULL EVENT TO SYNC
     * @return
     */
    public Boolean syncFull() {
        pila.clear();
        pila.add(new SyncActorData(EventEnum.FULL));
        // Execute queue
        runQueue();
        return true;
    }

    /**
     * ADD USER EVENT TO SYNC
     * @param user
     * @return
     */
    public Boolean syncUser(User user) {

        pila.add( new SyncActorData(EventEnum.USER, user) );
        // Execute queue
        runQueue();
        return true;
    }


    /**
     * Start
     */
    private void runQueue() {
        // Init runnner if is not created, and create timer
        if(syncRunner == null) {
            syncRunner =  actorSystem.actorOf(
                    SpringExtension.SpringExtProvider.get(actorSystem)
                            .props(SYNC_RUNNER_ACTOR_NAME),SYNC_RUNNER_ACTOR_ID);
        }

        // Check pila size
        if(pila.size() != 0 && !isRunning) {
            isRunning = true;
            syncRunner.tell(pila.poll(), ActorRef.noSender());
        }
    }

    /**
     * Notify work end ( RUNNER CONTEXT)
     */
    public void notifyEnd() {
        if(pila.size() != 0) {
            syncRunner.tell(pila.poll(), ActorRef.noSender());
        } else {
            isRunning = false;
        }

    }
}
