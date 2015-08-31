package happyhours.dimooon.com.happyhours.database.facade.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class HappyDAO {

    protected SQLiteDatabase database;

    public HappyDAO(SQLiteDatabase database) {
        this.database = database;
    }

    protected long executeInsert(String tableName,String nullable,ContentValues contentValues){
        return database.insert(
                tableName,
                nullable,
                contentValues);
    }

    protected Cursor executeSingleGetQuery(String tableName, String[] projection,String where, String sortOrder,long id){
        return database.query(
                tableName,
                projection,
                where,
                new String[]{String.valueOf(id)},
                null,
                null,
                sortOrder
        );

    }
    protected Cursor executeGetAllQuery(String tableName,String[] projection,String sortOrder){
        return database.query(
                tableName,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

}
