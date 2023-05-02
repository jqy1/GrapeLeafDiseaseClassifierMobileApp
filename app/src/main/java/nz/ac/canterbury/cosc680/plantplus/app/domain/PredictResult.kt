package nz.ac.canterbury.cosc680.plantplus.app.domain

import EMPTY_IMAGE_URI
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "results")
class PredictResult {

    @ColumnInfo
    var photoId : Long = 0

    @PrimaryKey(autoGenerate = true)
    var predictResultId : Long = 0

    @ColumnInfo
    var path : String = ""

    @ColumnInfo
    var uri : String = ""

    @Ignore
    var imageUri: Uri = EMPTY_IMAGE_URI

    @ColumnInfo
    var date :String = ""

    @ColumnInfo
    var disease : String = ""

    @ColumnInfo
    var possibility : Double = 0.0

    constructor(){}

    private fun setCurrentDate(){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        this.date = current.format(formatter)
    }

    //for add
    @Ignore
    constructor( photoId:Long, path:String, uri:String, ) {
        this.photoId = photoId
        this.path = path
        this.uri = uri
        setCurrentDate()
    }

    // for update
    @Ignore
    constructor(id:Long,photoId:Long,path:String, uri:String){
        this.predictResultId = id
        this.photoId = photoId
        this.path = path
        this.uri = uri
        setCurrentDate()
    }

    override fun toString() = "{$photoId}-{$disease}-{$possibility}"

}