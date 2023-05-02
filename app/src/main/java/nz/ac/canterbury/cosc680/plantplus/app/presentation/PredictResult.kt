package nz.ac.canterbury.cosc680.plantplus.app.presentation


import android.annotation.SuppressLint
import android.widget.CheckBox
import android.widget.Toast
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nz.ac.canterbury.cosc680.plantplus.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import nz.ac.canterbury.cosc680.plantplus.LocalPredictServiceViewModel
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.SmallUsableButton
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.UsableButton
import nz.ac.canterbury.cosc680.plantplus.app.utils.A
import nz.ac.canterbury.cosc680.plantplus.app.utils.CustomItem


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PredictResultScreen(navController: NavHostController) {

    Scaffold(
        topBar = {

            TopBar(stringResource(R.string.classificationResult), onClick = {})


        },
        content = {
            val context = LocalContext.current
            val predictlocalViewModel = LocalPredictServiceViewModel.current
            val results by predictlocalViewModel.predictResults.collectAsState()
            Column(modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Row(){
                    if(results.isNotEmpty()){
                        val image: Painter = rememberImagePainter(results[0].imageUri)
                        Image(painter = image,contentDescription = "test",
                            modifier = Modifier
                                .size(350.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .padding(12.dp))
                    }
                }

                LazyColumn(Modifier.weight(3f)) {
                    items(items = results) { item ->
                        predictlocalViewModel.addPredictResult(item)
                        CustomItem(result = item)
                    }

                }
                Row(){
                    Button(onClick = {
                        predictlocalViewModel.feedback(context = context, uri = A.uri, disease = A.disease)
                        Toast.makeText(context, "FeedBack Succeed", Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.ScanScreen.route)
                    }, Modifier.weight(1f)) {
                        Text(text = "Feedback")
                    }
                }
            }

        },

    )






        
    }







@Composable
@Preview
fun PredictResultPreview(){
    PredictResultScreen(navController = rememberNavController())
}

