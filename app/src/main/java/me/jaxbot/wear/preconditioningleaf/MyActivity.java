package me.jaxbot.wear.preconditioningleaf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MyActivity extends ActionBarActivity {
    final Context that = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlarmSetterAC.setAlarmsForToday(this);
        AlarmSetterAC.setAlarmSetterAlarm(this);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

