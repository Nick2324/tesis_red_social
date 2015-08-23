package sportsallaround.utils.gui;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by nicolas on 8/08/15.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    OnTimePickedListener callBack;

    public interface OnTimePickedListener{

        public void onTimePicked(String time);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callBack = (OnTimePickedListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTimePickedListener.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minutes, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        callBack.onTimePicked(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
    }

}
