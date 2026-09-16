package com.example.expensetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetracker.DAO.ExpenseDao
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class ViewList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val expenseDao = MainActivity.database.getExpenseDao()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting3(
                        modifier = Modifier.padding(innerPadding),
                        dao = expenseDao
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting3(dao: ExpenseDao, modifier: Modifier = Modifier) {
    val expenses by dao.getAllExpenses()
        .collectAsState(initial = emptyList())
    LazyColumn(modifier= modifier) {
        expenses.forEach{expense -> item {Text(text = "Title: ${expense.title} \nDescription: ${expense.description} \nRON: ${expense.price} \nDate:${expense.createdAT}: ")}

        }
    }

}