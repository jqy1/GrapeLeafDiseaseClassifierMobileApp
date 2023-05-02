package nz.ac.canterbury.cosc680.plantplus.app.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.ac.canterbury.cosc680.plantplus.app.domain.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE mail = (:mail)  LIMIT 1")
    fun findByName(mail: String): User

    @Query("SELECT * FROM users")
    fun loadAll(): Flow<List<User>>

    @Insert
    fun insert(result : User): Long

    @Update
    fun update(result: User)

    @Delete
    fun delete(result: User)
}