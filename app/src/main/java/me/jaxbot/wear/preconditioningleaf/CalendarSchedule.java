package me.jaxbot.wear.preconditioningleaf;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarSchedule {
    // Majority of the calendar code is adapted from Android documentation:
    // http://developer.android.com/guide/topics/providers/calendar-provider.html

    public static final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,  // 0
            CalendarContract.Instances.BEGIN,     // 1
            CalendarContract.Instances.TITLE,     // 2
            CalendarContract.Instances.END,       // 3
            CalendarContract.Instances.ALL_DAY,   // 4
            CalendarContract.Events.EVENT_LOCATION // 5
    };

    // The indices for the projection array above.
    private static final int PROJECTION_BEGIN_INDEX = 1;
    private static final int PROJECTION_END_INDEX = 3;
    private static final int PROJECTION_TITLE_INDEX = 2;
    private static final int PROJECTION_ALL_DAY = 4;

    public static ArrayList<Long> getStartEndTimes(Context ctx) {
        ArrayList<Long> timesList = new ArrayList<Long>();

        // Get current day schedule, 12:00 am to 11:59 pm
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTimeInMillis(System.currentTimeMillis() - 86400 * 2 * 1000);
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.setTimeInMillis(System.currentTimeMillis() - 86400 * 2 * 1000);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        long endMillis = endTime.getTimeInMillis();

        Cursor cur;
        ContentResolver cr = ctx.getContentResolver();

        // The ID of the recurring event whose instances you are searching
        // for in the Instances table
        String selection = CalendarContract.Instances.EVENT_ID + " != ?";
        String[] selectionArgs = new String[] {"-1"};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        cur =  cr.query(builder.build(),
                INSTANCE_PROJECTION,
                selection,
                selectionArgs,
                CalendarContract.Events.DTSTART);

        long end = 0;

        long previousEnd = 0;
        long GAP = 1860 * 1000; // 31 minutes

        while (cur.moveToNext()) {
            long beginVal;
            long endVal;
            long allDay;

            // Get the field values
            beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
            endVal = cur.getLong(PROJECTION_END_INDEX);
            allDay = cur.getLong(PROJECTION_ALL_DAY);

            // We can't figure out when to start AC on an all-day event
            if (allDay == 1)
                continue;

            if (beginVal > previousEnd + GAP) {
                timesList.add(beginVal - GAP);
                if (previousEnd != 0)
                    timesList.add(previousEnd);
            }

            previousEnd = endVal;

            // Get the final end time, since no event will be there to follow
            if (endVal > end)
                end = endVal;

            System.out.println(cur.getString(PROJECTION_TITLE_INDEX) + " start " + longToDate(beginVal) + " end " + longToDate(endVal) + " allday: " + cur.getInt(PROJECTION_ALL_DAY));
        }
        System.out.println("End is: " + longToDate(end));
        timesList.add(end);

        return timesList;
    }

    public static String longToDate(long lon)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lon);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:m:s a");
        return formatter.format(calendar.getTime());
    }
}