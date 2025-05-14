package com.fajarxfce.feature.pos.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.extension.boldBorder

@Composable
fun PosSearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    onClicke: (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
) {
//    BasicTextField(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(50.dp)
//            .clip(RoundedCornerShape(16.dp))
//            .boldBorder(),
//        state = TODO(),
//    )
}