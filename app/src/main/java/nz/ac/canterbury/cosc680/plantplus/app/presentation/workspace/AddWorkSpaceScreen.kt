package nz.ac.canterbury.cosc680.plantplus.app.presentation

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
import androidx.navigation.NavController
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun AddWorkSpaceScreen(
    navController: NavController,onClickCreate : (workSpace:WorkSpace)-> Unit
) {
    Scaffold(
        topBar = {
            TopBar(stringResource(R.string.main_screen_name), isMainScreen = true, onClick = {})
        },
        content = {

            Column(modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 60.dp))
                val landname = remember { mutableStateOf(TextFieldValue()) }
                val landlocation = remember { mutableStateOf(TextFieldValue()) }


                Text(text = "Create Work Space", style = TextStyle(fontSize = 20.sp, fontFamily = FontFamily.Default))

                Spacer(modifier = Modifier.padding(top = 20.dp))
                TextField(
                    label = { Text(text = "Land Name") },
                    value = landname.value,
                    onValueChange = { landname.value = it })

                TextField(
                    label = { Text(text = "Land Location") },
                    value = landlocation.value,
                    onValueChange = { landlocation.value = it })


                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            //navController.navigate(Screen.LoginPageScreen.route)
                            var workSpace = WorkSpace(name = landname.value.text, desc = landlocation.value.text)
                            onClickCreate(workSpace)
                                  },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Create Work Space")
                    }
                }
            }


        } ,
                bottomBar = {
            ScreenBottomBar(navController)}


    )}






//                UserInfoRow(
//                    label = stringResource(R.string.name_label),
//                    value = "User name goes here"
//                )
//                UserInfoRow(
//                    label = stringResource(R.string.email_label),
//                    value = "Email goes here"
//                )
//                UsableButton(text = stringResource(id = R.string.signup), {navController.navigate(
//                    Screen.SignUpScreen.route) })
//                UsableButton(text = stringResource(id = R.string.login), {navController.navigate(
//                    Screen.LogInScreen.route) })
//                UsableButton(text = stringResource(id = R.string.forgetpass), {navController.navigate(
//                    Screen.ForgetPasswardScreen.route) })
//
//                UsableButton(text = stringResource(id = R.string.login), {navController.navigate(
//                    Screen.MainScreen.route) })


