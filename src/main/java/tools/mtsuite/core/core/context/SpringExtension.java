package tools.mtsuite.core.core.context;


import akka.actor.*;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

public class SpringExtension extends AbstractExtensionId<SpringExtension.SpringExt> {

    public static final SpringExtension SpringExtProvider = new SpringExtension();

    @Override
    public SpringExt createExtension(ExtendedActorSystem system) {
        return new SpringExt();
    }

    public static class SpringExt implements Extension {
        private volatile ApplicationContext applicationContext;
        
        private HashMap<String,ActorRef> actors = new HashMap<String,ActorRef>();

        public void initialize(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }
        
        public HashMap<String,ActorRef> getActorList(){
        	return this.actors;
        }

        public Props props(String actorBeanName) {
            return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
        }
        
        public void removeActor(String key) {
        	this.actors.remove(key);
        }
        
        public void addActor(String key,ActorRef actorRef) {
        	this.actors.put(key, actorRef);
        }
        
        
        public ActorRef getActor(String key) {
        	return actors.get(key);
        }
    }
}
