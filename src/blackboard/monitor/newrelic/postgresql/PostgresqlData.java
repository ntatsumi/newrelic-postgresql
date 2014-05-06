package blackboard.monitor.newrelic.postgresql;

import java.util.HashMap;
import java.util.Map;

/**
 * PostgreSQL data enum
 * Created by ntatsumi on 5/6/14.
 */
public enum PostgresqlData {
    NUMBACKENDS("Numbackends", "processes", "Number of Backends");

    private String key;
    private String unit;
    private String metricName;

    private static Map<String, PostgresqlData> keyMap;

    private PostgresqlData(String key, String unit, String metricName) {
        this.key = key;
        this.unit = unit;
        this.metricName = metricName;
    }

    public static PostgresqlData getPostgresqlData(String key) {
        if(keyMap == null)
            initMapping();

        return keyMap.get(key);
    }

    private static void initMapping() {
        keyMap = new HashMap<String, PostgresqlData>();
        for (PostgresqlData data : values()) {
            keyMap.put(data.key, data);
        }
    }

    public String getUnit() {
        return unit;
    }

    public String getMetricName() {
        return metricName;
    }
}
