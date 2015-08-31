package happyhours.dimooon.com.happyhours.database.facade.bean;

public class HappyTimerActivity {

    public static final String TABLE_NAME = "TimerActivityTable";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_TIMER_ID = "timerId";
    public static final String TABLE_COLUMN_ACTIVITY_ID = "activityId";
    public static final String TABLE_COLUMN_SESSION_ID = "sessionId";
    public static final String TABLE_COLUMN_VALUE = "value";

    public static final String TABLE_COLUMN_NAME_NULLABLE = "0";
    private static final String TEXT_INTEGER = " INTEGER";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TABLE_COLUMN_TIMER_ID + TEXT_INTEGER + "," +
                    TABLE_COLUMN_ACTIVITY_ID + TEXT_INTEGER + "," +
                    TABLE_COLUMN_SESSION_ID + TEXT_INTEGER + "," +
                    TABLE_COLUMN_VALUE + TEXT_INTEGER +
                    " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long id;
    private long timerId;
    private long activityId;
    private long activityValue;
    private long sessionId;
    private String timerName;

    public HappyTimerActivity() {
    }

    public HappyTimerActivity(long id, long timerId, long activityId, long activityValue, long sessionId) {
        this.id = id;
        this.timerId = timerId;
        this.activityId = activityId;
        this.activityValue = activityValue;
        this.sessionId = sessionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimerId() {
        return timerId;
    }

    public void setTimerId(long timerId) {
        this.timerId = timerId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getActivityValue() {
        return activityValue;
    }

    public void setActivityValue(long activityValue) {
        this.activityValue = activityValue;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HappyTimerActivity)) return false;

        HappyTimerActivity that = (HappyTimerActivity) o;

        if (id != that.id) return false;
        if (timerId != that.timerId) return false;
        if (activityId != that.activityId) return false;
        if (activityValue != that.activityValue) return false;
        if (sessionId != that.sessionId) return false;
        return timerName.equals(that.timerName);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (timerId ^ (timerId >>> 32));
        result = 31 * result + (int) (activityId ^ (activityId >>> 32));
        result = 31 * result + (int) (activityValue ^ (activityValue >>> 32));
        result = 31 * result + (int) (sessionId ^ (sessionId >>> 32));
        result = 31 * result + timerName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HappyTimerActivity{" +
                "id=" + id +
                ", timerId=" + timerId +
                ", activityId=" + activityId +
                ", activityValue=" + activityValue +
                ", sessionId=" + sessionId +
                ", timerName='" + timerName + '\'' +
                '}';
    }
}