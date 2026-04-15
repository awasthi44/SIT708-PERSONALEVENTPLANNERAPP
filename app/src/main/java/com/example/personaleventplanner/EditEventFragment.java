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
import androidx.navigation.Navigation;

import com.example.personaleventplanner.data.Event;
import com.example.personaleventplanner.viewmodel.EventViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditEventFragment extends Fragment {

    private EditText etEditTitle, etEditLocation;
    private Spinner spEditCategory;
    private Button btnEditPickDateTime, btnUpdateEvent;
    private TextView tvEditSelectedDateTime;

    private EventViewModel eventViewModel;
    private Calendar selectedDateTime;
    private int eventId = -1;

    public EditEventFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEditTitle = view.findViewById(R.id.etEditTitle);
        etEditLocation = view.findViewById(R.id.etEditLocation);
        spEditCategory = view.findViewById(R.id.spEditCategory);
        btnEditPickDateTime = view.findViewById(R.id.btnEditPickDateTime);
        btnUpdateEvent = view.findViewById(R.id.btnUpdateEvent);
        tvEditSelectedDateTime = view.findViewById(R.id.tvEditSelectedDateTime);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        String[] categories = {"Work", "Social", "Travel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEditCategory.setAdapter(adapter);

        if (getArguments() != null) {
            eventId = getArguments().getInt("eventId", -1);
        }

        if (eventId != -1) {
            loadEventData(categories);
        }

        btnEditPickDateTime.setOnClickListener(v -> showDateTimePicker());
        btnUpdateEvent.setOnClickListener(v -> updateEvent(view));
    }

    private void loadEventData(String[] categories) {
        eventViewModel.getEventById(eventId, event -> {
            if (event != null && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    etEditTitle.setText(event.getTitle());
                    etEditLocation.setText(event.getLocation());

                    for (int i = 0; i < categories.length; i++) {
                        if (categories[i].equals(event.getCategory())) {
                            spEditCategory.setSelection(i);
                            break;
                        }
                    }

                    selectedDateTime = Calendar.getInstance();
                    selectedDateTime.setTimeInMillis(event.getEventDateTime());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                    tvEditSelectedDateTime.setText(sdf.format(selectedDateTime.getTime()));
                });
            }
        });
    }

    private void showDateTimePicker() {
        Calendar currentCalendar = selectedDateTime != null ? selectedDateTime : Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (datePicker, year, month, day) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            requireContext(),
                            (timePicker, hour, minute) -> {
                                selectedDateTime = Calendar.getInstance();
                                selectedDateTime.set(year, month, day, hour, minute, 0);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                                tvEditSelectedDateTime.setText(sdf.format(selectedDateTime.getTime()));
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

    private void updateEvent(View view) {
        String title = etEditTitle.getText().toString().trim();
        String location = etEditLocation.getText().toString().trim();
        String category = spEditCategory.getSelectedItem().toString();

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

        Event updatedEvent = new Event(title, category, location, selectedDateTime.getTimeInMillis());
        updatedEvent.setId(eventId);

        eventViewModel.update(updatedEvent);

        Toast.makeText(requireContext(), "Event updated successfully", Toast.LENGTH_SHORT).show();

        Navigation.findNavController(view).navigateUp();
    }
}