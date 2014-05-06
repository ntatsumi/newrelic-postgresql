package blackboard.monitor;

import com.newrelic.metrics.publish.util.Logger;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * JMX Connection manager to look up MBeans
 * Created by ntatsumi on 5/5/14.
 */
public class MBeanConnection {
    private static final Logger LOG = Logger.getLogger(MBeanConnection.class);

    protected MBeanServerConnection connection = null;
    private JMXConnector jmxConnector = null;

    public void close () throws IOException
    {
        jmxConnector.close();
        LOG.debug("Closed connection to MBeanServer" );
    }

    /**
     * Connects to MBeanServer
     * @param host	hostname where BbLearn is running
     * @param port	port
     * @param username	username required to connect to BbLearn
     * @param password	password required to connect to BbLearn
     */
    public void connect (final String host, final Integer port, final String username, final String password)
    {
        LOG.debug("Connecting to MBeanServer");
        JMXServiceURL url;
        String serviceUrl = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
        final Map<String, Object> env = new HashMap<String, Object>();
        try
        {
            url = new JMXServiceURL(serviceUrl);
            env.put(JMXConnector.CREDENTIALS, new String[] {username, password});
            jmxConnector = JMXConnectorFactory.connect(url, env);
            connection = jmxConnector.getMBeanServerConnection();
        } catch (MalformedURLException e)
        {
            LOG.error("Error while creating "+serviceUrl+". "+e.getMessage());
            throw new RuntimeException("Error while creating "+serviceUrl, e);
        } catch (IOException e)
        {
            LOG.error("Connection failed due to wrong credentials/ JMX is not enabled in the configuration or BbLearn is down");
            throw new RuntimeException("Connection failed due to wrong credentials/ JMX is not enabled in the configuration or BbLearn is down", e);

        } catch (Exception e)
        {
            LOG.error("Connection failed due to wrong credentials or JMX is not enabled in the configuration");
            throw new RuntimeException("Connection failed due to wrong credentials or JMX is not enabled in the configuration", e);
        }
    }

    public Map<String, Object> getAttributes(String mBeanDomainName, String mBeanName) throws Exception
    {
        ObjectName mBean = new ObjectName(mBeanDomainName + "name=" + mBeanName);

        Map<String, Object> attributes = new HashMap<String, Object>();
        MBeanAttributeInfo[] mBeanInfo = this.connection.getMBeanInfo(mBean).getAttributes();
        for(MBeanAttributeInfo attr : mBeanInfo)
        {
            if(attr.isReadable()) {
                String attributeName = attr.getName();
                Object value = this.connection.getAttribute(mBean, attributeName);
                if(value != null)
                {
                    attributes.put(attributeName, value);
                    LOG.debug("Got " + attributeName + " = " + value);
                }
            }
            else {
                LOG.error("Cannot read " + mBeanDomainName + "name=" + mBeanName);
            }
        }
        return attributes;
    }
}
