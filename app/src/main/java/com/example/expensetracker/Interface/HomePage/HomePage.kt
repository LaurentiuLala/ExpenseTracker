package com.example.expensetracker.Interface.HomePage

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import com.example.expensetracker.DataStore.BudgetDataStore.getBudget
import com.example.expensetracker.Pages.AddPage
import com.example.expensetracker.Pages.ViewListPage
import com.example.expensetracker.Service.DialogEdit.AlertDialogEdit
import com.example.expensetracker.Service.DialogEdit.DialogBudget
import com.example.expensetracker.Service.PriceCalculation.PriceCalculation
import com.example.expensetracker.ViewModel.ExpenseViewModel
import kotlin.math.abs


@Composable
fun HomePage(modifier: Modifier = Modifier,viewmodel: ExpenseViewModel) {
    val context = LocalContext.current
    val budget by getBudget(context).collectAsState(initial = 0.0)
    val expenses by viewmodel.expenseList
        .collectAsState(initial = emptyList())
    val calculator = PriceCalculation()
    val total = calculator.calculateTotal(expenses)
    val remaining = budget - total
    var showDialog by remember { mutableStateOf(false) }



    Column( modifier= modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Row() {Text(text = "Your budget is: $budget" )
            IconButton(onClick = {
                showDialog = true}) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "")
            }
        }
        Text(
            text = "Your expenses are: $total",
            modifier = modifier
        )
        if(remaining > 0){
        Text(text = "Remaining amount is: $remaining",
            modifier = modifier
        )}
        else if(remaining == 0.0){
            Text(text = "No amount left: ")
        }
        else{
            Text(text = "Your debt is ${abs(remaining)}")
        }
        Divider()
        Text(
            text = "Food: ${calculator.calculateFood(expenses)}",
            modifier = modifier
        )
        Text(
            text = "Transport: ${calculator.calculateTransport(expenses)}",
            modifier = modifier
        )
        Text(
            text = "Entertainment: ${calculator.calculateEntertainment(expenses)}",
            modifier = modifier
        )
        Text(
            text = "Other: ${calculator.calculateOther(expenses)}",
            modifier = modifier
        )
        Row(modifier=modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
            ){
        Button(onClick ={val intent = Intent(context, AddPage::class.java)
            context.startActivity(intent)},modifier=modifier, ) {
            Icon(imageVector= Icons.Default.Add,contentDescription= "Add")
        }
        Button(onClick = {val i = Intent(context, ViewListPage::class.java)
            context.startActivity(i)},modifier = modifier) {Text(text = "View all")  }
    }}

    if (showDialog) {
        DialogBudget(
            context = context,
            budget = budget,
            dialogTitle = "Edit Budget",
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
