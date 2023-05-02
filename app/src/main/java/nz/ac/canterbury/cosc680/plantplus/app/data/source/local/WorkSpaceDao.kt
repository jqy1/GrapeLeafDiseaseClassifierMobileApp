package nz.ac.canterbury.cosc680.plantplus.app.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace

@Dao
interface WorkSpaceDao {
    @Query("SELECT * FROM workspaces")
    fun getAll(): List<WorkSpace>

    @Query("SELECT * FROM workspaces")
    fun loadAll(): Flow<List<WorkSpace>>


    @Query("SELECT count(workSpaceID) FROM workspaces")
    fun getCount() : Flow<Int>

    @Insert
    fun insert(workspace : WorkSpace): Long

    @Update
    fun update(workspace: WorkSpace)

    @Delete
    fun delete(workspace: WorkSpace)

}