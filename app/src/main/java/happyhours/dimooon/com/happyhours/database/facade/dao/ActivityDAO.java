package happyhours.dimooon.com.happyhours.database.facade.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyActivity;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.Activity;

/**
 * Created by dimooon on 8/26/15.
 */
public class ActivityDAO extends HappyDAO implements Activity{

    public ActivityDAO(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public long createActivity(long value) {
        ContentValues values = new ContentValues();
        values.put(HappyActivity.TABLE_COLUMN_VALUE, value);

        return executeInsert(HappyActivity.TABLE_NAME,HappyActivity.TABLE_COLUMN_NAME_NULLABLE,values);
    }

    @Override
    public HappyActivity getActivity(long id) {
        String[] projection = {
                HappyActivity.TABLE_COLUMN_ID,
                HappyActivity.TABLE_COLUMN_VALUE,
        };

        String sortOrder = HappyActivity.TABLE_COLUMN_VALUE + " ASC";

        Cursor cursor = executeSingleGetQuery(HappyActivity.TABLE_NAME,projection,HappyActivity.TABLE_COLUMN_ID + "=?",sortOrder,id);
        cursor.moveToFirst();

        long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyActivity.TABLE_COLUMN_ID));
        long value = cursor.getLong(cursor.getColumnIndexOrThrow(HappyActivity.TABLE_COLUMN_VALUE));

        return new HappyActivity(itemId,value);
    }

    @Override
    public ArrayList<HappyActivity> getActivities() {
        String[] projection = {
                HappyActivity.TABLE_COLUMN_ID,
                HappyActivity.TABLE_COLUMN_VALUE,
        };

        String sortOrder = HappyActivity.TABLE_COLUMN_VALUE + " ASC";

        Cursor cursor = executeGetAllQuery(HappyActivity.TABLE_NAME,projection,sortOrder);

        ArrayList<HappyActivity> timers = new ArrayList<HappyActivity>();
        cursor.moveToFirst();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HappyActivity.TABLE_COLUMN_ID));
            long value = cursor.getLong(cursor.getColumnIndexOrThrow(HappyActivity.TABLE_COLUMN_VALUE));

            timers.add(new HappyActivity(itemId,value));
        }

        return timers;
    }

    @Override
    public boolean updateActivity(long id, long value) {
        ContentValues values = new ContentValues();
        values.put(HappyActivity.TABLE_COLUMN_VALUE, value);

        String selection = HappyActivity.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = database.update(
                HappyActivity.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count == 1;
    }

    @Override
    public boolean deleteActivity(long id) {
        String selection = HappyActivity.TABLE_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int deleteCount = database.delete(HappyActivity.TABLE_NAME, selection, selectionArgs);

        return deleteCount == 1;
    }
}
