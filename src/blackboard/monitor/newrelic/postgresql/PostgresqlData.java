package blackboard.monitor.newrelic.postgresql;

import java.util.HashMap;
import java.util.Map;

/**
 * PostgreSQL data enum
 * Created by ntatsumi on 5/6/14.
 */
public enum PostgresqlData {
    TOTAL_CONNECTIONS("TotalConnections", "", "Total Connections"),
    MAX_CONNECTIONS("MaxConnections", "", "Max Connections"),
    TOTAL_SESSION_TIME_HOURS("TotalSessionTimeHours", "hours", "Total Session Time"),
    OLDEST_SESSION_TIME_HOURS("OldestSessionTimeHours", "hours", "Oldest Session Duration"),
    DATABASE_SIZE("DatabaseSize", "MB", "Database Size"),
    TOTAL_LIVE_SIZE("TotalLiveSize", "MB", "Total Live Size"),
    TOTAL_DEAD_SIZE("TotalDeadSize", "MB", "Total Dead Size"),
    TOTAL_TABLE_SCANS("TotalTableScans", "MB", "Total Table Scans"),
    TOTAL_INDEX_SCANS("TotalIndexScans", "MB", "Total Index Scans"),
    TOTAL_INSERTS("TotalInserts", "MB", "Total Inserts"),
    TOTAL_UPDATES("TotalUpdates", "MB", "Total Updates"),
    TOTAL_DELETES("TotalDeletes", "MB", "Total Deletes");

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
