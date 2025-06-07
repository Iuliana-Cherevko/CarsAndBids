package com.example.carsandbids.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.carsandbids.R
import com.example.carsandbids.navigation.Routes
import com.example.carsandbids.ui.theme.Iron
import com.example.carsandbids.ui.theme.MineShaft
import com.example.carsandbids.ui.theme.SilverChalice
import com.example.carsandbids.ui.theme.White

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    color: Color = White
) {
    val items = listOf(
        BottomNavItem("Auctions", R.drawable.auctions, currentRoute == Routes.AuctionList.route) {
            onNavigate(Routes.AuctionList.route)
        },
        BottomNavItem("Showcase", R.drawable.video, currentRoute == Routes.Showcase.route) {
            onNavigate(Routes.Showcase.route)
        },
        //        BottomNavItem("Watchlist", R.drawable.watchlist, false),
        BottomNavItem("Dashboard", R.drawable.dashboard, currentRoute == "dashboard") { /* todo */ },
        BottomNavItem("Community", R.drawable.community, currentRoute == "dashboard") { /* todo */ },
        BottomNavItem("Notifications", R.drawable.notifications, currentRoute == "dashboard") { /* todo */ }
    )


    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = Iron
        )

        NavigationBar(
            containerColor = color,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = item.isSelected,
                    onClick = item.onClick,
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.label,
                            tint =  if (item.isSelected) (if(color == Color.Black) White else MineShaft) else SilverChalice,
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            maxLines = 1,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (item.isSelected) (if(color == Color.Black) White else MineShaft) else SilverChalice,
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    ),
                )
            }
        }
    }
}
