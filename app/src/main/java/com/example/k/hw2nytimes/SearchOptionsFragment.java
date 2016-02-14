package com.example.k.hw2nytimes;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Switch;

/**
 * Created by Komal on 2/7/2016.
 */
public class SearchOptionsFragment extends DialogFragment {
    private static SearchOptionsFragment ourInstance;
    private SearchOptions options;


    public SearchOptionsFragment() {

        //Deliberately Empty constructor
    }

    public static SearchOptionsFragment getInstance(SearchOptions options) {
        if (ourInstance == null) {
            ourInstance = new SearchOptionsFragment();
            ourInstance.options = options;
        }

        return ourInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_options, container);
        final RadioButton arts = (RadioButton) view.findViewById(R.id.radioButton);
        final RadioButton fashion = (RadioButton) view.findViewById(R.id.radioButton2);
        final RadioButton sports = (RadioButton) view.findViewById(R.id.radioButton3);
        final Button saveButton = (Button) view.findViewById(R.id.Savebutton);
        final DatePicker datep = (DatePicker) view.findViewById(R.id.datePicker);
        final Switch oldestFirst = (Switch) view.findViewById(R.id.switch1);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ourInstance.options.setOldestFirst(oldestFirst.isChecked());
                ourInstance.options.setCategory_sports(sports.isChecked());
                ourInstance.options.setCategory_art(arts.isChecked());
                ourInstance.options.setCategory_fashion(fashion.isChecked());
                ourInstance.options.setBeginDate(datep.getMinDate());
            }
        });


        return view;
    }


}
