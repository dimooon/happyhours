package happyhours.dimooon.com.happyhours.database.facade.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyActivity;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.Session;

public class SessionDAO extends HappyDAO implements Session {

    public SessionDAO(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public long createSession(String name) {
        ContentValues values = new ContentValues();
        values.put(HappySession.TABLE_COLUMN_TIMER_ACTIVITY_ID, 0);
        values.put(HappySession.TABLE_COLUMN_NAME, name);
        values.put(HappySession.TABLE_COLUMN_TIMER_ACTIVITY_TIMESTAMP, System.currentTimeMillis());

        return executeInsert(HappySession.TABLE_NAME,HappySession.TABLE_COLUMN_NAME_NULLABLE,values);
    }

    @Override
    public HappySession getSession(long id) {

        String[] projection = {
                HappySession.TABLE_COLUMN_ID,
                HappySession.TABLE_COLUMN_TIMER_ACTIVITY_ID,
                HappySession.TABLE_COLUMN_NAME,
                HappySession.TABLE_COLUMN_TIMER_ACTIVITY_TIMESTAMP
        };

        String sortOrder = HappySession.TABLE_COLUMN_NAME + " ASC";

        Cursor cursor = executeSingleGetQuery(HappySession.TABLE_NAME,projection,HappySession.TABLE_COLUMN_ID + "=?",sortOrder,id);
        cursor.moveToFirst();

        long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_ID));
        long activityTimerId = cursor.getLong(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_TIMER_ACTIVITY_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_NAME));
        long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_TIMER_ACTIVITY_TIMESTAMP));

        return new HappySession(itemId,activityTimerId,name,timestamp);

    }

    @Override
    public ArrayList<HappySession> getSessions() {

        String[] projection = {
                HappySession.TABLE_COLUMN_ID,
                HappySession.TABLE_COLUMN_TIMER_ACTIVITY_ID,
                HappySession.TABLE_COLUMN_NAME,
                HappySession.TABLE_COLUMN_TIMER_ACTIVITY_TIMESTAMP
        };

        String sortOrder = HappySession.TABLE_COLUMN_NAME + " ASC";

        Cursor cursor = executeGetAllQuery(HappySession.TABLE_NAME,projection,sortOrder);

        ArrayList<HappySession> sessions = new ArrayList<HappySession>();
        cursor.moveToFirst();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_ID));
            long activityTimerId = cursor.getLong(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_TIMER_ACTIVITY_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_NAME));
            long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(HappySession.TABLE_COLUMN_TIMER_ACTIVITY_TIMESTAMP));

            sessions.add(new HappySession(itemId,activityTimerId, name,timestamp));
        }

        return sessions;
    }

    @Override
    public boolean updateSessionName(long id,String name) {

        ContentValues values = new ContentValues();
        values.put(HappySession.TABLE_COLUMN_NAME, name);

        String selection = HappySession.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = database.update(
                HappySession.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count == 1;

    }

    @Override
    public boolean deleteSession(long id) {

        String selection = HappySession.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int deleteCount = database.delete(HappySession.TABLE_NAME, selection, selectionArgs);

        return deleteCount == 1;

    }
}
