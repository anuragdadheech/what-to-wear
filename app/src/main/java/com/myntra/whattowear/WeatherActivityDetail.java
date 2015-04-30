package com.myntra.whattowear;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class WeatherActivityDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_activity_detail);
        Intent intent = getIntent();
        int events = intent.getIntExtra("events", 0);
        int parties = intent.getIntExtra("parties", 0);
        int meetings = intent.getIntExtra("meetings", 0);

        TextView tvEvents = (TextView)findViewById(R.id.meeting_count);
        tvEvents.setText(""+events);
        TextView tvParties = (TextView)findViewById(R.id.party_text);
        tvParties.setText(""+parties);
        TextView tvMeetings = (TextView)findViewById(R.id.meeting_text);
        tvMeetings.setText(""+meetings);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_activity_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
