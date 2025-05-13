package com.fajarxfce.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fajarxfce.core.ui.R
import com.fajarxfce.core.ui.component.CashierAppText
import com.fajarxfce.core.ui.extension.boldBorder
import com.fajarxfce.core.ui.extension.noRippleClickable
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierAppTheme

@Composable
internal fun FeaturedCard(
    title: String,
    iconId: Int,
    onMenuClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .height(120.dp)
            .background(
                color = CashierAppTheme.colors.background,
                shape = RoundedCornerShape(8.dp),
            )
            .boldBorder()
            .clip(RoundedCornerShape(8.dp))
            .noRippleClickable { onMenuClick() }
            .padding(16.dp),
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = title,
            tint = CashierAppTheme.colors.background,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        CashierAppText(
            text = title,
            style = CashierAppTheme.typography.heading5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun FeaturedCardPreview() {
    AppTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeaturedCard(
                title = "Point of Sale\nPOS",
                iconId = R.drawable.core_ui_ic_logo,
                onMenuClick = {},
            )

            FeaturedCard(
                title = "Kelola\nProduk",
                iconId = R.drawable.core_ui_ic_logo,
                onMenuClick = {},
            )
        }
    }
}
