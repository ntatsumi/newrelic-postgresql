package blackboard.monitor.newrelic.postgresql;

import com.newrelic.metrics.publish.Runner;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

/**
 * Main class
 * Created by ntatsumi on 5/5/14.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Runner runner = new Runner();
            runner.add(new PostgresqlAgentFactory());
            runner.setupAndRun(); // Never returns
        } catch (ConfigurationException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(-1);
        }
    }
}
