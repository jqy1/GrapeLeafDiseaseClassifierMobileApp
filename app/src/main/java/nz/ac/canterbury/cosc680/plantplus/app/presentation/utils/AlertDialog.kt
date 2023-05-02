package nz.ac.canterbury.cosc680.plantplus.app.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import nz.ac.canterbury.cosc680.plantplus.LocalPredictServiceViewModel

@Composable
fun ShowRepsoneResult(
    text: String,
) {
    var showDialg by remember { mutableStateOf(true) }
    if(showDialg){
        AlertDialog(
            onDismissRequest = { /* Don't */ },
            title = {
                Text(text = "Response Message")
            },
            text = {
                Text(text)
            },
            confirmButton = {
                Button( onClick = { showDialg = false}) {
                    Text("Ok")
                }
            }
        )
    }
}

@Composable
fun ShowPredictJSON(){
    val localPredictViewModel = LocalPredictServiceViewModel.current
    val result by localPredictViewModel.response.collectAsState()
    if("" != result){
        ShowRepsoneResult( result )
    }
}
