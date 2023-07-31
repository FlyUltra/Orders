package org.orders.database;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DBRow {
    private Map<String, Object> cells;

    public DBRow() {
        cells = new HashMap<>();
    }

    public void addCell(String key, Object value) {
        cells.put(key, value);
    }

    public Object getObject(String key) {
        return cells.get(key);
    }

    public String getString(String key) {
        Object obj = cells.get(key);
        return (obj != null) ? obj.toString() : null;
    }

    public UUID getUUID(String key) {
        String uuidString = getString(key);
        if (uuidString != null) {
            try {
                return UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                // ConsoleLogger.sendError("Cannot load UUID from database - invalid format");
            }
        }
        return null;
    }

    public int getInt(String key) {
        Object obj = cells.get(key);
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    public boolean getBoolean(String key) {
        Object obj = cells.get(key);
        return (obj instanceof Boolean) && (Boolean) obj;
    }

    public long getLong(String key) {
        Object obj = cells.get(key);
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return 0;
    }

    public double getDouble(String key) {
        Object obj = cells.get(key);
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return 0;
    }

    public float getFloat(String key) {
        Object obj = cells.get(key);
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        return 0;
    }

    public byte[] getBlob(String key) {
        Object obj = cells.get(key);
        return (obj instanceof byte[]) ? (byte[]) obj : null;
    }
}