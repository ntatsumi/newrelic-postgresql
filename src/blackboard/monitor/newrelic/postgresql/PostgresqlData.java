package blackboard.monitor.newrelic.postgresql;

import java.util.HashMap;
import java.util.Map;

/**
 * PostgreSQL data enum
 * Created by ntatsumi on 5/6/14.
 */
public enum PostgresqlData {
    TOTAL_CONNECTIONS("Connections/TotalConnections", "connections", "Total Connections"),
    MAX_CONNECTIONS("Connections/MaxConnections", "connections", "Max Connections"),
    TOTAL_SESSION_TIME_HOURS("Sessions/TotalSessionTimeHours", "hours", "Total Session Time"),
    OLDEST_SESSION_TIME_HOURS("Sessions/OldestSessionTimeHours", "hours", "Oldest Session Duration"),
    DATABASE_SIZE("Storage/DatabaseSize", "MB", "Database Size"),
    TOTAL_LIVE_SIZE("Storage/TotalLiveSize", "MB", "Total Live Size"),
    TOTAL_DEAD_SIZE("Storage/TotalDeadSize", "MB", "Total Dead Size"),
    TOTAL_TABLE_SCANS("AccessPatterns/TotalTableScans", "MB", "Total Table Scans"),
    TOTAL_INDEX_SCANS("AccessPatterns/TotalIndexScans", "MB", "Total Index Scans"),
    TOTAL_INSERTS("AccessPatterns/TotalInserts", "MB", "Total Inserts"),
    TOTAL_UPDATES("AccessPatterns/TotalUpdates", "MB", "Total Updates"),
    TOTAL_DELETES("AccessPatterns/TotalDeletes", "MB", "Total Deletes");

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
