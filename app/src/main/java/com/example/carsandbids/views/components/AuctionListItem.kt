package com.example.carsandbids.views.components

import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.carsandbids.R
import com.example.carsandbids.data.models.Auction
import com.example.carsandbids.ui.theme.Gray
import com.example.carsandbids.ui.theme.MineShaft
import com.example.carsandbids.ui.theme.White
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AuctionListItem(
    auction: Auction,
    onClick: () -> Unit
) {
    val formattedEndDate = ZonedDateTime.parse(auction.auctionEnd).format(DateTimeFormatter.ofPattern("MM/dd/yy"))
    val formattedBid = NumberFormat.getNumberInstance(Locale.US).format(auction.highBid)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = auction.mainImage?.formats?.large?.url,
                contentDescription = auction.title,
                modifier = Modifier.fillMaxSize()
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Sold for $$formattedBid",
                style = MaterialTheme.typography.bodySmall,
                color = White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 8.dp, bottom = 8.dp)
                    .background(MineShaft, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Max)
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = auction.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                )
                Icon(
                    painter = painterResource(id = R.drawable.watchlist),
                    contentDescription = null,
                    tint = MineShaft,
                    modifier = Modifier.height(28.dp)
                )
            }

            Text(
                text = auction.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(top = 5.dp)
            )

            Text(
                text = "Ended $formattedEndDate",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray,
                textAlign = TextAlign.Left,
                modifier = Modifier
            )
        }
    }
}