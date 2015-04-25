package com.myntra.whatToWear;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import butterknife.InjectView;
import io.realm.Realm;


public class MainActivity extends ActionBarActivity {

    public final String LOG_TAG = MainActivity.class.getSimpleName();
    static TextView day;
    static TextView date;
    static TextView weatherCode;
    static TextView weatherCondition;
    static TextView weatherDesc;
    static TextView humidity;
    static TextView calenderData;
    static Button btn;
    static ProgressBar spinner;
    int notification_id = 1;

    SparseArray<String> weatherArray = new SparseArray<String>()
    {{
            put(200, "climacons_cls");
            put(201, "climacons_cls");
            put(202, "climacons_cls");
            put(210, "climacons_cls");
            put(211, "climacons_cls");
            put(212, "climacons_cls");
            put(221, "climacons_cls");
            put(230, "climacons_cls");
            put(231, "climacons_cls");
            put(232, "climacons_cls");

            put(300, "climacons_cds");
            put(301, "climacons_cds");
            put(302, "climacons_cds");
            put(310, "climacons_cds");
            put(311, "climacons_cds");
            put(312, "climacons_cds");
            put(313, "climacons_cds");
            put(314, "climacons_cds");
            put(321, "climacons_cds");


            put(500, "climacons_crs");
            put(501, "climacons_crs");
            put(502, "climacons_crs");
            put(503, "climacons_crs");
            put(504, "climacons_crs");
            put(511, "climacons_ccs");
            put(520, "climacons_cds");
            put(521, "climacons_cds");
            put(522, "climacons_cds");
            put(531, "climacons_cds");


            put(600, "climacons_ccs");
            put(601, "climacons_ccs");
            put(602, "climacons_ccs");
            put(611, "climacons_ccs");
            put(612, "climacons_ccs");
            put(615, "climacons_ccs");
            put(616, "climacons_ccs");
            put(620, "climacons_ccs");
            put(621, "climacons_ccs");
            put(622, "climacons_ccs");


            put(701, "climacons_cws");
            put(711, "climacons_cws");
            put(721, "climacons_cws");
            put(731, "climacons_cws");
            put(741, "climacons_cws");
            put(751, "climacons_cws");
            put(761, "climacons_cws");
            put(762, "climacons_cws");
            put(771, "climacons_cws");
            put(781, "climacons_cws");



            put(800, "climacons_sun");
            put(801, "climacons_cs");
            put(802, "climacons_css");
            put(803, "climacons_ccs");
            put(804, "climacons_ccs");


            put(900, "climacons_cl");
            put(901, "climacons_cl");
            put(902, "climacons_cl");

            put(903, "climacons_css");
            put(904, "climacons_sun");
            put(905, "climacons_wind");
            put(906, "climacons_ccs");

            put(951, "climacons_wind");
            put(952, "climacons_wind");
            put(953, "climacons_wind");
            put(954, "climacons_wind");
            put(955, "climacons_wind");
            put(956, "climacons_wind");
            put(957, "climacons_wind");
            put(958, "climacons_wind");
            put(959, "climacons_wind");
            put(960, "climacons_cl");
            put(961, "climacons_cl");
            put(962, "climacons_cl");
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location loc){
                if(loc != null){
                    FetchWeatherTask weatherTask = new FetchWeatherTask();
                    weatherTask.execute(loc);

                    FetchCalenderTask calenderTask = new FetchCalenderTask(getApplicationContext());
                    calenderTask.execute();

                    spinner = (ProgressBar)findViewById(R.id.progressBar1);
                    spinner.setVisibility(View.VISIBLE);

                }

            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            date = (TextView)rootView.findViewById(R.id.date);
            day = (TextView)rootView.findViewById(R.id.day);
            weatherCode = (TextView)rootView.findViewById(R.id.weather_code);
            weatherCondition = (TextView)rootView.findViewById(R.id.weather_condition);
            weatherDesc = (TextView)rootView.findViewById(R.id.weather_desc);
            humidity = (TextView)rootView.findViewById(R.id.weather_humidity);
            calenderData = (TextView)rootView.findViewById(R.id.calender_details);
            btn = (Button) rootView.findViewById(R.id.button_hello);
            return rootView;
        }



    }
    public class FetchWeatherTask extends AsyncTask<Location, Void, WeatherData> {


        @Override
        protected WeatherData doInBackground(Location... params) {
            if (params == null) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            String api_key = "a83c918bcb58191e945aa227248fed99";
            String format = "json";
            String units = "metric";
            double latitude = params[0].getLatitude();
            double longitude = params[0].getLongitude();
            int numDays = 5;
            // Weather information.  Each day's forecast info is an element of the "list" array.
            final String OWM_LIST = "list";

            try {
                //sample query: http://api.openweathermap.org/data/2.5/forecast/daily?lat=12.93&lon=77.63&cnt=10&mode=json&units=metric
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL =
                        "http://api.openweathermap.org/data/2.5/weather?";
                final String API_PARAM = "APPID";
                final String LAT_PARAM = "lat";
                final String LON_PARAM = "lon";
                final String UNITS_PARAM = "units";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(LAT_PARAM, ""+latitude)
                        .appendQueryParameter(LON_PARAM, ""+longitude)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(API_PARAM, api_key)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                spinner.setVisibility(View.GONE);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            final String OWM_DATETIME = "dt";

            // All temperatures are children of the "temp" object.
            final String OWM_HUMIDITY = "humidity";
            final String OWM_MAIN = "main";
            final String OWM_DESC = "description";

            final String OWM_WEATHER = "weather";
            final String OWM_WEATHER_ID = "id";
            WeatherData wData = new WeatherData(); // Create a new weather object

            try{
                JSONObject forecastJson = new JSONObject(forecastJsonStr);
                JSONArray weatherArray = forecastJson.getJSONArray(OWM_WEATHER);
                long dateTime;
                String weather;
                String description;
                int weatherId;
                double humidity;
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                dateTime = forecastJson.getLong(OWM_DATETIME);
                weatherId = weatherObject.getInt(OWM_WEATHER_ID);
                JSONObject mainObject = forecastJson.getJSONObject(OWM_MAIN);
                weather = weatherObject.getString(OWM_MAIN);
                description = weatherObject.getString(OWM_DESC);
                humidity = mainObject.getDouble(OWM_HUMIDITY);
                DateFormat date = new SimpleDateFormat("dd-MM-yyyy");


                //Storing the data using REALM.IO

                wData.setDate(date.format(new Date(dateTime*1000)));
                wData.setWeathercode(weatherId);
                wData.setHumidity(humidity);
                wData.setWeathercondition(weather);
                wData.setWeatherdetail(description);

                Realm realm = Realm.getInstance(getApplicationContext());
                //transaction
                realm.beginTransaction();
                WeatherData data = realm.copyToRealmOrUpdate(wData);
                realm.commitTransaction();

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                spinner.setVisibility(View.GONE);
                e.printStackTrace();
            }

            return wData;
        }

        @Override
        protected void onPostExecute(WeatherData w) {
            if(w != null) {
                super.onPostExecute(w);
                humidity.setText(Double.toString(w.getHumidity()));
                weatherCode.setText(Double.toString(w.getWeathercode()));
                date.setText(w.getDate());
                day.setText(Utils.getDayName(getApplicationContext(), new Date()));
                weatherDesc.setText(w.getWeatherdetail());
                weatherCondition.setText(w.getWeathercondition());
                spinner.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show_notification();
                    }
                });
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void show_notification() {
            // prepare intent which is triggered if the
            // notification is selected

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            // build notification
            // the addAction re-use the same intent to keep the example short
            Notification n  = new Notification.Builder(getApplicationContext())
                    .setContentTitle("New notification")
                    .setContentText("2 notifications")
                    .setSmallIcon(R.drawable.star)
                    .setContentIntent(pIntent)
                    .setStyle(new Notification.BigTextStyle().bigText(getString(R.string.big_notification)))
                    .setAutoCancel(true).build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }
    }
}
