package nz.ac.canterbury.cosc680.plantplus

import EMPTY_IMAGE_URI
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantDatabase
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("nz.ac.canterbury.cosc680.plantplus", appContext.packageName)
    }

    @Test
    fun add_user(){
        Log.d("T","TODO: need implement")
    }

    @Test
    fun listPredictResult(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val database = PlantDatabase.getDatabase(appContext)

        val list = database.resultDao().getAll()

        Log.i("predict result",list.toString())

    }

    @Test
    fun add_Result(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        var db = PlantDatabase.getDatabase(appContext)
        val resultDao = db.resultDao()

        var result = PredictResult(  )
        result.imageUri = EMPTY_IMAGE_URI
        result.disease = "spot"
        result.possibility = 0.89

        Log.d("T",result.toString())
    }

    @Test
    fun list_Result(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        var db = PlantDatabase.getDatabase(appContext)
        val resultDao = db.resultDao()

        val listResult = resultDao.getAll()

        listResult.forEach(){
            Log.d("T",it.toString())
        }


    }
}