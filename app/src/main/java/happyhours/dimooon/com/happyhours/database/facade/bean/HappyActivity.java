package happyhours.dimooon.com.happyhours.database.facade.bean;

public class HappyActivity {

    public static final String TABLE_NAME = "ActivityTable";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_VALUE = "value";
    public static final String TABLE_COLUMN_NAME_NULLABLE = "0";
    private static final String INTEGER_TYPE = " INTEGER";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TABLE_COLUMN_VALUE + INTEGER_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long id;
    private long value;

    public HappyActivity() {
    }

    public HappyActivity(long id, long value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HappyActivity)) return false;

        HappyActivity that = (HappyActivity) o;

        if (id != that.id) return false;
        return value == that.value;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (value ^ (value >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "HappyActivity{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
