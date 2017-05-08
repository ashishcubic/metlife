package metlife.lms.reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import metlife.lms.R;
import metlife.lms.helper.AlarmDBHelper;

public class editNotification extends Activity {
    private TextView editDate, editTime;
    private Button edit, cancel;
    private Calendar myCalendar;
    private int topic_id;
    private String topic_name;
    private int count;
    private AlarmDBHelper alarmdbhelper=null;
    private PendingIntent displayIntent=null;

    private AlarmManager alarmManager=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_notification);

        myCalendar = Calendar.getInstance();
        editDate = (TextView) findViewById(R.id.tveditdate);
        editTime = (TextView) findViewById(R.id.tvedittime);
        edit = (Button) findViewById(R.id.btneditsave);
        cancel = (Button) findViewById(R.id.btneditcancel);
        Bundle extras = getIntent().getExtras();
        topic_id =extras.getInt("topic_id");
        topic_name=extras.getString("topic_name");
        count=extras.getInt("request_code");

        final DatePickerDialog.OnDateSetListener mydate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };

        final TimePickerDialog.OnTimeSetListener mytime = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(editNotification.this, mydate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new TimePickerDialog(editNotification.this, mytime, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editNotification.this.finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editNotification.this.finish();
                long alarmTime=myCalendar.getTimeInMillis();
                if(alarmdbhelper==null) {
                    alarmdbhelper = new AlarmDBHelper(getApplicationContext());
                }



                long insertid=alarmdbhelper.editinsert(topic_name,alarmTime,count,topic_id);
                if(insertid==-1){
                    Toast t=Toast.makeText(editNotification.this, "Reminder not saved", Toast.LENGTH_LONG);
                    t.show();
                }

                //  long t1=alarmdbhelperdatabase.retrieve(insertid);
                // Calendar c=Calendar.getInstance();
                // c.editTimeInMillis(t1);

                alarmManager = (AlarmManager)getBaseContext().getSystemService(ALARM_SERVICE);
                Toast t=Toast.makeText(editNotification.this, "Reminder set for " + myCalendar.getTime(), Toast.LENGTH_LONG);
                t.show();

                Intent i= new Intent(getBaseContext(),Receiver.class);
                i.putExtra("topic_id", topic_id);
                i.putExtra("topic_name",topic_name);

                i.putExtra("request_code",count);
                displayIntent = PendingIntent.getBroadcast(getBaseContext(), count, i, PendingIntent.FLAG_CANCEL_CURRENT);

                // PendingIntent displayIntent=PendingIntent.getActivity(getBaseContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        myCalendar.getTimeInMillis(), displayIntent);



                 Intent refresh = new Intent(editNotification.this, WatchList.class);
                startActivity(refresh);
            }
        });

    }

    private void updateDateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel() {

        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTime.setText(sdf.format(myCalendar.getTime()));
    }

    public void CancelAlarm(Context context,int request_code)

    {
        alarmdbhelper = new AlarmDBHelper(context.getApplicationContext());
        alarmdbhelper.delete(request_code);

        Intent intent = new Intent(context, Receiver.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, request_code, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(sender);


    }

}
