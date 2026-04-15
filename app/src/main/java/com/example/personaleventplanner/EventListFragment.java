package com.example.personaleventplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaleventplanner.adapter.EventAdapter;
import com.example.personaleventplanner.data.Event;
import com.example.personaleventplanner.viewmodel.EventViewModel;

public class EventListFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private EventViewModel eventViewModel;
    private TextView tvEmptyMessage;

    public EventListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        tvEmptyMessage = view.findViewById(R.id.tvEmptyMessage);

        adapter = new EventAdapter(new EventAdapter.OnEventActionListener() {
            @Override
            public void onEditClick(Event event) {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", event.getId());
                Navigation.findNavController(view)
                        .navigate(R.id.action_eventListFragment_to_editEventFragment, bundle);
            }

            @Override
            public void onDeleteClick(Event event) {
                eventViewModel.delete(event);
                com.google.android.material.snackbar.Snackbar.make(view, "Event deleted successfully", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            adapter.setEventList(events);

            if (events == null || events.isEmpty()) {
                tvEmptyMessage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmptyMessage.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
}