package com.example.expensetracker.DAO.ExpenseDAO

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.example.expensetracker.Model.ExpenseModel.Expense
import com.example.expensetracker.Model.ExpenseModel.Type
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM EXPENSE")
    fun getAllExpenses(): Flow<List<Expense>>
    @Insert
    suspend fun addExpense(expense: Expense)

    @Query("Delete FROM Expense where id = :id")
    suspend fun delete(id:Int)

    @Query("UPDATE Expense set title=:title,description=:description,createdAT=:createdAT, type=:type, price= :price WHERE id = :id" )
     suspend fun update(id:Int, title:String, description:String, createdAT: Date, type: Type, price: Double)
}