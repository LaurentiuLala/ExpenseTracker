package com.example.expensetracker.Converters

import com.example.expensetracker.Model.ExpenseModel.Type
import androidx.room3.ColumnTypeConverter
import java.util.Date

class Converters {

    @ColumnTypeConverter
    fun fromType(type: Type): String {
        return type.name
    }

    @ColumnTypeConverter
    fun toType(value: String): Type {
        return Type.valueOf(value)
    }

    @ColumnTypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @ColumnTypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }
}