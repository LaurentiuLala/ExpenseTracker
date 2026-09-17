package com.example.expensetracker.Pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.Database.DatabaseProvider
import com.example.expensetracker.Interface.ViewPage.ViewPage
import com.example.expensetracker.ViewModel.ExpenseViewModel
import com.example.expensetracker.ViewModel.ExpenseViewModelFactory
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class ViewListPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val dao = DatabaseProvider.database.getExpenseDao()

                val factory = ExpenseViewModelFactory(dao)

                val viewmodel: ExpenseViewModel = viewModel(
                    factory = factory
                )
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ViewPage(
                        modifier = Modifier.padding(innerPadding),
                        viewmodel = viewmodel
                    )
                }
            }
        }
    }
}