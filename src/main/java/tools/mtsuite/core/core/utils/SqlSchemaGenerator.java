package tools.mtsuite.core.core.utils;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import tools.mtsuite.core.common.model.*;
import tools.mtsuite.core.common.model.integrations.BacklogCredential;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.common.model.integrations.BugReporterLog;
import tools.mtsuite.core.core.keycloakSync.KcSyncJobStatus;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class SqlSchemaGenerator {

	public static void main(String[] args) {

		Map<String, String> settings = new HashMap<>();
		settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		settings.put("hibernate.connection.url", "jdbc:mysql://webmail.highimpact.com.ar:3306/mtsuite");
		settings.put("hibernate.connection.username", "root");
		settings.put("hibernate.connection.password", "ATSPassword");
		settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		settings.put("hibernate.show_sql", "true");


		MetadataSources config = new MetadataSources(
				new StandardServiceRegistryBuilder().applySettings(settings).build());

		config.addAnnotatedClass(GenericObject.class);
		config.addAnnotatedClass(File.class);
		config.addAnnotatedClass(Project.class);
		config.addAnnotatedClass(ProjectVersion.class);
		config.addAnnotatedClass(TestSuite.class);
		config.addAnnotatedClass(TestSuiteRunner.class);
		config.addAnnotatedClass(TestSuiteRunnerStatistics.class);
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(UserProject.class);
		config.addAnnotatedClass(BugReporterCredential.class);
		config.addAnnotatedClass(BacklogCredential.class);
		config.addAnnotatedClass(BugReporterLog.class);
		config.addAnnotatedClass(KcSyncJobStatus.class);
		config.addAnnotatedClass(FileTokenAccess.class);





		MetadataImplementor metadataImplementor = (MetadataImplementor) config.buildMetadata();
		SchemaExport export = new SchemaExport();

		export.setDelimiter(";");
		export.setOutputFile("src/main/resources/Schema.sql");
		export.setFormat(true);

		// can change the output here
		EnumSet<TargetType> enumSet = EnumSet.of(TargetType.SCRIPT);
		export.execute(enumSet, SchemaExport.Action.BOTH, metadataImplementor);

	}
}
