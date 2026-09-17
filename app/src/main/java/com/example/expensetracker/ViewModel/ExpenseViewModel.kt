package com.example.expensetracker.ViewModel

import androidx.lifecycle.ViewModel
import com.example.expensetracker.DAO.ExpenseDAO.ExpenseDao
import com.example.expensetracker.Model.ExpenseModel.Expense
import com.example.expensetracker.Model.ExpenseModel.Type
import kotlinx.coroutines.flow.Flow
import java.util.Date


class ExpenseViewModel(private val expenseDao: ExpenseDao) : ViewModel() {


    val expenseList: Flow<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun addExpense(expense: Expense){
        expenseDao.addExpense(Expense(title=expense.title, description = expense.description, createdAT = Date(),type= expense.type,price = expense.price))
    }

    suspend fun deleteExpense(id: Int){
        expenseDao.delete(id)
    }
    suspend fun update(id:Int,title:String,description:String,createdAT:Date,type: Type, price: Double){
        expenseDao.update(id=id , title=title,description=description,createdAT = createdAT,type = type, price = price)
    }

}