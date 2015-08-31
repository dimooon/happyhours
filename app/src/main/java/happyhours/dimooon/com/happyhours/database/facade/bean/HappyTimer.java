package happyhours.dimooon.com.happyhours.database.facade.bean;

public class HappyTimer {

    public static final String TABLE_NAME = "TimerTable";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_NAME = "name";
    public static final String TABLE_COLUMN_NAME_NULLABLE = "hm...";
    private static final String TEXT_TYPE = " TEXT";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TABLE_COLUMN_NAME + TEXT_TYPE  +
                    " )";

    public static final String SQL_INSRT_DEFAULT_TIMER =
            "INSERT INTO " + TABLE_NAME + " (" + TABLE_COLUMN_NAME + " )"+
            " VALUES ('Default') ";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long id;
    private String name;

    public HappyTimer() {
    }

    public HappyTimer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HappyTimer)) return false;

        HappyTimer that = (HappyTimer) o;

        if (id != that.id) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HappyTimer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
