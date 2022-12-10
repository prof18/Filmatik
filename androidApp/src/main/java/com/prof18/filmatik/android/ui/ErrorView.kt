package com.prof18.filmatik.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.prof18.filmatik.presentation.ErrorData

@Composable
fun ErrorView(
    errorData: ErrorData,
    onRetryClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = errorData.message,
            style = MaterialTheme.typography.bodyLarge
        )

        if (onRetryClick != null) {
            Button(onClick = onRetryClick) {
                Text(text = errorData.buttonText)
            }
        }
    }
}
