package com.example.nireburuaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nireburuaapp.ui.theme.NireburuaAppTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NireburuaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Igotz",
                        surname = "Idigoras",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Message(val name: String, val surname: String, val modifier: Modifier, )

@Composable
fun Greeting(name: String, surname:String, modifier: Modifier = Modifier) {
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(100.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Perfil de contacto",
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp)
                .clip(CircleShape),

            contentScale = ContentScale.Crop

        )
        Text(
            text = "$name $surname",
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp )


        )
        Spacer(modifier = Modifier.width(8.dp))

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NireburuaAppTheme {
        Greeting(
            name = "Igotz" ,
            surname = "Idigoras"
        )
    }
}