package com.fajarxfce.core.ui.component.dialog

import android.os.Message
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.component.button.CashierMediumButton
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBackground
import com.fajarxfce.core.ui.theme.CashierGreen
import com.fajarxfce.core.ui.theme.CashierRed

@Composable
fun CashierDialog(
    message: String? = null,
    isSuccess: Boolean? = null,
    isCancelable: Boolean = true,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit = {},
) {
    val icon = when (isSuccess) {
        true -> Icons.Default.CheckCircle
        false -> Icons.Default.Error
        null -> null
    }
    val iconBg = if (isSuccess == true) CashierGreen else CashierRed

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
                    shape = RoundedCornerShape(size = 16.dp),
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier
                        .size(110.dp),
                    imageVector = it,
                    contentDescription = null,
                    tint = iconBg,
                )
            }
            CashierText(
                text = if (message.isNullOrEmpty()) stringResource(com.fajarxfce.core.ui.R.string.core_ui_success) else message,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            CashierMediumButton(
                text = stringResource(com.fajarxfce.core.ui.R.string.core_ui_ok),
                onClick = {
                    if (onConfirm != null) {
                        onConfirm()
                    } else {
                        onDismissRequest()
                    }
                },
            )
        }
    }
}

@Preview
@Composable
private fun CashierDialogPreview() {
    AppTheme {
        CashierDialog(
            message = "This is a message",
            isSuccess = true,
            onDismissRequest = {},
        )
    }
}