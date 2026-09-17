package com.example.expensetracker.Pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room3.Room
import com.example.expensetracker.Database.DatabaseProvider
import com.example.expensetracker.Database.ExpenseDATABASE
import com.example.expensetracker.Interface.HomePage.HomePage
import com.example.expensetracker.ViewModel.ExpenseViewModel
import com.example.expensetracker.ViewModel.ExpenseViewModelFactory
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class HomePage : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.database = Room.databaseBuilder(applicationContext, ExpenseDATABASE::class.java, ExpenseDATABASE.NAME).build()
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val dao = DatabaseProvider.database.getExpenseDao()

                val factory = ExpenseViewModelFactory(dao)

                val viewmodel: ExpenseViewModel = viewModel(
                    factory = factory
                )
                HomePage(modifier = Modifier,viewmodel);

            }
            }
        }
    }