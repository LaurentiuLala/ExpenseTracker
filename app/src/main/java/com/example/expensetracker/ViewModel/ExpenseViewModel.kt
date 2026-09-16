package com.example.expensetracker.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.expensetracker.DAO.ExpenseDao
import com.example.expensetracker.DTO.Expense
import com.example.expensetracker.DTO.Type
import com.example.expensetracker.MainActivity
import kotlinx.coroutines.flow.Flow
import java.time.Instant
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