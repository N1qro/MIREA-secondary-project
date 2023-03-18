package com.example.mirea_app

import android.content.Context
import androidx.room.*


@Entity(tableName = "TABLE_NAME")
data class DBDATA(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "snapshot_date", typeAffinity = ColumnInfo.TEXT)
    val createdAt: String,
)

@Dao
interface GENERALDAO {
    @Insert
    suspend fun insertData(dbDATA: DBDATA)

    @Query("DELETE FROM sensor_snapshots WHERE id = :id")
    suspend fun deleteDataById(id: Long)
}

@Database(entities = [DBDATA::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun GeneralDAO(): GENERALDAO
}

object AppDatabaseHelper {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (database != null) {
            return database!!
        }
        val databaseName = "DATABASENAME.db"
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
        database = db
        return db
    }
}