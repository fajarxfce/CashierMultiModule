package com.fajarxfce.core.ui.component

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fajarxfce.core.ui.R
import com.fajarxfce.core.ui.theme.CashierAppTheme

data class DialogState(
    val message: String? = null,
    val isSuccess: Boolean? = null,
)

@Composable
fun CashierDialog(
    message: String? = null,
    isSuccess: Boolean? = null,
    isCancelable: Boolean = true,
    onDismissRequest: () -> Unit = {},
    onButtonClick: (() -> Unit)? = null
) {
    val icon = when (isSuccess) {
        true -> Icons.Rounded.Check
        false -> Icons.Rounded.Clear
        else -> null
    }
    val iconBg = if (isSuccess == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            icon?.let {
                Box(
                    modifier = Modifier
                        .background(
                            color = iconBg,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(8.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(64.dp),
                        imageVector = it,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            CashierAppText(
                text = if (message.isNullOrEmpty()) stringResource(R.string.core_ui_success) else message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Button(
                onClick = {
                    onButtonClick?.invoke()
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
            ) {
                CashierAppText(
                    text = stringResource(R.string.core_ui_ok),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CashierDialogPreview() {
    CashierDialog(
        message = "This is a message",
        isSuccess = true,
        onDismissRequest = {},
        onButtonClick = {}
    )
}