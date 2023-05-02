package nz.ac.canterbury.cosc680.plantplus.app.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen

//@Composable
//fun ScreenBottomBar(navController: NavController, modifier: Modifier = Modifier) {
//    BottomAppBar(
//        content = {
//            Row(
//                modifier = modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
////                Button(onClick = { navController.navigate(Screen.MainScreen.route) }) {
////                    Text(text = "Home")
////                }
//
//
//                IconButton(onClick = {
//                    navController.navigate(Screen.MainScreen.route)
//                }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.baseline_home_24),
//                        contentDescription = null
//                    )
//                }
//
//                IconButton(onClick = {
//                    navController.navigate(Screen.ScanScreen.route)
//                }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_baseline_document_scanner_24),
//                        contentDescription = null
//                    )
//                }
//
//                Button(onClick = { navController.navigate(Screen.SettingScreen.route) }) {
//                    Text(text = stringResource(id = R.string.setting_name))
//                }
//            }
//        }
//    )
//}


@Composable
fun ScreenBottomBar(navController: NavController, modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier=modifier) {
        BottomNavigationItem(
            selected = true,
            onClick = { navController.navigate(Screen.MainScreen.route)},
            icon = {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.Spa,
                    contentDescription = null
                )
            },
            label = {
                androidx.compose.material.Text(stringResource(R.string.home_screen))
            }
        )
        BottomNavigationItem(
            icon = {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null
                )
            },
            label = {
                androidx.compose.material.Text(stringResource(R.string.scan))
            },
            selected = false,
            onClick = {navController.navigate(Screen.ScanScreen.route)}
        )
        BottomNavigationItem(
            icon = {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                androidx.compose.material.Text(stringResource(R.string.setting_name))
            },
            selected = false,
            onClick = {navController.navigate(Screen.SettingScreen.route)}
        )



    }
}
