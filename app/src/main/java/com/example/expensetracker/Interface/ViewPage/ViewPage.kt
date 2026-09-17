package com.example.expensetracker.Interface.ViewPage

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.expensetracker.Model.ExpenseModel.Expense
import com.example.expensetracker.Service.DialogEdit.AlertDialogEdit
import com.example.expensetracker.ViewModel.ExpenseViewModel
import kotlinx.coroutines.launch

@Composable
fun ViewPage(viewmodel: ExpenseViewModel, modifier: Modifier = Modifier) {
    var expenseToEdit by remember { mutableStateOf<Expense?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val expenses by viewmodel.expenseList
        .collectAsState(initial = emptyList())
    LazyColumn(modifier= modifier) {
        expenses.forEach{expense -> item {Text(text = "Title: ${expense.title} \nDescription: ${expense.description} \nRON: ${expense.price} \nDate:${expense.createdAT}: ")
            Row() {
                IconButton(onClick = {
                    expenseToEdit = expense
                    showDialog = true}) {
            Icon(imageVector = Icons.Default.Build, contentDescription = "")}
                IconButton(onClick = {scope.launch{viewmodel.deleteExpense(expense.id)}}) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            }
        }

        }
    }
    if (showDialog && expenseToEdit != null) {
        AlertDialogEdit(
            viewmodel = viewmodel,
            expensetoedit = expenseToEdit!!,
            dialogTitle = "Edit Expense",
            icon = Icons.Default.Build,
            onDismissRequest = {
                showDialog = false
            },
            onConfirmation = {
                showDialog = false
            }
        )
    }

}