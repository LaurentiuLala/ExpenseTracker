package com.example.expensetracker.Service.PriceCalculation


import com.example.expensetracker.Model.ExpenseModel.Expense
import com.example.expensetracker.Model.ExpenseModel.Type

class PriceCalculation {
    fun calculateFood(expense:List<Expense>): Double{
        return expense
            .filter{it.type == Type.FOOD}
            .sumOf { it.price }
    }
    fun calculateTransport(expense:List<Expense>): Double{
        return expense
            .filter{it.type == Type.TRANSPORT}
            .sumOf { it.price }
    }
    fun calculateEntertainment(expense:List<Expense>): Double{
        return expense
            .filter{it.type == Type.ENTERTAINMENT}
            .sumOf { it.price }
    }
    fun calculateOther(expense:List<Expense>): Double{
        return expense
            .filter{it.type == Type.OTHER}
            .sumOf { it.price }
    }
    fun calculateTotal(expense:List<Expense>): Double{
        return expense .sumOf { it.price }
    }
}