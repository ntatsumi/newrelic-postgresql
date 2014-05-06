package blackboard.monitor.newrelic.postgresql;

import java.util.Map;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.AgentFactory;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

/**
 * AgentFactory for PostgreSQL
 * Created by ntatsumi on 5/5/14.
 */
public class PostgresqlAgentFactory extends AgentFactory {

    @Override
    public Agent createConfiguredAgent(Map<String, Object> properties) throws ConfigurationException {
        String name = (String) properties.get("name");
        String monitorBridgeHost = (String) properties.get("monitor-bridge-host");
        Integer monitorBridgePort = Integer.valueOf((String)properties.get("monitor-bridge-port"));
        String monitorBridgeUsername = (String) properties.get("monitor-bridge-username");
        String monitorBridgePassword = (String) properties.get("monitor-bridge-password");
        String monitorBridgeData = (String) properties.get("monitor-bridge-data");

        if (name == null || monitorBridgeHost == null || monitorBridgePort == null ||
                monitorBridgeUsername == null || monitorBridgePassword == null || monitorBridgeData == null) {
            throw new ConfigurationException("'name', 'monitor-bridge-host', 'monitor-bridge-port', 'monitor-bridge-username', " +
                    "'monitor-bridge-password', 'monitor-bridge-data', and 'host' cannot be null. Do you have a 'config/plugin.json' file?");
        }

        return new PostgresqlAgent(name, monitorBridgeHost, monitorBridgePort, monitorBridgeUsername, monitorBridgePassword, monitorBridgeData);
    }
}
