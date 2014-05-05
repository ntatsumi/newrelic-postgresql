package blackboard.newrelic.postgresql;

import java.util.Map;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.AgentFactory;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

/**
 * AgentFactory for PostgreSQL
 */
public class PostgresqlAgentFactory extends AgentFactory {

    @Override
    public Agent createConfiguredAgent(Map<String, Object> properties) throws ConfigurationException {
        String name = (String) properties.get("name");
        String monitorBridge = (String) properties.get("monitor-bridge");
        String postgresql = (String) properties.get("postgresql");
        
        if (name == null || monitorBridge == null || postgresql == null) {
            throw new ConfigurationException("'name', 'monitorBridge' and 'host' cannot be null. Do you have a 'config/plugin.json' file?");
        }
        
        return new PostgresqlAgent(name, monitorBridge, postgresql);
    }
}
