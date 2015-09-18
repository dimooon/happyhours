package happyhours.dimooon.com.happyhours.model.database.facade.bean;

import java.util.HashMap;

public class HappyTimer {

    public static final long DEFAULT_TIMER_ID = 1;

    public static final String TABLE_NAME = "TimerTable";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_NAME = "name";
    public static final String TABLE_COLUMN_HAPPY = "happy";
    public static final String TABLE_COLUMN_NAME_NULLABLE = "hm...";
    private static final String TEXT_TYPE = " TEXT";

    public static final int HAPPY  = 1;
    public static final int NOT_HAPPY = 0;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TABLE_COLUMN_HAPPY + " INTEGER ," +
                    TABLE_COLUMN_NAME + TEXT_TYPE  +
                    " )";

    public static final String SQL_INSRT_DEFAULT_TIMERS =
            "INSERT INTO " + TABLE_NAME + " (" + TABLE_COLUMN_NAME + ")"+
            " VALUES ('%s') ";

    public static final String USING_THIS_APPLICATION = "I am using this application.";
    public static final String Codding = "I am writing a code.";
    public static final String Debugging = "I am looking for issues.";
    public static final String BugFixing = "I am fixing bug.";
    public static final String Branching = "I am working with CVS.";
    public static final String Merging = "I am merging perfect code into dev.";

    public static final HashMap<String,String> defaultTimers = new HashMap<>();

    static {
        defaultTimers.put(USING_THIS_APPLICATION, String.format(SQL_INSRT_DEFAULT_TIMERS, USING_THIS_APPLICATION));
        defaultTimers.put(Codding, String.format(SQL_INSRT_DEFAULT_TIMERS, Codding));
        defaultTimers.put(Debugging, String.format(SQL_INSRT_DEFAULT_TIMERS, Debugging));
        defaultTimers.put(BugFixing, String.format(SQL_INSRT_DEFAULT_TIMERS, BugFixing));
        defaultTimers.put(Branching, String.format(SQL_INSRT_DEFAULT_TIMERS, Branching));
        defaultTimers.put(Merging, String.format(SQL_INSRT_DEFAULT_TIMERS, Merging));
    }

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long id;
    private String name;
    private int happy;

    public HappyTimer() {
    }

    public HappyTimer(long id, String name, int happy) {
        this.id = id;
        this.name = name;
        this.happy = happy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HappyTimer)) return false;

        HappyTimer timer = (HappyTimer) o;

        if (id != timer.id) return false;
        if (happy != timer.happy) return false;
        return name.equals(timer.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + happy;
        return result;
    }

    @Override
    public String toString() {
        return "\nHappyTimer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", happy=" + happy +
                '}';
    }
}
