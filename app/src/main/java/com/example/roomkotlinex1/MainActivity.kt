package com.example.roomkotlinex1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.roomkotlinex1.ui.theme.RoomKotlinEx1Theme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomKotlinEx1Theme {
                Scaffold(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {  innerPadding ->
                    Greeting(
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Greeting() {

    val context = LocalContext.current

    val db = Room.databaseBuilder(
        context,
        UserDatabase::class.java, "users-db1"
    ).allowMainThreadQueries().build()

    var userlistKq by remember {
        mutableStateOf(db.userDao().getAll().toMutableStateList())
    }

    Column (Modifier.fillMaxWidth()){
        Text(
            text = "Quan ly Sinh vien",
            style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = {
            val user = User(firstName = "Duy", lastName = "Le")
            db.userDao().insertAll(user)

            userlistKq = db.userDao().getAll().toMutableStateList()
        }) {
            Text(text = "Thêm SV")
        }

        LazyColumn {

            items(userlistKq) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Text(modifier = Modifier.weight(1f), text = it.uid.toString())
                    Text(modifier = Modifier.weight(1f), text = it.firstName.toString())
                    Text(modifier = Modifier.weight(1f), text = it.lastName.toString())
                }
                Divider()
            }
        }


    }
}
