package com.myntra.whattowear;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by 8302 on 4/13/2015.
 */
public class FetchCalenderTask extends AsyncTask<Location, Void, String>  {
    private Context mContext;
    public final String LOG_TAG = MainActivity.class.getSimpleName();
    public FetchCalenderTask (Context context){
        mContext = context;
    }
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Events._ID,                           // 0
            CalendarContract.Events.DURATION,                  // 1
            CalendarContract.Events.DTSTART,                  // 1
            CalendarContract.Events.DTEND,                  // 1
            CalendarContract.Events.EVENT_LOCATION                  // 1
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_DURATION_INDEX = 1;
    private static final int PROJECTION_START_INDEX = 2;
    private static final int PROJECTION_END_INDEX = 3;
    private static final int PROJECTION_LOCATION_INDEX = 4;

    @Override
    protected String doInBackground(Location... locations) {

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
            String eventDuration = null;
            String location = null;
            long start = 0;
            long end = 0;

            // Get the field values
            eventID = cur.getLong(PROJECTION_ID_INDEX);
            eventDuration = cur.getString(PROJECTION_DURATION_INDEX);
            start = cur.getLong(PROJECTION_START_INDEX);
            end = cur.getLong(PROJECTION_END_INDEX);
            location = cur.getString(PROJECTION_LOCATION_INDEX);

            //TODO: Do something with the values...
            Log.e(LOG_TAG, "Event ID : "+eventID+" | duration : "+eventDuration);

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


        return calenderData;
    }

    @Override
    protected void onPostExecute(String calenderData) {
        super.onPostExecute(calenderData);
        MainActivity.calenderData.setText(calenderData);
    }
}
