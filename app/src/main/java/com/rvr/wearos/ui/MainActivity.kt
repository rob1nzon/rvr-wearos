package com.rvr.wearos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.rvr.wearos.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LockScreen()
            }
        }
    }
}

@Composable
fun LockScreen() {
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("Готов") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        timeText = { TimeText() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = status,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        status = try {
                            val resp = RetrofitClient.api.controlLock("unlock", "DEVICE_ID")
                            if (resp.isSuccessful) "Открыто ✓" else "Ошибка"
                        } catch (e: Exception) {
                            "Ошибка: ${e.message}"
                        }
                        isLoading = false
                    }
                },
                enabled = !isLoading,
                colors = ButtonDefaults.primaryButtonColors()
            ) {
                Text("Открыть", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        status = try {
                            val resp = RetrofitClient.api.controlLock("lock", "DEVICE_ID")
                            if (resp.isSuccessful) "Закрыто ✓" else "Ошибка"
                        } catch (e: Exception) {
                            "Ошибка: ${e.message}"
                        }
                        isLoading = false
                    }
                },
                enabled = !isLoading,
                colors = ButtonDefaults.secondaryButtonColors()
            ) {
                Text("Закрыть", fontSize = 16.sp)
            }
        }
    }
}
