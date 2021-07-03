package tools.mtsuite.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@OpenAPIDefinition(
		info = @Info(
				title = "mTSuite core application API",
				version = "1",
				license = @License(name = "Private license by High Impact SAS", url = "http://highimppact.com.ar"),
				contact = @Contact(url = "http://highimppact.com.ar", name = "Contacto", email = "contacto@highimpact.com.ar")
		)
)
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class MTSuiteCore {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MTSuiteCore.class,args);
	}
}
