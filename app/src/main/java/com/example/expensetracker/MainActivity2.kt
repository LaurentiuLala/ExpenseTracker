package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.expensetracker.DAO.ExpenseDao
import com.example.expensetracker.Model.Expense
import com.example.expensetracker.Model.Type
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val expenseDao = MainActivity.database.getExpenseDao()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        modifier = Modifier.padding(innerPadding),
                        expenseDao = expenseDao
                    )
                }
            }
        }
            }
        }



@Composable
fun Greeting2(modifier: Modifier = Modifier, expenseDao: ExpenseDao) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    val radioOptions = listOf("FOOD", "ENTERTAINMENT", "TRANSPORT", "OTHER")
    val (selectedOption, onOptionselected) = remember { mutableStateOf(radioOptions[0]) }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Title: ", modifier)
        TextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
        )
        Text(text = "Description: ", modifier)
        TextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier
        )
        Text(text = "Price: ", modifier)
        TextField(
            value = price,
            onValueChange = { price = it }, modifier = Modifier
        )

        Row(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth()
        ) {
            radioOptions.forEach { text ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = text == selectedOption,
                            onClick = { onOptionselected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = text == selectedOption,
                        onClick = null
                    )

                    Text(text = text)
                }
            }
        }

            Button(onClick = {val expense = Expense(title=title, description = description,price=price.toDouble(), type = Type.valueOf(selectedOption), createdAT = Date())
            scope.launch{expenseDao.addExpense(expense)}
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)},modifier = modifier.padding(12.dp))
            {
                Text(text = "save",modifier)
            }
            }


    }


