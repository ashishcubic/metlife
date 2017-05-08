package metlife.lms.reminder;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import metlife.lms.R;
import metlife.lms.helper.AlarmDBHelper;

public class SetNotification extends Activity {

    private TextView tvdate, tvtime;
    private Button save, cancel;
    private Calendar myCalendar;
    private int topic_id;
    private String topic_name;
    private String date;
    private String time;
    private int count;
    private AlarmDBHelper alarmdbhelperdatabase=null;
    private AlarmManager alarmManager=null;
    View spl_hori_time;
    LinearLayout btnLayout;



    ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
    private int feed_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_notification);

        myCalendar = Calendar.getInstance();
        tvdate = (TextView) findViewById(R.id.tvsetdate);
        tvtime = (TextView) findViewById(R.id.tvsettime);
        save = (Button) findViewById(R.id.btnsave);
        cancel = (Button) findViewById(R.id.btncancel);

        spl_hori_time=(View)findViewById(R.id.SplitLine_hor5);
        btnLayout=(LinearLayout)findViewById(R.id.btnLayout);

        Bundle extras = getIntent().getExtras();
        topic_id =extras.getInt("topic_id");
        topic_name=extras.getString("topic_name");
        date =extras.getString("date");
        time=extras.getString("time");
        Intent intent=getIntent();
        feed_status=intent.getIntExtra("feedback",0);
        if(date.equals("DD-MM-YYYY") || time.equals("HH:MM:SS"))
        {
            save.setVisibility(View.GONE);
            tvtime.setVisibility(View.GONE);
            spl_hori_time.setVisibility(View.GONE);
            btnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            tvdate.setText("Sorry,You don't have appointment.");

        }
        else {
            tvdate.setText(date);
            tvtime.setText(time);
            String [] dateParts = date.split("-");
            String day = dateParts[0];
            String month = dateParts[1];
            String year = dateParts[2];

            String [] timeParts = time.split(":");
            String hour = timeParts[0];
            String minute= timeParts[1];


            myCalendar.set(Calendar.YEAR, Integer.parseInt(year));
            myCalendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
            myCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
            myCalendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
            myCalendar.set(Calendar.MINUTE,Integer.parseInt(minute));

            save.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (feed_status==0) {

                    // TODO Auto-generated method stub
                    SetNotification.this.finish();
                    long alarmTime=myCalendar.getTimeInMillis();
                    if(alarmdbhelperdatabase==null) {
                        alarmdbhelperdatabase = new AlarmDBHelper(getApplicationContext());
                    }
                    count = (int)(alarmdbhelperdatabase.retrievelast());

                    count++;
                    long insertid=alarmdbhelperdatabase.insert(topic_name,alarmTime,count,topic_id);
                    if(insertid==-1){
                        Toast t=Toast.makeText(SetNotification.this, "Reminder not saved", Toast.LENGTH_LONG);
                        t.show();
                    }

                    //  long t1=alarmdbhelperdatabase.retrieve(insertid);
                    // Calendar c=Calendar.getInstance();
                    // c.setTimeInMillis(t1);

                        alarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);
                        Toast t = Toast.makeText(SetNotification.this, "Reminder set for your appointment before 2 hours.", Toast.LENGTH_LONG);
                        t.show();

                        Intent i = new Intent(getBaseContext(), Receiver.class);
                        i.putExtra("topic_id", topic_id);
                        i.putExtra("topic_name", topic_name);

                        i.putExtra("request_code", count);
                        PendingIntent displayIntent = PendingIntent.getBroadcast(getBaseContext(), count, i, PendingIntent.FLAG_CANCEL_CURRENT);

                        // PendingIntent displayIntent=PendingIntent.getActivity(getBaseContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP,
                                myCalendar.getTimeInMillis() - (120 * 60000), displayIntent);
                        intentArray.add(displayIntent);

                    }else if (feed_status==2){

                        SetNotification.this.finish();
                        long alarmTime=myCalendar.getTimeInMillis();
                        if(alarmdbhelperdatabase==null) {
                            alarmdbhelperdatabase = new AlarmDBHelper(getApplicationContext());
                        }
                        count = (int)(alarmdbhelperdatabase.retrievelast());

                        count++;
                        long insertid=alarmdbhelperdatabase.insert(topic_name,alarmTime,count,topic_id);
                        if(insertid==-1){
                            Toast t=Toast.makeText(SetNotification.this, "Reminder not saved", Toast.LENGTH_LONG);
                            t.show();
                        }
                        alarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);
                        Toast t = Toast.makeText(SetNotification.this, "Reminder set for your callBack on Time", Toast.LENGTH_LONG);
                        t.show();

                        Intent i = new Intent(getBaseContext(), Receiver.class);
                        i.putExtra("topic_id", topic_id);
                        i.putExtra("topic_name", topic_name);

                        i.putExtra("request_code", count);
                        PendingIntent displayIntent = PendingIntent.getBroadcast(getBaseContext(), count, i, PendingIntent.FLAG_CANCEL_CURRENT);

                        // PendingIntent displayIntent=PendingIntent.getActivity(getBaseContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP,
                                myCalendar.getTimeInMillis(), displayIntent);
                        intentArray.add(displayIntent);
                    }

                    // Intent i = new Intent(SetNotification.this, Home.class);
                    // startActivity(i);
                }
            });


        }


        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SetNotification.this.finish();
            }
        });


    }//oncreate ends

}
