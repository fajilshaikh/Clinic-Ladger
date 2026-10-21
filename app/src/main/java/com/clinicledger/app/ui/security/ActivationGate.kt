package com.clinicledger.app.ui.security

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinicledger.app.util.KEY_ACTIVATED
import com.clinicledger.app.util.PREFS_NAME
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun todaysActivationKey(): String {
    val formatter = SimpleDateFormat("ddMMyyyy", Locale.US)
    return "fajil${formatter.format(Date())}"
}

/**
 * One-time activation gate: shown only until the correct key is entered once,
 * then never again on this device (tracked in SharedPreferences).
 */
@Composable
fun ActivationGate(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    var isUnlocked by remember { mutableStateOf(prefs.getBoolean(KEY_ACTIVATED, false)) }

    if (isUnlocked) {
        content()
    } else {
        ActivationScreen(
            onSubmit = { input ->
                val correct = input.trim().equals(todaysActivationKey(), ignoreCase = true)
                if (correct) {
                    prefs.edit().putBoolean(KEY_ACTIVATED, true).apply()
                    isUnlocked = true
                }
                correct
            }
        )
    }
}

@Composable
private fun ActivationScreen(onSubmit: (String) -> Boolean) {
    var key by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var keyVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.widthIn(max = 360.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text("Clinic Ledger", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(
                "Enter the activation key to unlock this app.",
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = key,
                onValueChange = {
                    key = it
                    showError = false
                },
                label = { Text("Activation Key") },
                singleLine = true,
                visualTransformation = if (keyVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { keyVisible = !keyVisible }) {
                        Icon(
                            if (keyVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (keyVisible) "Hide key" else "Show key"
                        )
                    }
                },
                isError = showError,
                supportingText = if (showError) {
                    { Text("Incorrect key. Please try again.") }
                } else null,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (!onSubmit(key)) {
                        showError = true
                        key = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Unlock", fontWeight = FontWeight.Bold)
            }
        }
    }
}
