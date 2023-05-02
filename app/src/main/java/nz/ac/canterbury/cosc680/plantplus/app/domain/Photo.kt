package nz.ac.canterbury.cosc680.plantplus.app.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "photos")
class Photo {
    @PrimaryKey(autoGenerate = true)
    var photoId : Long = 0

    @ColumnInfo
    var path : String = ""

    @ColumnInfo
    var uri : String = ""

    @ColumnInfo
    var workSpaceId : Long = 0

    constructor(){}

    //for add
    @Ignore
    constructor(path: String, uri: String, ){
        this.path = path
        this.uri = uri
    }

    //for edit
    @Ignore
    constructor(photoId:Long, path: String, uri: String, ){
        this.photoId = photoId
        this.path = path
        this.uri = uri
    }

    override fun toString() = "<Photo {$photoId}-{$path}-{$uri}>"

}