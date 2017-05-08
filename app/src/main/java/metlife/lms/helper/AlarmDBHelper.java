package metlife.lms.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AlarmDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "alarmclock.db";
    private String alarm_name=null,history_name=null,favurite_name=null,favourite_content=null;
    private long time=0,insert_id,request_code,history_insert_id,favourite_insert_id;
    private long topic_id,history_id,favourite_id ;




    private static final String SQL_CREATE_ALARM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + AlarmDatabase.Alarm.TABLE_NAME + " (" +
                    AlarmDatabase.Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AlarmDatabase.Alarm.COLUMN_NAME_ALARM_NAME + " TEXT," +
                    AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TIME_HOUR + " INTEGER," + AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE + " INTEGER," +
                    AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID+ " INTEGER"+
                     " )";
    private static final String SQL_CREATE_HISTORY_TABLE =
            "CREATE TABLE IF NOT EXISTS " + AlarmDatabase.Historytrack.TABLE_NAME + " (" +
                    AlarmDatabase.Historytrack._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_ID + " INTEGER," +
                    AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_NAME + " TEXT" +
                    " )";
    private static final String SQL_CREATE_FAVOURITE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + AlarmDatabase.Favouritetrack.TABLE_NAME + " (" +
                    AlarmDatabase.Favouritetrack._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID + " INTEGER," +
                    AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_NAME+ " TEXT," +
                    AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_PARENT_CONTENT+ " TEXT" +
                    " )";

    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + AlarmDatabase.Alarm.TABLE_NAME;
    private static final String SQL_DELETE_HISTORY =
            "DROP TABLE IF EXISTS " + AlarmDatabase.Historytrack.TABLE_NAME;
    private static final String SQL_DELETE_FAVOURITE =
            "DROP TABLE IF EXISTS " + AlarmDatabase.Favouritetrack.TABLE_NAME;

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL(SQL_CREATE_ALARM_TABLE);
        db.execSQL(SQL_CREATE_HISTORY_TABLE);
        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM);
        db.execSQL(SQL_DELETE_HISTORY);
        db.execSQL(SQL_DELETE_FAVOURITE);
        onCreate(db);

    }


    public int insert(String name,long time,int request_code,int topic_id) {
        SQLiteDatabase db=this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_NAME,name);
        values.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TIME_HOUR,time);
        values.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE,request_code);
        values.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID, topic_id);



        // Inserting Row
        long insertcheck = db.insert(AlarmDatabase.Alarm.TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return (int) insertcheck;
    }

    public long retrieve(long id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AlarmDatabase.Alarm.TABLE_NAME, new String[] { AlarmDatabase.Alarm._ID,AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TIME_HOUR,
                        }, AlarmDatabase.Alarm._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        long time=cursor.getLong(1);
     return time;

    }
    public long retrievelast(){

        SQLiteDatabase db = this.getReadableDatabase();
        long last;
        Cursor cursor = db.query(AlarmDatabase.Alarm.TABLE_NAME, new String[]{AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE}, null, null, null, null, AlarmDatabase.Alarm._ID + " DESC", "1");
        if (cursor != null)
            cursor.moveToFirst();
       try{

        last=cursor.getInt(cursor.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE));
            return last;
       }
        catch(CursorIndexOutOfBoundsException c){
            return 0;
        }



    }
    public int editinsert(String name,long time,int request_code,int topic_id) {
        SQLiteDatabase db=this.getWritableDatabase();


        ContentValues newvalues = new ContentValues();
      //  newvalues.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_NAME,name);
       newvalues.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TIME_HOUR,time);
       // newvalues.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE,request_code);
        //newvalues.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID,topic_id);



        // Inserting Row
        long updatecheck = db.update(AlarmDatabase.Alarm.TABLE_NAME, newvalues, AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE + "=" + request_code, null);
        db.close(); // Closing database connection
        return (int) updatecheck;
    }
    public int delete(int request_code) {
        SQLiteDatabase db=this.getWritableDatabase();




        long deletecheck=db.delete(AlarmDatabase.Alarm.TABLE_NAME, AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE + "=" + request_code, null);



        db.close(); // Closing database connection
        return (int) deletecheck;
    }




    public ArrayList<HashMap<String, String>> getAllAlarms(Context c) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(AlarmDatabase.Alarm.TABLE_NAME, new String[] { AlarmDatabase.Alarm._ID,AlarmDatabase.Alarm.COLUMN_NAME_ALARM_NAME,AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TIME_HOUR,AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE,AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID
                }, null,
               null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        else{
            return null;
        }
        HashMap<String, String> hashMap ;
        ArrayList<HashMap<String, String>> Alarmlist = new ArrayList<HashMap<String,String>>();
        cursor.moveToFirst();
        do{
       // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            insert_id=cursor.getLong(cursor.getColumnIndex(AlarmDatabase.Alarm._ID));
           // int a=cursor.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID);
           // int b=cursor.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE);
            alarm_name = cursor.getString(cursor.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_NAME));
            time = cursor.getLong(cursor.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TIME_HOUR));
            topic_id=cursor.getInt(cursor.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID));

            request_code =cursor.getInt(cursor.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE));

                    Calendar c1 = Calendar.getInstance();
            c1.setTimeInMillis(time);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            hashMap = new HashMap<String, String>();
            hashMap.put(AlarmDatabase.Alarm._ID,String.valueOf(insert_id));
            hashMap.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_NAME,alarm_name);
            hashMap.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TIME_HOUR,String.valueOf(sdf.format(c1.getTime())));
            hashMap.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_REQUEST_CODE,String.valueOf(request_code));
            hashMap.put(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID,String.valueOf(topic_id));

            Alarmlist.add(hashMap);
        }while(cursor.moveToNext());
        return Alarmlist;
    }

    public int insertHistory(String topic_name,int topic_id) {
        SQLiteDatabase db=this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_ID,topic_id);
        values.put(AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_NAME,topic_name);




        // Inserting Row
        long history_insertcheck = db.insert(AlarmDatabase.Historytrack.TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return (int) history_insertcheck;
    }

    public ArrayList<HashMap<String, String>> getAllHistory(Context c) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor historycursor = db.query(AlarmDatabase.Historytrack.TABLE_NAME, new String[] { AlarmDatabase.Historytrack._ID,AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_ID,AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_NAME
                }, null,
                null, null, null, null, null);
        if (historycursor != null) {
            historycursor.moveToFirst();
        }
        else{
            return null;
        }
        HashMap<String, String> hashMap ;
        ArrayList<HashMap<String, String>> Historylist = new ArrayList<HashMap<String,String>>();
        historycursor.moveToFirst();
        do{
            // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            history_insert_id=historycursor.getLong(historycursor.getColumnIndex(AlarmDatabase.Historytrack._ID));

            history_name = historycursor.getString(historycursor.getColumnIndex(AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_NAME));

            history_id=historycursor.getInt(historycursor.getColumnIndex(AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_ID));




            hashMap = new HashMap<String, String>();
            hashMap.put(AlarmDatabase.Historytrack._ID,String.valueOf(insert_id));
            hashMap.put(AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_NAME,history_name);

            hashMap.put(AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_ID,String.valueOf(history_id));


            Historylist.add(hashMap);
        }while(historycursor.moveToNext());
        return Historylist;
    }


    //Favourites

    public int insertFav(String topic_name,int topic_id,String content) {
        SQLiteDatabase db=this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID,topic_id);
        values.put(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_NAME,topic_name);
        values.put(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_PARENT_CONTENT,content);




        // Inserting Row
        long fav_insertcheck = db.insert(AlarmDatabase.Favouritetrack.TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return (int) fav_insertcheck;
    }

    public ArrayList<HashMap<String, String>> getallFavourite(Context c) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor favcursor = db.query(AlarmDatabase.Favouritetrack.TABLE_NAME, new String[] { AlarmDatabase.Favouritetrack._ID,AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID,AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_NAME,AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_PARENT_CONTENT},
                null, null, null, null, null);
        if (favcursor != null) {
           favcursor.moveToFirst();
        }
        else{
            return null;
        }
        HashMap<String, String> hashMap ;
        ArrayList<HashMap<String, String>> Favouritelist = new ArrayList<HashMap<String,String>>();
        favcursor.moveToFirst();
        do{
            // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            favourite_insert_id=favcursor.getLong(favcursor.getColumnIndex(AlarmDatabase.Favouritetrack._ID));

            favurite_name = favcursor.getString(favcursor.getColumnIndex(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_NAME));

            favourite_id=favcursor.getInt(favcursor.getColumnIndex(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID));
            favourite_content=favcursor.getString(favcursor.getColumnIndex(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_PARENT_CONTENT));





            hashMap = new HashMap<String, String>();
            hashMap.put(AlarmDatabase.Favouritetrack._ID,String.valueOf(history_id));
            hashMap.put(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_NAME,favurite_name);

            hashMap.put(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID,String.valueOf(favourite_id));
            hashMap.put(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_PARENT_CONTENT,favourite_content);



            Favouritelist.add(hashMap);
        }while(favcursor.moveToNext());
        return Favouritelist;
    }

    public int deletefav(String topic_name,int topic_id) {
        SQLiteDatabase db=this.getWritableDatabase();




        long deletecheck=db.delete(AlarmDatabase.Favouritetrack.TABLE_NAME, AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID + "=" + topic_id, null);



        db.close(); // Closing database connection
        return (int) deletecheck;
    }

    public boolean selectFav(int topic_id)
    {
        String value="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.query(AlarmDatabase.Favouritetrack.TABLE_NAME, new String[]{AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID}, AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID + "=" + topic_id,
                null, null, null, null, null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }
    public boolean selectAlarmCheck(int topic_id)
    {
        String value="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.query(AlarmDatabase.Alarm.TABLE_NAME, new String[]{AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID}, AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID + "=" + topic_id,
                null, null, null, null, null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }
    public String selectFavCheck(int topic_id)
    {
        String value="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.query(AlarmDatabase.Favouritetrack.TABLE_NAME, new String[]{AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID}, AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID + "=" + topic_id,
                null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            value = c.getString(c.getColumnIndex(AlarmDatabase.Favouritetrack.COLUMN_NAME_FAVOURITE_TOPIC_ID));

        }
        return value;
    }
    public String selectAlarmSet(int topic_id)
    {
        String value="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.query(AlarmDatabase.Alarm.TABLE_NAME, new String[]{AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID}, AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID + "=" + topic_id,
                null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            value = c.getString(c.getColumnIndex(AlarmDatabase.Alarm.COLUMN_NAME_ALARM_TOPIC_ID));

        }
        return value;
    }
   public void setSqlDeleteHistory(String tableName)
   {
       SQLiteDatabase db=this.getWritableDatabase();
       db.execSQL("delete from " + tableName);
       db.close();
   }
    public int deleteHistory(int topic_id) {
        SQLiteDatabase db=this.getWritableDatabase();

        long deletecheck=db.delete(AlarmDatabase.Historytrack.TABLE_NAME, AlarmDatabase.Historytrack.COLUMN_NAME_HISTORY_TOPIC_ID + "=" + topic_id, null);
        db.close(); // Closing database connection
        return (int) deletecheck;
    }
}