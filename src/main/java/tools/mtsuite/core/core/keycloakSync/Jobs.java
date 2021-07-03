package tools.mtsuite.core.core.keycloakSync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Jobs {

    private static final Logger log = LoggerFactory.getLogger(Jobs.class);

    @Autowired
    private SyncService syncService;

    @Scheduled(cron = "${cron.fullsync.initial}")
    void fullSync() {
        log.info("CRON fullSync: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        syncService.syncFull();
    }

}
