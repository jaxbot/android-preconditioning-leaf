package me.jaxbot.wear.preconditioningleaf;

/**
 * Created by jonathan on 3/29/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleArrayAdapter extends ArrayAdapter<ScheduledStart> {
    private final Context context;
    private final ArrayList<ScheduledStart> values;

    public ScheduleArrayAdapter(Context context, ArrayList<ScheduledStart> values) {
        super(context, R.layout.schedule_row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.schedule_row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values.get(position).name);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(values.get(position).time);

        TimePicker timePicker = (TimePicker) rowView.findViewById(R.id.timePicker);

        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

        return rowView;
    }
}
