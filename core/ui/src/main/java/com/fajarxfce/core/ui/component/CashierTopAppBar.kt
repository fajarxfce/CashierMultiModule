package com.fajarxfce.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp,
    toolbarTitle: String,
    backButtonAction: () -> Unit
) {
    TopAppBar(
        modifier = modifier.shadow(elevation),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor,
            titleContentColor = contentColor
        ),
        title = {
            CashierText(
                text = toolbarTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 64.dp),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            TextButton(
                modifier = Modifier.padding(start = 14.dp),
//                colors = ButtonDefaults.buttonColors(
//                    contentColor = backgroundColor
//                ),
                onClick = backButtonAction
            ) {
                CashierIcon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    tint = CashierBlue
                )
            }
        },
        actions = {

        },
    )
}

@Preview
@Composable
private fun PreviewBaseTopAppBar() {
    AppTheme {
        BaseTopAppBar(
            modifier = Modifier,
            toolbarTitle = "Menu",
            backButtonAction = {},
        )
    }
}