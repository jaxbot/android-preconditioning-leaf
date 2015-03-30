package me.jaxbot.wear.preconditioningleaf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyActivity extends ActionBarActivity {
    final Context that = this;
    WebView webview;
    ArrayList<ScheduledStart> scheduleArray = new ArrayList<ScheduledStart>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        AlarmSetterAC.setAlarmsForToday(this);
        AlarmSetterAC.setAlarmSetterAlarm(this);

        scheduleArray.add(new ScheduledStart("Test 1", 0, 10));
        scheduleArray.add(new ScheduledStart("Test 2", 0, 15));
        scheduleArray.add(new ScheduledStart("Test 3", 0, 18));
        scheduleArray.add(new ScheduledStart("Test 4", 0, 19));

    }

    void showToast(String text)
    {
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_signoff) {
            SharedPreferences settings = getSharedPreferences("U", 0);
            SharedPreferences.Editor editor = settings.edit();

            editor.putString("username", "");
            editor.putString("password", "");

            editor.commit();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ListView listView = (ListView)(findViewById(R.id.listView));
        listView.setAdapter(new ScheduleArrayAdapter(this,  scheduleArray));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

