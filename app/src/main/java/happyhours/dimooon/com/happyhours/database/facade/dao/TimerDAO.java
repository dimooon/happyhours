package happyhours.dimooon.com.happyhours.database.facade.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.Timer;

public class TimerDAO extends HappyDAO implements Timer{

    public TimerDAO(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public long createTimer(String name,boolean happy) {
        ContentValues values = new ContentValues();
        values.put(HappyTimer.TABLE_COLUMN_NAME, name);
        values.put(HappyTimer.TABLE_COLUMN_HAPPY, happy ? 1 : 0);

        return executeInsert(HappyTimer.TABLE_NAME,HappyTimer.TABLE_COLUMN_NAME_NULLABLE,values);
    }

    @Override
    public HappyTimer getTimer(long id) {
        String[] projection = {
                HappyTimer.TABLE_COLUMN_ID,
                HappyTimer.TABLE_COLUMN_NAME,
                HappyTimer.TABLE_COLUMN_HAPPY
        };

        String sortOrder = HappyTimer.TABLE_COLUMN_NAME + " ASC";

        Cursor cursor = executeSingleGetQuery(HappyTimer.TABLE_NAME,projection,HappyTimer.TABLE_COLUMN_ID + "=?",sortOrder,id);

        if(cursor.getCount()<1){
            return null;
        }

        cursor.moveToFirst();
        long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimer.TABLE_COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(HappyTimer.TABLE_COLUMN_NAME));
        int happy = cursor.getInt(cursor.getColumnIndexOrThrow(HappyTimer.TABLE_COLUMN_HAPPY));

        return new HappyTimer(itemId,name,happy);
    }

    @Override
    public ArrayList<HappyTimer> getTimers() {
        String[] projection = {
                HappyTimer.TABLE_COLUMN_ID,
                HappyTimer.TABLE_COLUMN_NAME,
                HappyTimer.TABLE_COLUMN_HAPPY,
        };

        String sortOrder = HappyTimer.TABLE_COLUMN_NAME + " ASC";

        Cursor cursor = executeGetAllQuery(HappyTimer.TABLE_NAME,projection,sortOrder);

        ArrayList<HappyTimer> timers = new ArrayList<HappyTimer>();
        cursor.moveToFirst();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimer.TABLE_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(HappyTimer.TABLE_COLUMN_NAME));
            int happy = cursor.getInt(cursor.getColumnIndexOrThrow(HappyTimer.TABLE_COLUMN_HAPPY));

            timers.add(new HappyTimer(itemId,name,happy));
        }

        return timers;
    }

    @Override
    public boolean updateTimer(long id, String name) {
        ContentValues values = new ContentValues();
        values.put(HappyTimer.TABLE_COLUMN_NAME, name);

        String selection = HappyTimer.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = database.update(
                HappyTimer.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count == 1;
    }

    @Override
    public boolean deleteTimer(long id) {

        String selection = HappyTimer.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int deleteCount = database.delete(HappyTimer.TABLE_NAME, selection, selectionArgs);

        return deleteCount == 1;
    }
}
