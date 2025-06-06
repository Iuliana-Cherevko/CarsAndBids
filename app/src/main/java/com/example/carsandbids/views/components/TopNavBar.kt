package com.example.carsandbids.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.carsandbids.R
import com.example.carsandbids.ui.theme.MineShaft
import com.example.carsandbids.ui.theme.Shamrock
import com.example.carsandbids.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = buildAnnotatedString {
                    append("cars")
                    withStyle(style = SpanStyle(color = Shamrock)) {
                        append(" & ")
                    }
                    append("bids")
                },
                color = MineShaft,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MineShaft,
                    modifier = Modifier.size(30.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.car),
                    contentDescription = null,
                    tint = MineShaft,
                    modifier = Modifier.height(26.dp)
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBackBar(
    title: String,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MineShaft
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MineShaft,
                textAlign = TextAlign.Center,
            )
        },
        modifier = Modifier.fillMaxWidth(),
    )
}


@Composable
fun TopShowcaseNavBar() { }