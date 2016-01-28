package alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.warriors.group.icare.DietListActivity;
import com.warriors.group.icare.LoginActivity;
import com.warriors.group.icare.ProfileListActivity;
import com.warriors.group.icare.R;

/**
 * Created by Mobile App Develop on 12-1-16.
 */
public class MyBroadcastReceiver  extends BroadcastReceiver {

    SharedPreferences preferences;

    Context myContext;

    MediaPlayer mp;

    //NotificationModel notificationModel=new NotificationModel();


    String MSG=null;


    @Override
    public void onReceive(Context context, Intent intent) {

        mp=MediaPlayer.create(context, R.raw.alrm);
        mp.start();

        Toast.makeText(context, "Please Check your Notification",
                Toast.LENGTH_LONG).show();

        //preferences = getBaseContext().getSharedPreferences("notifi_title", MODE_PRIVATE);

        String title=intent.getStringExtra("TITLE_NOTIFI");
        String notifi_ID=intent.getStringExtra("ID_NOTIFI");
        String msg_Notify=intent.getStringExtra("NOTIFI_MSG");
        //intent.putExtra("NOTIFI_MSG",alarmMsg);

        int id=Integer.valueOf(notifi_ID);

                //("TITLE_NOTIFI", "Jewel");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.notification_icon);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(msg_Notify);

        //Intent resultIntent = new Intent(context, ResultActivity.class);

        Intent resultIntent = new Intent(context, LoginActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(LoginActivity.class);
//        stackBuilder.addParentStack(ResultActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

//       NotificationManager mNotificationManager = (NotificationManager) context.getSystemService();

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        int notificationID=id;
        mNotificationManager.notify(notificationID, mBuilder.build());

//        // Vibrate the mobile phone
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(2000);
    }

//
//    private void notify(String methodName) {
//
//        String name ="Jewel"; //this.getClass().getName();
//
//        String[] strings = name.split("\\.");
//
//        Notification noti = new Notification.Builder(myContext)
//
//                .setContentTitle(methodName).setAutoCancel(true)
//
//                .setSmallIcon(R.mipmap.ic_launcher)
//
//                .setContentText(name).build(my);
//
//        NotificationManager notificationManager =(NotificationManager) myContext.getSystemService(myContext.NOTIFICATION_SERVICE);
//
//        notificationManager.notify((int) System.currentTimeMillis(), noti);
//
//    }

}
