package alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.warriors.group.icare.R;

public class AlarmActivity extends AppCompatActivity {
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

    }

    public void startAlert(View view) {
//        EditText text = (EditText) findViewById(R.id.time);
//        int i = Integer.parseInt(text.getText().toString());
//        Intent intent = new Intent(this, MyBroadcastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (i * 1000), pendingIntent);
//        Toast.makeText(this, "Alarm set in " + i + " seconds",
//                Toast.LENGTH_LONG).show();

        callAlarm("Alarm 1 ",234324243,"Jewel");

    }

    public void startAlert2(View view) {
        callAlarm("Alarm 2 ",234324242,"Evan");
    }



    private void callAlarm(String alarmMsg, int RequestCode,String notificationTitle){

        EditText text = (EditText) findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());

        Intent intent = new Intent(this ,MyBroadcastReceiver.class);
        intent.putExtra("TITLE_NOTIFI",notificationTitle);
        intent.putExtra("ID_NOTIFI",String.valueOf(RequestCode));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(this, alarmMsg + i + " seconds",
                Toast.LENGTH_LONG).show();

    }

}
