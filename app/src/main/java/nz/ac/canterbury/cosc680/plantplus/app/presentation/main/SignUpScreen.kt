package nz.ac.canterbury.cosc680.plantplus.app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nz.ac.canterbury.cosc680.plantplus.LocalServiceViewModel
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SignUpScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val userViewModel = LocalServiceViewModel.current

    val isRegister = userViewModel.authtoken.collectAsState()
    if (isRegister.value != ""){
        userViewModel.setRegisterStatus(true)
        navController.navigate(Screen.LoginPageScreen.route)

    }


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
                val firstname = remember { mutableStateOf("") }
                val lastname = remember { mutableStateOf("") }
                val email = remember { mutableStateOf("") }
                val username = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }


                Text(text = "Sign Up", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

                Spacer(modifier = Modifier.padding(top = 20.dp))
                TextField(
                    label = { Text(text = "Firstname") },
                    value = firstname.value,
                    onValueChange = { firstname.value = it })

                TextField(
                    label = { Text(text = "Lastname") },
                    value = lastname.value,
                    onValueChange = { lastname.value = it })

                TextField(
                    label = { Text(text = "E-mail") },
                    value = email.value,
                    onValueChange = { email.value = it })

                TextField(
                    label = { Text(text = "Username") },
                    value = username.value,
                    onValueChange = { username.value = it })

                TextField(
                    label = {Text(text = "Password")},
                    value = password.value,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { password.value = it })

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            userViewModel.currentRegisterUser(firstname.value,lastname.value, email.value, username.value, password.value)
                            navController.navigate(Screen.LoginPageScreen.route)},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Sign Up")
                    }
                }





            }


        }

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


