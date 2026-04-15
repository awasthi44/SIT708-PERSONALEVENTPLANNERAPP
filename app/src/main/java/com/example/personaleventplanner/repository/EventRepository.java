package com.example.personaleventplanner.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.personaleventplanner.dao.EventDao;
import com.example.personaleventplanner.data.Event;
import com.example.personaleventplanner.database.EventDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {

    private final EventDao eventDao;
    private final LiveData<List<Event>> allEvents;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public void insert(Event event) {
        executorService.execute(() -> eventDao.insert(event));
    }

    public void update(Event event) {
        executorService.execute(() -> eventDao.update(event));
    }

    public void delete(Event event) {
        executorService.execute(() -> eventDao.delete(event));
    }

    public void getEventById(int id, RepositoryCallback callback) {
        executorService.execute(() -> {
            Event event = eventDao.getEventById(id);
            callback.onComplete(event);
        });
    }

    public interface RepositoryCallback {
        void onComplete(Event event);
    }
}
