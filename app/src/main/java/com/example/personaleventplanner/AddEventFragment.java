package com.example.personaleventplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.personaleventplanner.data.Event;
import com.example.personaleventplanner.viewmodel.EventViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventFragment extends Fragment {

    private EditText etTitle, etLocation;
    private Spinner spCategory;
    private Button btnPickDateTime, btnSaveEvent;
    private TextView tvSelectedDateTime;

    private EventViewModel eventViewModel;
    private Calendar selectedDateTime;

    public AddEventFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitle = view.findViewById(R.id.etTitle);
        etLocation = view.findViewById(R.id.etLocation);
        spCategory = view.findViewById(R.id.spCategory);
        btnPickDateTime = view.findViewById(R.id.btnPickDateTime);
        btnSaveEvent = view.findViewById(R.id.btnSaveEvent);
        tvSelectedDateTime = view.findViewById(R.id.tvSelectedDateTime);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        String[] categories = {"Work", "Social", "Travel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        btnPickDateTime.setOnClickListener(v -> showDateTimePicker());
        btnSaveEvent.setOnClickListener(v -> saveEvent());
    }

    private void showDateTimePicker() {
        Calendar currentCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (datePicker, year, month, day) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            requireContext(),
                            (timePicker, hour, minute) -> {
                                selectedDateTime = Calendar.getInstance();
                                selectedDateTime.set(year, month, day, hour, minute, 0);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                                tvSelectedDateTime.setText(sdf.format(selectedDateTime.getTime()));
                            },
                            currentCalendar.get(Calendar.HOUR_OF_DAY),
                            currentCalendar.get(Calendar.MINUTE),
                            false
                    );
                    timePickerDialog.show();
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void saveEvent() {
        String title = etTitle.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDateTime == null) {
            Toast.makeText(requireContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDateTime.getTimeInMillis() < System.currentTimeMillis()) {
            Toast.makeText(requireContext(), "Past date is not allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event(title, category, location, selectedDateTime.getTimeInMillis());
        eventViewModel.insert(event);

        Toast.makeText(requireContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();

        etTitle.setText("");
        etLocation.setText("");
        tvSelectedDateTime.setText("No date selected");
        selectedDateTime = null;
    }
}