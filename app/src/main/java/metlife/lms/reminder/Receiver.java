package metlife.lms.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import metlife.lms.R;
import metlife.lms.activity.DetailActivity;

/**
 * Created by Ashish on 22/10/2015.
 */
public class Receiver extends BroadcastReceiver {

         int request_code=0;

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            int topic_id = extras.getInt("topic_id");
            String topic_name=extras.getString("topic_name");
            request_code=extras.getInt("request_code");
            Toast.makeText(context, "You have fixed appointment with !!!!."+topic_name,
                    Toast.LENGTH_LONG).show();
            
            // Vibrate the mobile phone
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);
            createNotification(context, topic_id,topic_name);

        }

    public void createNotification(Context c,int topic_id,String topic_n) {
        NotificationManager mNM;
       // Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Uri alarmSound = Uri.parse("android.resource://"+c.getPackageName()+"/raw/appointment");
       // int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        Intent i=new Intent(c.getApplicationContext(),DetailActivity.class);
       // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
       // Bundle bundle = new Bundle();
       // bundle.putInt("topic_id", topic_id);
        String job_id=String.valueOf(topic_id);
        i.putExtra("lead_id",job_id);
        //TTS.mTts.speak("Its Time to cook " + topic_n, TextToSpeech.QUEUE_FLUSH,null);
        mNM = (NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c);
        PendingIntent pIntent = PendingIntent.getActivity(c.getApplicationContext(), request_code, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification n=builder.setContentTitle("You appointment for today")
                .setContentText("You have fixed appointment with " + topic_n)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true).setSound(alarmSound)
                .build();

        //.addAction(R.drawable.beyondteaching, "click to open app", pIntent)

            n.flags = Notification.FLAG_AUTO_CANCEL;
            mNM.notify(request_code, n);
        }

    }



