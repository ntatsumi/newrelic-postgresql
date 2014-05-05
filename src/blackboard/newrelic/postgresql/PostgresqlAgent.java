package blackboard.newrelic.postgresql;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.configuration.ConfigurationException;
import com.newrelic.metrics.publish.processors.EpochCounter;

/**
 * An agent for PostgreSQL.
 */
public class PostgresqlAgent extends Agent {

    private static final String GUID = "blackboard.newrelic.postgresql";
    private static final String VERSION = "0.0.1";

    private String name;
    private String monitorBridge;
    private String postgresql;

    /**
     * Constructor for PostgreSQL Agent.
     * @param name Logical name of the PostgreSQL instance you're monitoring
     * @param monitorBridge monitor-bridge instance address
     * @param postgresql PostgreSQL instances monitored by monitor-bridge
     */
    public PostgresqlAgent(String name, String monitorBridge, String postgresql) {
        super(GUID, VERSION);
        this.name = name;
        this.monitorBridge = monitorBridge;
        this.postgresql = postgresql;
    }

    @Override
    public String getComponentHumanLabel() {
        return name;
    }

    @Override
    public void pollCycle() {
        reportMetric("Connection Counts", "connections", getConnectionCounts());
        reportMetric("Database Size", "MB", getDatabaseSize());
    }

    private Integer getConnectionCounts() {
        int min = 1;
        int max = 100;
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    private Integer getDatabaseSize() {
        return 500;
    }
}
