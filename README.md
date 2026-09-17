# Expense Tracker

Expense Tracker is an Android application developed in Kotlin using Jetpack Compose. The application allows users to manage their expenses, organize them by category, calculate spending totals, and manage a personal budget.

## Features

* Add new expenses
* Edit existing expenses
* Delete expenses
* Categorize expenses:

  * Food
  * Transport
  * Entertainment
  * Other
* Calculate total expenses
* Calculate expenses by category
* Set and edit a personal budget
* Display the remaining budget
* Display the amount exceeded when expenses are higher than the budget
* Store expense data locally
* Automatically update the UI when data changes

## Technologies

* Kotlin
* Android
* Jetpack Compose
* Room Database
* DataStore Preferences
* Kotlin Coroutines
* Kotlin Flow
* ViewModel
* KSP

## Architecture

The application follows a separation of responsibilities between the UI, ViewModel, DAO, and database.

```text
UI / Composable
      ↓
ViewModel
      ↓
DAO
      ↓
Room Database
```

The budget is stored separately using DataStore:

```text
HomePage
    ↓
DataStore
    ↓
DataStore Preferences
```

## UI Layer

The UI is implemented using Jetpack Compose.

The UI is responsible for:

* Displaying information
* Reading user input
* Handling button interactions
* Displaying dialogs
* Navigating between screens
* Observing application state

The UI does not directly communicate with the database. Database operations are handled through the ViewModel and DAO.

## ViewModel

The `ExpenseViewModel` acts as an intermediary between the UI and the database.

Its responsibilities include:

* Adding expenses
* Updating expenses
* Deleting expenses
* Exposing the expense list to the UI
* Sending database operations to the DAO

The ViewModel receives the DAO through its constructor instead of creating the database connection itself. This keeps the ViewModel independent from the database implementation.

## DAO

The `ExpenseDao` is responsible for communicating with the Room database.

It contains operations for:

* Retrieving all expenses
* Adding expenses
* Updating expenses
* Deleting expenses

The DAO exposes the expense list as:

```kotlin
Flow<List<Expense>>
```

Using `Flow` allows the application to react automatically when the database changes.

## Room Database

Room is used to store structured expense data locally.

The `Expense` entity contains information such as:

* ID
* Title
* Description
* Creation date
* Expense type
* Price

Room is suitable for this data because expenses represent structured information that requires operations such as inserting, updating, deleting, and querying records.

## DataStore

DataStore Preferences is used to store the user's budget.

The budget is a simple value, so using Room for it would add unnecessary database structure.

DataStore provides a simple key-value storage mechanism:

```text
"budget" → Double
```

The budget is exposed as a `Flow`, allowing the UI to automatically update when the stored value changes.

## Coroutines

Kotlin Coroutines are used for database and DataStore operations.

Operations such as inserting, updating, and deleting expenses are `suspend` functions.

The operations are executed inside a coroutine using `launch`, allowing the application to perform these operations without blocking the UI.

## Reactive UI

The application uses `Flow` together with Compose state to keep the UI synchronized with the database.

The process works as follows:

```text
Room Database
      ↓
DAO
      ↓
Flow<List<Expense>>
      ↓
ViewModel
      ↓
collectAsState()
      ↓
Compose UI
```

When an expense is added, edited, or deleted, Room emits a new list through the `Flow`.

Compose receives the updated state and recomposes the affected UI automatically.

For example, the total expense amount does not need to be manually updated after every database operation. The total is recalculated from the updated expense list.

## Expense Calculations

The `PriceCalculation` class is responsible for calculating expense values.

It provides methods for:

* Total expenses
* Food expenses
* Transport expenses
* Entertainment expenses
* Other expenses

The calculations are performed on the list of expenses received from the ViewModel.

This keeps calculation logic separate from the UI.

## Budget Calculation

The application compares the user's budget with the total expenses.

```text
Budget - Total Expenses = Remaining Amount
```

If the result is:

* Positive → the user still has money available
* Zero → the entire budget has been used
* Negative → the expenses exceeded the budget

The application displays the exceeded amount as a positive value to make it easier to understand.

## Data Validation and Responsibility

Some application rules are handled outside the UI.

For example, when adding an expense, the ViewModel creates the `Expense` object that will be sent to the DAO.

This provides better control over the data that reaches the database and prevents the UI from being responsible for all application logic.

The UI is mainly responsible for collecting user input, while the ViewModel handles application logic and communication with the DAO.

This separation makes the application easier to maintain and reduces the amount of logic inside the UI.

## Project Structure

```text
com.example.expensetracker
│
├── DAO
│   └── ExpenseDao
│
├── Database
│   ├── ExpenseDATABASE
│   └── DatabaseProvider
│
├── DataStore
│   └── BudgetDataStore
│
├── Model
│   ├── Expense
│   └── Type
│
├── ViewModel
│   ├── ExpenseViewModel
│   └── ExpenseViewModelFactory
│
├── Service
│   ├── PriceCalculation
│   └── DialogEdit
│
└── Interface
    └── HomePage
```

## Main Principles

The project follows several important software development principles:

* Separation of concerns
* Clear responsibilities for each component
* Reactive UI updates
* Local data persistence
* Reusable business logic
* Database access through DAO
* Communication between UI and database through ViewModel
* Using the appropriate storage solution for different types of data

## Summary

Expense Tracker combines Jetpack Compose, Room, DataStore, ViewModel, Coroutines, and Flow to create a local expense management application.

Room is used for structured expense data, while DataStore is used for the simple budget preference.

The application uses a reactive architecture where changes in stored data are automatically reflected in the user interface.
