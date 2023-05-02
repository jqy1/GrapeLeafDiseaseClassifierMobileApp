package nz.ac.canterbury.cosc680.plantplus.app.utils

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nz.ac.canterbury.cosc680.plantplus.LocalPredictServiceViewModel
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult
import java.net.URI

class A {
    companion object{
        var disease = ""
        var uri: Uri = Uri.parse("file://dev/null")
    }
}
@Composable
fun CustomItem(result: PredictResult){



    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            text = result.disease,
            color = if(result.possibility>0.5){
                Color.Red
            }else {
                Color.Black
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = (String.format("%.4f", result.possibility * 100) + "%"),
            color = if(result.possibility>0.5){
                Color.Red
            }else {
                Color.Black
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        )
        val checkedState = remember { mutableStateOf(false) }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it  }
        )
        if (checkedState.value){
            A.disease = result.disease
            A.uri = result.imageUri
        }

    }
}


@Preview
@Composable
fun CustomItemPreview(){
    //CustomItem()

}