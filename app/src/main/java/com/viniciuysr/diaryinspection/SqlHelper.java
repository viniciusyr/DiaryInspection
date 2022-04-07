package com.viniciuysr.diaryinspection;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "inspecoes_diario.db";
    private static final int DB_VERSION = 1;
    private static SqlHelper INSTANCE;

    static SqlHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SqlHelper(context);
        return INSTANCE;
    }

    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                " CREATE TABLE IF NOT EXISTS inspec (id INTEGER primary key, type_pedido INTEGER, item TEXT, date_time DATETIME)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("Teste", "Upgrade ok!");
    }


    List<Register> getRegistersBy(int type) {
        List<Register> registers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM inspec WHERE type_pedido = ?", new String[]{String.valueOf(type)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    Register register = new Register();

                    register.type = cursor.getInt(cursor.getColumnIndex("pedido"));
                    register.response = cursor.getString(cursor.getColumnIndex("item"));
                    register.createdDate = cursor.getString(cursor.getColumnIndex("datetime"));

                    registers.add(register);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("SQLite", e.getMessage(), e);
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return registers;
    }

    long addItem(int type, String response) {
        SQLiteDatabase db = getWritableDatabase();

        long pedId = 0;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("type_pedido", type);
            values.put("item", response);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            String now = sdf.format(new Date());

            values.put("date_time", now);
            pedId = db.insertOrThrow("inspec", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("SQLite", e.getMessage(), e);
        } finally {
            if (db.isOpen())
                db.endTransaction();
        }
        return pedId;
    }

}
