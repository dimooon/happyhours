package happyhours.dimooon.com.happyhours.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyActivity;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;

public class HappyDatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HappyDatabase.db";

    public HappyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(HappyTimer.SQL_CREATE_ENTRIES);
        db.execSQL(HappyTimer.SQL_INSRT_DEFAULT_TIMER);
        db.execSQL(HappyActivity.SQL_CREATE_ENTRIES);
        db.execSQL(HappyTimerActivity.SQL_CREATE_ENTRIES);
        db.execSQL(HappySession.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HappyTimer.SQL_DELETE_ENTRIES);
        db.execSQL(HappyActivity.SQL_DELETE_ENTRIES);
        db.execSQL(HappyTimerActivity.SQL_DELETE_ENTRIES);
        db.execSQL(HappySession.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}