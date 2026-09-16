package com.example.expensetracker.Database
import androidx.room3.ColumnTypeConverters
import com.example.expensetracker.DTO.Expense
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.expensetracker.Converters.Converters
import com.example.expensetracker.DAO.ExpenseDao


@Database(entities = [Expense::class], version = 1)
@ColumnTypeConverters(Converters::class)
abstract class ExpenseDATABASE : RoomDatabase(){

    companion object{
        const val NAME = "Expense_DB"
    }

    abstract fun getExpenseDao(): ExpenseDao
}