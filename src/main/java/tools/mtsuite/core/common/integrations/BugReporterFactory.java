package tools.mtsuite.core.common.integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.integrations.backlog.BacklogService;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.EnumMap;
import java.util.Map;
@Service
public class BugReporterFactory {
        private final Map<BugReporterType, IBugReporter> strategies = new EnumMap<>(BugReporterType.class);

        @Autowired
        private BacklogService backlogService;

        @PostConstruct
        public void init() throws MalformedURLException {
            initStrategies();
        }

        public IBugReporter getStrategy(BugReporterType bugReporterType) {
            if (bugReporterType == null || !strategies.containsKey(bugReporterType)) {
                throw new IllegalArgumentException("Invalid " + bugReporterType);
            }
            return strategies.get(bugReporterType);
        }

        private void initStrategies() {
            strategies.put(BugReporterType.Backlog, backlogService);
        }
    }

