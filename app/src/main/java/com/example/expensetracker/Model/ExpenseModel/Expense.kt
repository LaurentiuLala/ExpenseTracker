package com.example.expensetracker.Model.ExpenseModel

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.util.Date

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var title:String,
    var description: String,
    var createdAT: Date,
    var type: Type,
    var price: Double
)
enum class Type{
    FOOD,
    TRANSPORT,
    ENTERTAINMENT,
    OTHER
}