package tools.mtsuite.core.core.context;

import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.mtsuite.core.common.model.enums.Devices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

@Configuration
@EnableScheduling
@Scope("singleton")
public class BeanConfiguration {
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}


	@Bean(name = "ModelMapperBean")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ModelMapper create() {
		ModelMapper modelMapper = new ModelMapper();
		Provider<Date> localDateProvider = new AbstractProvider<Date>() {
			@Override
			public Date get() {
				return new Date();
			}
		};

		Converter<String, Date> toStringDate = new AbstractConverter<String, Date>() {
			@Override
			protected Date convert(String source) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				Date localDate = null;
				try {
					localDate = format.parse(source);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return localDate;
			}

		};
		Converter<Date, String> toDateString = new AbstractConverter<Date, String>() {

			@Override
			protected String convert(Date source) {
				// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				String localDate = format.format(source);
				return localDate;
			}
		};
		modelMapper.createTypeMap(String.class, Date.class);
		modelMapper.createTypeMap(Date.class, String.class);
		modelMapper.addConverter(toStringDate);
		modelMapper.addConverter(toDateString);
		modelMapper.getTypeMap(String.class, Date.class).setProvider(localDateProvider);
		modelMapper.getConfiguration().setFieldMatchingEnabled(true);
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("poolScheduler");
		scheduler.setPoolSize(50);
		return scheduler;
	}


}
