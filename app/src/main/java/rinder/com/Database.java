package rinder.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import rinder.com.mod.user;

public class Database extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "rinder";

    private static final int DATABASE_VERSION = 1;

    public static Database sqlite;

    public Database(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(user.CREATE_TABLE_USERS);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j){
        db.execSQL(String.format("DROP TABLE IF EXISTS '%s'", user.TABLE_USERS));
        onCreate(db);
    }

    public Cursor read(String table, String[] cols, String whereCol, String[] whereVal, String orderCol){
        SQLiteDatabase db = getReadableDatabase();
        Cursor curr = db.query(table, cols, whereCol, whereVal, null, null, orderCol);
        return curr;
    }

    public void insert(String table, ContentValues val){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table, null, val);
        db.close();
    }

    public void update(String table, ContentValues val, String whereCol, String[] whereVal){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table, val, whereCol, whereVal);
        db.close();
    }

    public void delete(String table, String whereCol, String[] whereVal){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(table, whereCol, whereVal);
        db.close();
    }
}
