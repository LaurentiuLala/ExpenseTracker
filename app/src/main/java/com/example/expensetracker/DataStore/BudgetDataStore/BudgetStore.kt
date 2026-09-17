package com.example.expensetracker.DataStore.BudgetDataStore

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.budgetDataStore by preferencesDataStore(name = "budget_preferences")

val BUDGET_KEY = doublePreferencesKey("budget")

fun getBudget(context: Context): Flow<Double> {
    return context.budgetDataStore.data.map { preferences ->
        preferences[BUDGET_KEY] ?: 0.0
    }
}

suspend fun saveBudget(context: Context, budget: Double){
    context.budgetDataStore.edit { preferences ->
        preferences[BUDGET_KEY] = budget
}}