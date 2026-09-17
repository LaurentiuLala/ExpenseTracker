package com.example.expensetracker.Service.DialogEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.expensetracker.Model.ExpenseModel.Expense
import com.example.expensetracker.Model.ExpenseModel.Type
import com.example.expensetracker.ViewModel.ExpenseViewModel
import kotlinx.coroutines.launch

@Composable
fun AlertDialogEdit(
    viewmodel: ExpenseViewModel,
    expensetoedit: Expense,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    icon: ImageVector,
) {
    val radioOptions = listOf("FOOD", "ENTERTAINMENT", "TRANSPORT", "OTHER")
    val (selectedOption, onOptionselected) = remember { mutableStateOf(expensetoedit.type.name) }
    var newTitle by remember { mutableStateOf(expensetoedit.title) }
    var newDescription by remember { mutableStateOf(expensetoedit.description) }
    var newPrice by remember { mutableStateOf(expensetoedit.price.toString()) }
    val scope = rememberCoroutineScope()
    AlertDialog(

        modifier = Modifier.fillMaxWidth(),

        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
            TextField(value= newTitle, onValueChange = {newTitle=it})
            TextField(value= newPrice, onValueChange = {newPrice = it})
            TextField(value= newDescription, onValueChange = {newDescription =it})
            Row(
                modifier = Modifier
                    .selectableGroup()
                    .fillMaxWidth()
            ) {
                radioOptions.forEach { text ->
                    Column(
                        modifier = Modifier
                            .selectable(
                                selected = text == selectedOption,
                                onClick = { onOptionselected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 2.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = text == selectedOption,
                            onClick = null
                        )

                        Text(text = text)
                    }
                }
            }}
        },

        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    scope.launch {
                    viewmodel.update(expensetoedit.id,newTitle,newDescription,expensetoedit.createdAT, Type.valueOf(selectedOption), newPrice.toDouble())}
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}