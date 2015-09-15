package happyhours.dimooon.com.happyhours.model.database.facade.bean;

public class HappySession {

    public static final String TABLE_NAME = "SessionTable";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_NAME = "name";
    public static final String TABLE_COLUMN_TIMER_ACTIVITY_ID = "timerActivityId";
    public static final String TABLE_COLUMN_TIMER_ACTIVITY_TIMESTAMP = "timerActivityTimestamp";
    public static final String TABLE_COLUMN_NAME_NULLABLE = "0";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TABLE_COLUMN_NAME + TEXT_TYPE + "," +
                    TABLE_COLUMN_TIMER_ACTIVITY_ID + INTEGER_TYPE + "," +
                    TABLE_COLUMN_TIMER_ACTIVITY_TIMESTAMP + INTEGER_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long id;
    private long timerActivityId;
    private String name;
    private long timestamp;

    public HappySession() {
    }

    public HappySession(long id, long timerActivityId, String name, long timestamp) {
        this.id = id;
        this.timerActivityId = timerActivityId;
        this.name = name;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimerActivityId() {
        return timerActivityId;
    }

    public void setTimerActivityId(long timerActivityId) {
        this.timerActivityId = timerActivityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HappySession)) return false;

        HappySession session = (HappySession) o;

        if (id != session.id) return false;
        if (timerActivityId != session.timerActivityId) return false;
        if (timestamp != session.timestamp) return false;
        return name.equals(session.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (timerActivityId ^ (timerActivityId >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "HappySession{" +
                "id=" + id +
                ", timerActivityId=" + timerActivityId +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
