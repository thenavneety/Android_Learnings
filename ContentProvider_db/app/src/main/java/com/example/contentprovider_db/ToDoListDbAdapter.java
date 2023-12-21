package com.example.contentprovider_db;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class ToDoListDbAdapter {

    private static String DB_NAME = "todolist.db";
    private static int DB_Version = 2;

    private static String Table_todo = "table_todo";
    private static String Column_todo_ID = "task_id";
    private static String Column_todo = "todo";

    private static String Column_place = "place";

    private static String Table_To_Do = "CREATE TABLE " + Table_todo+"(" + Column_todo_ID+ "Integer Primary Key," + Column_todo+ "Text Not Null,"+ Column_place+ "Text NOT NULL)";

    private Context context;
    private SQLiteDatabase sqliteDatabase;
    private static ToDoListDbAdapter toDoListDbAdapterInstance;

    private ToDoListDbAdapter(Context context){
        this.context = context;
        sqliteDatabase = new ToDoListDBHelper(this.context,DB_NAME,null,DB_Version).getWritableDatabase();
    }

    //singelton implementation
    public static ToDoListDbAdapter getToDoListDBAdapterInstance(Context context){
        if(toDoListDbAdapterInstance==null){
            toDoListDbAdapterInstance = new ToDoListDbAdapter(context);
        }
        return toDoListDbAdapterInstance;
    }

    //insert,delete,modify

    public boolean insert(String toDoItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_todo,toDoItem);
        contentValues.put(Column_place,toDoItem);
        return sqliteDatabase.insert(Table_todo,null,contentValues)>0;
    }

    public boolean delete(int taskID){
        return sqliteDatabase.delete(Table_todo,Column_todo_ID+"="+taskID,null)>0;
    }
    public boolean modify(int taskID,String toDOItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_todo,toDOItem);
        return sqliteDatabase.update(Table_todo,contentValues,Column_todo_ID+"="+taskID,null)>0;
    }
    public List<toDo> getAllToDos(){
        List<toDo> toDoList=new ArrayList<toDo>();
        Cursor cursor = sqliteDatabase.query(Table_To_Do,new String[]{Column_todo_ID,Column_todo,Column_place},null,null,null,null,null);
        if(cursor!=null & cursor.getCount()>0){
            while(cursor.moveToNext()){
                toDo toDo = new toDo(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
                toDoList.add(toDo);
            }
        }
        cursor.close();
        return toDoList;
    }
    private static class ToDoListDBHelper extends SQLiteOpenHelper{
        public ToDoListDBHelper(Context context,String databasename,SQLiteDatabase.CursorFactory factory,int dbVersion){
            super(context,databasename,factory,dbVersion);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
            Log.v(TAG,"inside onCreate");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(Table_To_Do);
            Log.v(TAG,"inside onCreate");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion,    final int newVersion) {
//            Log.v(Tag,"inside onUpgrade");
//            switch(newVersion){
//                case 2: sqLiteDatabase.execSQL("ALTER TABLE "+Table_To_Do+" ADD COLUMN "+Column_place+" TEXT ");
//                break;
//                default:break;
//            }
        }
    }

}
