package tools.mtsuite.core.core.context;

import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static tools.mtsuite.core.core.context.SpringExtension.SpringExtProvider;


@Configuration
public class AkkaConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public ActorSystem actorSystem() {
		ActorSystem actorSystem = ActorSystem.create("ActorSystem");
		SpringExtProvider.get(actorSystem).initialize(applicationContext);
		return actorSystem;
	}

	@Bean
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(500);
		executor.setMaxPoolSize(500);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("akka-threadPoolExecutor-");
		executor.initialize();
		return executor;
	}

}
