package com.example.expensetracker

import android.R
import android.R.attr.name
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.room3.Room
import com.example.expensetracker.Database.ExpenseDATABASE
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    companion object{
        lateinit var database: ExpenseDATABASE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(applicationContext, ExpenseDATABASE::class.java, ExpenseDATABASE.NAME).build()
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
            Greeting(1200);

                }
            }
        }
    }


@Composable

fun Greeting(buget: Int,modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var food by remember { mutableStateOf(0) }
    var transport by remember { mutableStateOf(0) }
    var entertainment by remember { mutableStateOf(0) }
    var other by remember { mutableStateOf(0) }
    var total by remember { mutableStateOf(0) }
    Column( modifier= modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = "Your buget is: $buget",
            modifier = modifier
        )
        Divider()
        Text(
            text = "Your expenses are: $total",
            modifier = modifier
        )
        Divider()
        Text(
            text = "Food: $food",
            modifier = modifier
        )
        Text(
            text = "Transport: $transport",
            modifier = modifier
        )
        Text(
            text = "Entertainment: $entertainment",
            modifier = modifier
        )
        Text(
            text = "Other: $other",
            modifier = modifier
        )
        Button(onClick ={val intent = Intent(context, MainActivity2::class.java)
            context.startActivity(intent)},modifier=modifier, ) {
            Icon(imageVector= Icons.Default.Add,contentDescription= "Add")
        }
        Button(onClick = {val i = Intent(context, ViewList::class.java)
            context.startActivity(i)},modifier = modifier) {Text(text = "View all")  }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseTrackerTheme {
        Greeting(1200)
    }
}