package com.example.expensetracker.ViewModel

import androidx.lifecycle.ViewModel
import com.example.expensetracker.Model.Expense
import com.example.expensetracker.Model.Type
import com.example.expensetracker.MainActivity
import kotlinx.coroutines.flow.Flow
import java.util.Date


class ExpenseViewModel() : ViewModel() {

    val expenseDao = MainActivity.database.getExpenseDao()

    val expenseList: Flow<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun addExpense(title:String,description:String,type: Type,price: Double){
        expenseDao.addExpense(Expense(title=title, description = description, createdAT = Date(),type= type,price = price))
    }

    fun deleteExpense(id: Int){
        expenseDao.delete(id)
    }

}