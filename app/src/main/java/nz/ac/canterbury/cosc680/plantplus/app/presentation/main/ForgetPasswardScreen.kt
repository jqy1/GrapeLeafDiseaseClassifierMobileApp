
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ForgetPasswardScreen(
    navController: NavHostController
) {
    Scaffold(

    ) {
        ForgetPasswardScreenBody(navController = navController)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswardScreenBody(modifier: Modifier = Modifier,navController: NavHostController) {

    Column(modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email = remember { mutableStateOf(TextFieldValue()) }

        Spacer(modifier = Modifier.padding(top = 60.dp))
        Text(text = "Password Reset", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(text = "You will receive instructions for reseting your passward from E-mail.", style = TextStyle(fontSize = 20.sp))
        Spacer(modifier = Modifier.padding(top = 20.dp))

        TextField(
            label = { Text(text = "E-mail") },
            value = email.value,
            onValueChange = { email.value = it })
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {navController.navigate(Screen.LoginPageScreen.route)},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "SEND")
            }
        }
    }
}