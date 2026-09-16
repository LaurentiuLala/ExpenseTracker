package com.example.expensetracker.DAO

import androidx.lifecycle.LiveData
import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.example.expensetracker.DTO.Expense
import com.example.expensetracker.DTO.Type
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM EXPENSE")
    fun getAllExpenses(): Flow<List<Expense>>
    @Insert
    suspend fun addExpense(expense:Expense)

    @Query("Delete FROM Expense where id = :id")
    fun delete(id:Int)

    @Query("UPDATE Expense set title=:title,description=:description,createdAT=:createdAT, type=:type, price= :price WHERE id = :id" )
    fun update(id:Int,title:String,description:String,createdAT:Date,type: Type, price: Double)
}