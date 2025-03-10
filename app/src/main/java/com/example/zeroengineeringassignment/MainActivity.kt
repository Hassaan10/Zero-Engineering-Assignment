package com.example.zeroengineeringassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.zeroengineeringassignment.ui.navigation.MainNavigation
import com.example.zeroengineeringassignment.ui.theme.ZeroEngineeringAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZeroEngineeringAssignmentTheme {
                MainNavigation()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZeroEngineeringAssignmentTheme {
        MainNavigation()
    }
}