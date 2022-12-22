package com.istudio.distancetracker.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.istudio.distancetracker.compose.theme.MyApplicationTheme

class UserLogsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

            }
        }
    }
}