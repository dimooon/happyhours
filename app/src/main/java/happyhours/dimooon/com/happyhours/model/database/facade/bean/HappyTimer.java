package happyhours.dimooon.com.happyhours.model.database.facade.bean;

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

    public static final String SQL_INSRT_DEFAULT_TIMER =
            "INSERT INTO " + TABLE_NAME + " (" + TABLE_COLUMN_NAME + " )"+
            " VALUES ('Default') ";

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
        return "HappyTimer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", happy=" + happy +
                '}';
    }
}
