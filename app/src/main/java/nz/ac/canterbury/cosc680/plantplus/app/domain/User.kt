package nz.ac.canterbury.cosc680.plantplus.app.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User {

    @PrimaryKey(autoGenerate = true)
    var userID: Long = 0

    @ColumnInfo
    var password : String = ""

    @ColumnInfo
    var nickName : String = ""

    @ColumnInfo
    var mail : String = ""

    @ColumnInfo
    var date : String = ""

    constructor(){}

    //for add
    @Ignore
    constructor(nickName: String, password: String, mail:String,){
        this.nickName = nickName
        this.password = password
        this.mail = mail
        this.userID = 0
    }

    // for update
    @Ignore
    constructor(userId: Long, nickName: String, password: String, mail:String,){
        this.userID = userId

        this.nickName = nickName
        this.password = password
        this.mail = mail
    }

    override fun toString() = "{$userID}-{$nickName}-{$mail}"
}