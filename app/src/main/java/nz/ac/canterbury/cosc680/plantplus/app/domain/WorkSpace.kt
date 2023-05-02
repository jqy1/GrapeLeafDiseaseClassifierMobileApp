package nz.ac.canterbury.cosc680.plantplus.app.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "workspaces")
class WorkSpace {
    @PrimaryKey(autoGenerate = true)
    var workSpaceID: Long = 0

    @ColumnInfo
    var name : String = ""

    @ColumnInfo
    var desc : String = ""

    constructor(){}

    //for add
    @Ignore
    constructor(name: String, desc: String, ){
        this.name = name
        this.desc = desc
    }

    // for update
    @Ignore
    constructor(workSpaceId: Long, name: String, desc: String,){
        this.workSpaceID = workSpaceId

        this.name = name
        this.desc = desc
    }

    override fun toString() = "{$workSpaceID}-{$name}-{$desc}"
}