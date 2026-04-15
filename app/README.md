# 📅 Personal Event Planner App

## 📌 Overview
The **Personal Event Planner App** is an Android application developed using **Java** that allows users to manage their events, appointments, and trips efficiently. The app provides a simple and user-friendly interface where users can create, view, update, and delete events.

This application was developed as part of **Pass Task 4.1** and demonstrates the use of modern Android development practices such as **Room Database**, **Fragments**, and **Navigation Component**.

---

## 🚀 Features

### ✅ Core Functionality (CRUD)
- ➕ Add new events (Title, Category, Location, Date & Time)
- 📋 View all upcoming events sorted by date
- ✏️ Edit existing events
- 🗑️ Delete events with confirmation message

### 💾 Data Persistence
- Uses **Room Database** for local storage
- Data is saved even after closing or restarting the app

### 🧭 Navigation
- Implemented using **Jetpack Navigation Component**
- Uses **Fragments** instead of multiple activities
- Includes **Bottom Navigation Bar** for smooth switching

### ⚠️ Validation & Error Handling
- Title field cannot be empty
- Date and time must be selected
- Past dates are not allowed for new events
- User feedback provided using **Toast and Snackbar**

---

## 🛠️ Technologies Used

- Java
- XML (UI Design)
- Room Database (Local Storage)
- RecyclerView (List Display)
- ViewModel & LiveData (Architecture)
- Jetpack Navigation Component
- Material Design Components

---

## 📱 Screens in the App

1. **Event List Screen**
    - Displays all upcoming events
    - Sorted by date automatically
    - Includes Edit and Delete buttons

2. **Add Event Screen**
    - Allows users to enter event details
    - Includes date and time picker
    - Performs input validation before saving

3. **Edit Event Screen**
    - Pre-fills existing event details
    - Allows updating information
    - Saves changes to database

---

## 📂 Project Structure
com.example.personaleventplanner
│
├── MainActivity.java
├── AddEventFragment.java
├── EventListFragment.java
├── EditEventFragment.java
│
├── data
│ └── Event.java
│
├── dao
│ └── EventDao.java
│
├── database
│ └── EventDatabase.java
│
├── repository
│ └── EventRepository.java
│
├── viewmodel
│ └── EventViewModel.java
│
└── adapter
└── EventAdapter.java

---

## ▶️ How to Run the Project

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run the app on:
         Android Emulator OR
         Physical Android Device

🧪 Testing Checklist
✔ Add event successfully
✔ Validation for empty title
✔ Prevent past date selection
✔ Events display in sorted order
✔ Edit updates event correctly
✔ Delete removes event
✔ Data persists after app restart


🎥 Demonstration

👉 [Add your YouTube video link here]

🔗 GitHub Repository

👉 [Add your GitHub repo link here]

🤖 LLM Usage Declaration

This project was developed with assistance from ChatGPT. 
The AI was used to guide the implementation of:
     Room Database setup
     Fragment-based navigation
     RecyclerView and Adapter logic
     Validation and error handling

All generated code was reviewed, tested, and modified by the developer before final submission.

📌 Author

SHOVA AWASTHI
Student ID: 224887189
Deakin University

⭐ Conclusion

This project demonstrates a complete Android application using modern development practices, including structured architecture, persistent storage, and user-friendly design.