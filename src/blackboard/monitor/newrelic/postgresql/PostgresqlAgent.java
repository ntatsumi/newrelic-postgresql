package blackboard.monitor.newrelic.postgresql;

import blackboard.monitor.MBeanConnection;
import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.util.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * An agent for PostgreSQL.
 * Created by ntatsumi on 5/5/14.
 */
public class PostgresqlAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(PostgresqlAgent.class);
    private static final String GUID = "blackboard.newrelic.postgresql";
    private static final String VERSION = "0.0.1";

    private String name;
    private String monitorBridgeHost;
    private Integer monitorBridgePort;
    private String monitorBridgeUsername;
    private String monitorBridgePassword;
    private String monitorBridgeData;

    public final String MBEAN_DOMAIN_NAME = "monitor-bridge:";

    /**
     * Constructor for PostgreSQL Agent.
     * @param name Logical name of the PostgreSQL instance you're monitoring in New Relic
     * @param monitorBridgeHost monitor-bridge instance JMX host address
     * @param monitorBridgePort monitor-bridge instance JMX port
     * @param monitorBridgeUsername monitor-bridge instance JMX username
     * @param monitorBridgePassword monitor-bridge instance JMX password
     * @param monitorBridgeData PostgreSQL instances monitored by monitor-bridge
     */
    public PostgresqlAgent(String name, String monitorBridgeHost, Integer monitorBridgePort,
                           String monitorBridgeUsername, String monitorBridgePassword, String monitorBridgeData) {
        super(GUID, VERSION);
        this.name = name;
        this.monitorBridgeHost = monitorBridgeHost;
        this.monitorBridgePort = monitorBridgePort;
        this.monitorBridgeUsername = monitorBridgeUsername;
        this.monitorBridgePassword = monitorBridgePassword;
        this.monitorBridgeData = monitorBridgeData;
        LOG.info("PostgresqlAgent instantiated");
    }

    @Override
    public String getComponentHumanLabel() {
        return name;
    }

    @Override
    public void pollCycle() {
        LOG.debug("pollCycle");
        Map<String, Object> data = getData();
        if(data != null)
        {
            for (Map.Entry<String, Object> metric : data.entrySet())
            {
                String key = metric.getKey();
                Object value = metric.getValue();
                PostgresqlData postgresqlData = PostgresqlData.getPostgresqlData(key);
                if(postgresqlData == null) {
                    LOG.debug("No PostgresqlData type found for " + key);
                    continue;
                }
                if (value != null && Number.class.isAssignableFrom(value.getClass()))
                    reportMetric(postgresqlData.getMetricName(), postgresqlData.getUnit(), (Number) value);
                else
                    LOG.error("Numeric value was not found for " + key);
            }
        }
    }

    private Map<String, Object> getData() {
        MBeanConnection conn = new MBeanConnection();
        conn.connect(monitorBridgeHost, monitorBridgePort, monitorBridgeUsername, monitorBridgePassword);
        try
        {
            return conn.getAttributes(MBEAN_DOMAIN_NAME, monitorBridgeData);
        } catch (Exception e)
        {
            LOG.error("Could not get data from monitor-bridge MBean server", e);
            return null;
        } finally
        {
            try {
                if(conn != null) conn.close();
            } catch (IOException e) {
                LOG.error("Error closing MbeanServer connection", e);
            }
        }
    }
}
