package tools.mtsuite.core.core.keycloakSync.actor;


import akka.actor.UntypedAbstractActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import tools.mtsuite.core.common.dao.IUserDao;
import tools.mtsuite.core.core.keycloakSync.IKcSyncJobStatusDao;
import tools.mtsuite.core.core.keycloakSync.KcSyncJobStatus;
import tools.mtsuite.core.core.keycloakSync.SyncService;
import tools.mtsuite.core.core.keycloakSync.dto.AllEntityDto;
import tools.mtsuite.core.core.keycloakSync.dto.ModifyEntityDto;
import tools.mtsuite.core.core.keycloakSync.dto.User;
import tools.mtsuite.core.core.keycloakSync.utils.KeycloakConnectionSync;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Named("SyncRunnerActor")
@Scope("prototype")
public class SyncActor extends UntypedAbstractActor {

    private String jobName = "SYNC";

    @Autowired
    private SyncService syncService;

    @Autowired
    private IUserDao utilDao;

    @Autowired
    private KeycloakConnectionSync keycloakConnection;

    @Autowired
    private IKcSyncJobStatusDao IKcSyncJobStatusDao;


    @Override
    public void onReceive(Object o) {
        if(o instanceof SyncActorData) {
            SyncActorData m = (SyncActorData) o;
            onMessage(m);
        } else {
            f_error();
        }
    }


    private void onMessage(SyncActorData m) {

        if(m.getEvent() == null) {
            f_error();
            return;
        }

        try {
            // SYNC PROCESSOR
            if(syncProcessorDispatcher(m)) {
                f_success();
                return;
            }

            f_error();
            return;

        }catch (Exception e) {
            f_error();
            e.printStackTrace();
            return;
        }
    }

    private void f_success() {
        syncService.notifyEnd();
    }

    /**
     * ERROR
     */
    private void f_error() {
        syncService.notifyEnd();
    }

    /** ----------- ACTOR FUNCIONS ----------- **/
    /**
     * PROCESSOR DISPATCHER.  CALL ONLY FROM ACTOR
     * @return
     */
    public Boolean syncProcessorDispatcher(SyncActorData message) {
        switch(message.getEvent()) {
            case FULL:
                return syncFullWorker();
            case USER:
                return syncUserWorker(message.getUser());
            case LOGIN:
                return syncFullWorker();
            default:
                return syncChangedWorker();
                //return syncChangedWorker();
        }
    }

    /**
     * Sync FULL event
     * @return
     */
    @Transactional
    public Boolean syncFullWorker() {

        AllEntityDto allEntityDto = keycloakConnection.getFullData();

        List<Long> _currentDBUsers = utilDao.getUsersIdList();

        // Soft delete entities
        if(!_currentDBUsers.isEmpty())
            utilDao.softDeleteAllUser(_currentDBUsers);

        for(User user : allEntityDto.getUsers()) {
            syncUserWorker(user);
        }

        return true;
    }

    /**
     * Sync NORMAL EVENTS
     * @return
     */

    private Boolean syncChangedWorker() {

        String lastSyncDate = getLastSyncDate();

        if(lastSyncDate == null || lastSyncDate.equals("")) {
            lastSyncDate = getCurrentDateForKc();
        }

        ModifyEntityDto modifyEntityDto = keycloakConnection.getModifyEntities(lastSyncDate);


        for(User user : modifyEntityDto.getUsers()) {
            syncUserWorker(user);
        }

        // Set Last Date
        setLastSyncDate(getCurrentDateForKc());

        return true;
    }

    /**
     * SYNC USER
     * @param user
     * @param _actualDBUsers
     * @return
     */

    @Autowired
    private IUserDao userDao;
    @Transactional
    public Boolean syncUserWorker(User user) {
        // Safe individual sync
        try{
           tools.mtsuite.core.common.model.User currentUser = userDao.findByUsername(user.getUsername());
            // If exist, update, el se CREATE
            if(currentUser != null) {
                // Update
                    currentUser.updateKCUser(user);
            } else {
                currentUser = new   tools.mtsuite.core.common.model.User();
                currentUser.updateKCUser(user);
            }

            userDao.save(currentUser);

        }catch(Exception e)
        {
            System.out.println("ERROR SYNC USER!");
        }

        return true;
    }

    private String getLastSyncDate() {
        KcSyncJobStatus kcSyncJobStatus = IKcSyncJobStatusDao.findByJobName(this.jobName);

        if(kcSyncJobStatus == null) {
            kcSyncJobStatus =  IKcSyncJobStatusDao.save(new KcSyncJobStatus(this.jobName,getCurrentDateForKc(), "OK"));
        }
        return kcSyncJobStatus.getReferenceDate();
    }

    private String getCurrentDateForKc() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Date currentDate = new Date();

        String newLastSyncDate;
        try{
            newLastSyncDate = sdf.format(currentDate);
        }catch(Exception e) {
            newLastSyncDate = "0";
        }

        return newLastSyncDate;
    }

    private void setLastSyncDate(String date) {
        KcSyncJobStatus kcSyncJobStatus = IKcSyncJobStatusDao.findByJobName(this.jobName);
        if(kcSyncJobStatus != null) {
            kcSyncJobStatus.setReferenceDate(date);
            kcSyncJobStatus.setStatus("OK");
            IKcSyncJobStatusDao.save(kcSyncJobStatus);
        }
    }

}
