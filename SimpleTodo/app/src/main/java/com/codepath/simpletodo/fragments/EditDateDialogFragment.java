package com.codepath.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.codepath.simpletodo.R;

/**
 * Created by akueisara on 9/18/2016.
 */
public class EditDateDialogFragment extends DialogFragment {

    public interface EditDialogListener {
        void onFinishEditDialog(String date);
    }
    private Button okBtn;

    public EditDateDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditDateDialogFragment newInstance(String date) {
        EditDateDialogFragment frag = new EditDateDialogFragment();
        Bundle args = new Bundle();
        args.putString("dueDate", date);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_date_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle("Due Date");

        // Fetch arguments from bundle and set date
        String dateString = getArguments().getString("dueDate", "2016/09/18");
        String[] splitedDate = dateString.split("/");

        int year = Integer.parseInt(splitedDate[0]);
        int month = Integer.parseInt(splitedDate[1])-1;
        int day  = Integer.parseInt(splitedDate[2]);
        DatePicker datePicker = (DatePicker) getView().findViewById(R.id.date_picker);
        datePicker.updateDate(year, month, day);
        okBtn = (Button) view.findViewById(R.id.button_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    private void dismissDialog(){
        EditDialogListener listener = (EditDialogListener) getActivity();
        DatePicker datePicker = (DatePicker) getView().findViewById(R.id.date_picker);
        int month = datePicker.getMonth()+1;
        int day = datePicker.getDayOfMonth();
        String formattedMonth;
        String formattedDay;
        if(day < 10)
        {
            formattedDay = "0" + day;
        }
        else {
            formattedDay = String.valueOf(day);
        }

        if(month < 10)
        {
            formattedMonth = "0" + month;
        }
        else {
            formattedMonth = String.valueOf(month);
        }
        String dateString = datePicker.getYear() + "/" + formattedMonth + "/" + formattedDay;
        listener.onFinishEditDialog(dateString);
        dismiss();
    }
}
