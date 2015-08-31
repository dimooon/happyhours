package happyhours.dimooon.com.happyhours.database.facade.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.TimerActivity;

public class TimerActivityDAO extends HappyDAO implements TimerActivity{

    public TimerActivityDAO(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public long createTimerActivity(long timerId, long activityId, long defaultValue,long sessionId) {
        ContentValues values = new ContentValues();
        values.put(HappyTimerActivity.TABLE_COLUMN_TIMER_ID, timerId);
        values.put(HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID, activityId);
        values.put(HappyTimerActivity.TABLE_COLUMN_VALUE, defaultValue);
        values.put(HappyTimerActivity.TABLE_COLUMN_SESSION_ID, sessionId);

        return executeInsert(HappyTimerActivity.TABLE_NAME,HappyTimer.TABLE_COLUMN_NAME_NULLABLE,values);
    }

    @Override
    public HappyTimerActivity getTimerActivity(long id) {
        String[] projection = {
                HappyTimerActivity.TABLE_COLUMN_ID,
                HappyTimerActivity.TABLE_COLUMN_TIMER_ID,
                HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID,
                HappyTimerActivity.TABLE_COLUMN_VALUE,
                HappyTimerActivity.TABLE_COLUMN_SESSION_ID
        };

        String sortOrder = HappyTimerActivity.TABLE_COLUMN_ID + " ASC";

        Cursor cursor = executeSingleGetQuery(HappyTimerActivity.TABLE_NAME,projection,HappyTimerActivity.TABLE_COLUMN_ID + "=?",sortOrder,id);

        cursor.moveToFirst();
        long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_ID));
        long timerId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_TIMER_ID));
        long activityId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID));
        long value = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_VALUE));
        long sessionId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_SESSION_ID));

        return new HappyTimerActivity(itemId,timerId,activityId,value,sessionId);
    }

    @Override
    public ArrayList<HappyTimerActivity> getTimerActivities() {

        String[] projection = {
                HappyTimerActivity.TABLE_COLUMN_ID,
                HappyTimerActivity.TABLE_COLUMN_TIMER_ID,
                HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID,
                HappyTimerActivity.TABLE_COLUMN_VALUE,
                HappyTimerActivity.TABLE_COLUMN_SESSION_ID
        };

        String sortOrder = HappyTimerActivity.TABLE_COLUMN_ID + " ASC";

        Cursor cursor = executeGetAllQuery(HappyTimerActivity.TABLE_NAME,projection,sortOrder);

        ArrayList<HappyTimerActivity> timerActivities = new ArrayList<HappyTimerActivity>();
        cursor.moveToFirst();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_ID));
            long timerId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_TIMER_ID));
            long activityId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID));
            long value = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_VALUE));
            long sessionId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_SESSION_ID));

            timerActivities.add(new HappyTimerActivity(itemId,timerId,activityId,value,sessionId));
        }

        return timerActivities;
    }

    @Override
    public ArrayList<HappyTimerActivity> getTimerActivities(long sessionId) {
        String[] projection = {
                HappyTimerActivity.TABLE_COLUMN_ID,
                HappyTimerActivity.TABLE_COLUMN_TIMER_ID,
                HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID,
                HappyTimerActivity.TABLE_COLUMN_VALUE,
                HappyTimerActivity.TABLE_COLUMN_SESSION_ID
        };

        String sortOrder = HappyTimerActivity.TABLE_COLUMN_ID + " ASC";

        Cursor cursor = executeSingleGetQuery(HappyTimerActivity.TABLE_NAME, projection, HappyTimerActivity.TABLE_COLUMN_SESSION_ID + "=?", sortOrder, sessionId);

        ArrayList<HappyTimerActivity> timerActivities = new ArrayList<HappyTimerActivity>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_ID));
            long timerId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_TIMER_ID));
            long activityId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID));
            long value = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_VALUE));
            long storedSessionId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyTimerActivity.TABLE_COLUMN_SESSION_ID));

            timerActivities.add(new HappyTimerActivity(itemId,timerId,activityId,value,storedSessionId));
        }

        return timerActivities;
    }

    @Override
    public boolean updateTimerActivity(long itemId, long timerId, long activityId, long value) {

        ContentValues values = new ContentValues();
        values.put(HappyTimerActivity.TABLE_COLUMN_TIMER_ID, timerId);
        values.put(HappyTimerActivity.TABLE_COLUMN_ACTIVITY_ID, activityId);
        values.put(HappyTimerActivity.TABLE_COLUMN_VALUE, value);

        String selection = HappyTimerActivity.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(itemId) };

        int count = database.update(
                HappyTimerActivity.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count == 1;

    }

    @Override
    public boolean deleteTimerActivity(long id) {
        String selection = HappyTimerActivity.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int deleteCount = database.delete(HappyTimerActivity.TABLE_NAME, selection, selectionArgs);

        return deleteCount == 1;
    }
}