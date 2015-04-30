package com.myntra.whatToWear;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;

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

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.os.Build;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.ViewSwitcher;

import com.myntra.whattowear.CoverFlowAdapter;
import com.myntra.whattowear.ProductEntity;
import com.squareup.picasso.Picasso;
import com.myntra.whattowear.WeatherData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//import butterknife.InjectView;
import io.realm.Realm;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;


public class MainActivity extends ActionBarActivity {

    public final String LOG_TAG = MainActivity.class.getSimpleName();
    static TextView day;
    static TextView date;
    static TextView weatherCode;
    static TextView weatherCondition;
    static TextView weatherDesc;
    static TextView humidity;
    static TextView staticMeetingsText;
    static TextView calenderData;

    static TextView weatherText;
    static TextView meetingText;
    static ImageView weatherIcon;
    static ProgressBar spinner2;
    static String[] colors;
    static ActionBar bar;

    static TextView weatherLoader;
    static TextView clothesLoader;
    static TextView calendarLoader;

    static CardView weatherCard;
    static CardView topCard;
    static CardView bottomCard;
    static int clothesLoaded = 0;
    static boolean resumeLoaded = false;

    static int events;
    static int meetings;
    static int parties;

    private FeatureCoverFlow mCoverFlow;
    private FeatureCoverFlow mCoverFlow2;
    private CoverFlowAdapter mAdapter;
    private CoverFlowAdapter mAdapter2;
    private ArrayList<ProductEntity> mData = new ArrayList<>(0);
    private ArrayList<ProductEntity> mDataBottom = new ArrayList<>(0);

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

    PorterDuffColorFilter colorFilterIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        bar = getSupportActionBar();
        bar.setElevation(0);

    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("whattowear", MODE_PRIVATE);
        String top = prefs.getString("today_top", "");
        String bottom = prefs.getString("today_bottom", "");

        if(!top.isEmpty()){
            topCard.setVisibility(View.GONE);
            CardView topToday = (CardView) findViewById(R.id.card_view_top_today);
            topToday.setVisibility(View.INVISIBLE);
            Picasso.with(this).load(top).into((ImageView) findViewById(R.id.image_top_today));
            topToday.setVisibility(View.VISIBLE);
        }

        if(!bottom.isEmpty()){
            bottomCard.setVisibility(View.GONE);
            CardView bottomToday = (CardView) findViewById(R.id.card_view_bottom_today);
            bottomToday.setVisibility(View.INVISIBLE);
            Picasso.with(this).load(bottom).into((ImageView) findViewById(R.id.image_bottom_today));
            bottomToday.setVisibility(View.VISIBLE);
        }

        if(!resumeLoaded){
            com.myntra.whatToWear.MyLocation.LocationResult locationResult = new com.myntra.whatToWear.MyLocation.LocationResult(){
                @Override
                public void gotLocation(Location loc){
                    if(loc != null){
                        FetchWeatherTask weatherTask = new FetchWeatherTask();
                        weatherTask.execute(loc);


                        FetchCalenderTask calenderTask = new FetchCalenderTask(getApplicationContext());
                        calenderTask.execute();

                        SharedPreferences prefs = getSharedPreferences("whattowear", MODE_PRIVATE);

                        String top = prefs.getString("today_top", "");
                        String bottom = prefs.getString("today_bottom", "");

                        if(top.isEmpty()){
                            FetchTopTask topTask = new FetchTopTask();
                            topTask.execute("men%20formal%20shirts");

                        }

                        if(bottom.isEmpty()){
                            FetchBottomTask bottomTask = new FetchBottomTask();
                            bottomTask.execute("men%20pants%20formal");
                        }


                        if(spinner == null){
                            spinner = (ProgressBar)findViewById(R.id.progressBar1);
                            spinner.setVisibility(View.VISIBLE);
                        }

                    }

                }
            };
            com.myntra.whatToWear.MyLocation myLocation = new com.myntra.whatToWear.MyLocation();
            myLocation.getLocation(this, locationResult);
            resumeLoaded = true;
        }

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
        if (id == R.id.action_refresh) {
            SharedPreferences.Editor editor = getSharedPreferences("whattowear", MODE_PRIVATE).edit().clear();
            editor.apply();
//            Intent mStartActivity = new Intent(getApplicationContext(), MainActivity.class);
//            int mPendingIntentId = 123456;
//            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
//            AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 300, mPendingIntent);
//            System.exit(0);
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
            meetingText = (TextView)rootView.findViewById(R.id.meeting_text);
            calenderData = (TextView)rootView.findViewById(R.id.calender_details);
            staticMeetingsText = (TextView)rootView.findViewById(R.id.meeting_text_static);
            weatherText = (TextView)rootView.findViewById(R.id.weather_text);
            weatherIcon = (ImageView)rootView.findViewById(R.id.weatherIcon);
            weatherLoader = (TextView) rootView.findViewById(R.id.loader_weather);
            clothesLoader = (TextView) rootView.findViewById(R.id.loader_clothes);
            calendarLoader = (TextView) rootView.findViewById(R.id.loader_calendar);
            weatherLoader.setVisibility(View.VISIBLE);
            calendarLoader.setVisibility(View.VISIBLE);



            weatherCard = (CardView)rootView.findViewById(R.id.card_view_weather);
            weatherCard.setCardElevation(10);
            weatherCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), WeatherActivityDetail.class);
                    intent.putExtra("events", events);
                    intent.putExtra("meetings", meetings);
                    intent.putExtra("parties", parties);
                    startActivity(intent);
                }
            });
            topCard = (CardView)rootView.findViewById(R.id.card_view_top);
            topCard.setCardElevation(10);
            bottomCard = (CardView)rootView.findViewById(R.id.card_view_bottom);
            bottomCard.setCardElevation(10);


            btn = (Button) rootView.findViewById(R.id.button_hello);

            return rootView;
        }

    }

    public class FetchCalenderTask extends AsyncTask<Location, Void, Integer>  {
        private Context mContext;
        public final String LOG_TAG = MainActivity.class.getSimpleName();
        public FetchCalenderTask (Context context){
            mContext = context;
        }
        public final String[] EVENT_PROJECTION = new String[] {
                CalendarContract.Events._ID,                           // 0
                CalendarContract.Events.TITLE,                  // 1
                CalendarContract.Events.DTSTART,                  // 1
                CalendarContract.Events.DTEND,                  // 1
                CalendarContract.Events.EVENT_LOCATION                  // 1
        };


        // The indices for the projection array above.
        private static final int PROJECTION_ID_INDEX = 0;
        private static final int PROJECTION_TITLE_INDEX = 1;
        private static final int PROJECTION_START_INDEX = 2;
        private static final int PROJECTION_END_INDEX = 3;
        private static final int PROJECTION_LOCATION_INDEX = 4;

        @Override
        protected Integer doInBackground(Location... locations) {

            String calenderData = "";
            // today
            Calendar today = new GregorianCalendar();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
            Calendar tonight = (Calendar)today.clone();
            tonight.add(Calendar.DAY_OF_MONTH, 1);


            // Run query
            Cursor cur = null;
            ContentResolver cr = mContext.getContentResolver();
            Uri uri = CalendarContract.Events.CONTENT_URI;
            String selection = "((" + CalendarContract.Events.DTSTART + " > ?) AND ("
                    + CalendarContract.Events.DTEND + " < ?))";
            String[] selectionArgs = new String[] {""+(today.getTimeInMillis()), ""+(tonight.getTimeInMillis())};
            // Submit the query and get a Cursor object back.
            cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

            // Use the cursor to step through the returned records

            int count = 0;
            while (cur.moveToNext()) {
                count++;
                long eventID = 0;
                String eventTitle = null;
                String location = null;
                long start = 0;
                long end = 0;

                // Get the field values
                eventID = cur.getLong(PROJECTION_ID_INDEX);
                eventTitle = cur.getString(PROJECTION_TITLE_INDEX);
                start = cur.getLong(PROJECTION_START_INDEX);
                end = cur.getLong(PROJECTION_END_INDEX);
                location = cur.getString(PROJECTION_LOCATION_INDEX);

                if(eventTitle.toLowerCase().contains("meeting".toLowerCase())){
                    meetings++;
                }
                if(eventTitle.toLowerCase().contains("party".toLowerCase())){
                    parties++;
                }

                //TODO: Do something with the values...
                Log.e(LOG_TAG, "Event ID : "+eventID+" | duration : "+eventTitle);

                final String[] ATTENDEE_PROJECTION = new String[] {
                        CalendarContract.Attendees.ATTENDEE_NAME,                           // 0
                };

                // The indices for the projection array above.
                final int PROJECTION_ATTENDEE_NAME_INDEX = 0;
                Cursor attendeeCur = null;
                Uri attendeeUri = CalendarContract.Attendees.CONTENT_URI;
                String attendeeSelection = "((" + CalendarContract.Attendees.EVENT_ID + " = ?))";
                String[] attendeeSelectionArgs = new String[] {""+eventID};
                // Submit the query and get a Cursor object back.
                attendeeCur = cr.query(attendeeUri, ATTENDEE_PROJECTION, attendeeSelection, attendeeSelectionArgs, null);
                String name = "";
                while (attendeeCur.moveToNext()) {
                    name += " | " + attendeeCur.getString(0);
                }
                Log.e(LOG_TAG, name);
            }
            if(count == 0){
                calenderData = "No events today, wear casual clothes";
                //No events today, wear casual clothes
            }
            else if (count > 0 && count <= 2 ){
                calenderData = "A few meetings today, be cool";
            }
            else {
                calenderData = "Too many meetings, be formal";
            }
            events = count;


            return count;
        }

        @Override
        protected void onPostExecute(Integer calenderData) {
            super.onPostExecute(calenderData);
            Utils.counter(calenderData, MainActivity.meetingText);
            staticMeetingsText.setText("Events");
            calendarLoader.setVisibility(View.GONE);

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
            clothesLoader.setVisibility(View.VISIBLE);
            if(w != null) {
                super.onPostExecute(w);

//                humidity.setText(Double.toString(w.getHumidity()));
//                weatherCode.setText(Double.toString(w.getWeathercode()));
//                date.setText(w.getDate());
//                day.setText(Utils.getDayName(getApplicationContext(), new Date()));
//                weatherDesc.setText(w.getWeatherdetail());
//                weatherCondition.setText(w.getWeathercondition());
                weatherText.setText(Utils.toTitleCase(w.getWeatherdetail()));
                spinner.setVisibility(View.GONE);

                colors = Utils.getColorFromWeatherCode(w.getWeathercode());
                colorFilterIcon = new PorterDuffColorFilter(Color.parseColor(colors[1]), PorterDuff.Mode.SRC_ATOP);
                weatherIcon.setImageResource(Utils.getImageId(getApplicationContext(), weatherArray.get(w.getWeathercode())));
                weatherIcon.setColorFilter(colorFilterIcon);
                meetingText.setTextColor(Color.parseColor(colors[1]));
                getWindow().setStatusBarColor(Color.parseColor(colors[0]));
                bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colors[1])));
            }
            else {
                spinner.setVisibility(View.GONE);
            }
            weatherLoader.setVisibility(View.GONE);
            weatherCard.setVisibility(View.VISIBLE);
            SharedPreferences prefs = getSharedPreferences("whattowear", MODE_PRIVATE);

            String top = prefs.getString("today_top", "");
            String bottom = prefs.getString("today_bottom", "");
            spinner2 = (ProgressBar)findViewById(R.id.progressBar2);
            spinner2.setVisibility(View.VISIBLE);
            if(!top.isEmpty() && !bottom.isEmpty()){
                spinner2.setVisibility(View.GONE);
                clothesLoader.setVisibility(View.GONE);

            }
        }


    }

    public class FetchTopTask extends AsyncTask<String, Void, String[]>{
        @Override
        protected String[] doInBackground(String... strings) {
            if (strings == null) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String resultJSONStr;

            try {
                final String BASE_URL =
                        "http://developer.myntra.com/search/data/";
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(strings[0])
                        .build();
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                resultJSONStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
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

            final String OWM_DATA = "data";
            final String OWM_RESULTS = "results";
            final String OWM_PRODUCTS = "products";
            final String OWM_IMAGE = "search_image";
            final String OWM_BRANDS = "brands_filter_facet";
            try{
                JSONObject resultJson = new JSONObject(resultJSONStr);
                JSONObject resultData = resultJson.getJSONObject(OWM_DATA);
                JSONObject resultResults = resultData.getJSONObject(OWM_RESULTS);
                JSONArray productArray = resultResults.getJSONArray(OWM_PRODUCTS);
                String[] images = new String[5];
                String[] brands = new String[5];
                int length = Math.min(productArray.length(), 5);
                for (int i = 0 ; i < length; i++) {
                    JSONObject obj = productArray.getJSONObject(i);
                    images[i] = obj.getString(OWM_IMAGE);
                    brands[i] = obj.getString(OWM_BRANDS);
                    try {
                        mData.add(new ProductEntity(Utils.drawableFromUrl(obj.getString(OWM_IMAGE)),obj.getString(OWM_BRANDS),obj.getString(OWM_IMAGE),Utils.random()));
                    }
                    catch (IOException e){
                        return null;
                    }

                }
                return images;


            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                return null;

            }

        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);


            mAdapter = new CoverFlowAdapter(getApplicationContext());
            mAdapter.setData(mData);
            mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
            mCoverFlow.setAdapter(mAdapter);

            mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean inWardrobe = mData.get(position).inWardrobe;
                    if(inWardrobe){
                        Intent intent = new Intent(MainActivity.this, ProductDetail.class);
                        intent.putExtra("picture", mData.get(position).url);
                        intent.putExtra("type", "top");
                        intent.putExtra("brand", mData.get(position).titleResId);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, PDP.class);
                        intent.putExtra("picture", mData.get(position).url);
                        intent.putExtra("brand", mData.get(position).titleResId);
                        startActivity(intent);
                    }

                }
            });
            clothesLoaded++;
            if(clothesLoaded >=2){
                clothesLoader.setVisibility(View.GONE);
                spinner2.setVisibility(View.GONE);
                topCard.setVisibility(View.VISIBLE);
                bottomCard.setVisibility(View.VISIBLE);
            }


        }
    }
    public class FetchBottomTask extends AsyncTask<String, Void, String[]>{
        @Override
        protected String[] doInBackground(String... strings) {
            if (strings == null) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String resultJSONStr;

            try {
                final String BASE_URL =
                        "http://developer.myntra.com/search/data/";
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(strings[0])
                        .build();
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                resultJSONStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
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

            final String OWM_DATA = "data";
            final String OWM_RESULTS = "results";
            final String OWM_PRODUCTS = "products";
            final String OWM_IMAGE = "search_image";
            final String OWM_BRANDS = "brands_filter_facet";
            try{
                JSONObject resultJson = new JSONObject(resultJSONStr);
                JSONObject resultData = resultJson.getJSONObject(OWM_DATA);
                JSONObject resultResults = resultData.getJSONObject(OWM_RESULTS);
                JSONArray productArray = resultResults.getJSONArray(OWM_PRODUCTS);
                String[] images = new String[5];
                String[] brands = new String[5];
                int length = Math.min(productArray.length(), 5);
                for (int i = 0 ; i < length; i++) {
                    JSONObject obj = productArray.getJSONObject(i);
                    images[i] = obj.getString(OWM_IMAGE);
                    brands[i] = obj.getString(OWM_BRANDS);
                    try {
                        mDataBottom.add(new ProductEntity(Utils.drawableFromUrl(obj.getString(OWM_IMAGE)),obj.getString(OWM_BRANDS), obj.getString(OWM_IMAGE), Utils.random()));
                    }
                    catch (IOException e){
                        return null;
                    }

                }
                return images;


            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                return null;

            }

        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);


            mAdapter2 = new CoverFlowAdapter(getApplicationContext());
            mAdapter2.setData(mDataBottom);
            mCoverFlow2 = (FeatureCoverFlow) findViewById(R.id.coverflow1);
            mCoverFlow2.setAdapter(mAdapter2);

            mCoverFlow2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean inWardrobe = mDataBottom.get(position).inWardrobe;
                    if(inWardrobe){
                        Intent intent = new Intent(MainActivity.this, ProductDetail.class);
                        intent.putExtra("picture", mDataBottom.get(position).url);
                        intent.putExtra("type", "bottom");
                        intent.putExtra("brand", mDataBottom.get(position).titleResId);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, PDP.class);
                        intent.putExtra("picture", mDataBottom.get(position).url);
                        intent.putExtra("brand", mDataBottom.get(position).titleResId);
                        startActivity(intent);
                    }
                }
            });

            clothesLoaded++;
            if(clothesLoaded >=2){
                clothesLoader.setVisibility(View.GONE);
                spinner2.setVisibility(View.GONE);
                topCard.setVisibility(View.VISIBLE);
                bottomCard.setVisibility(View.VISIBLE);
            }

        }
    }


}
