package utfpr.edu.br.motelanimal.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import utfpr.edu.br.motelanimal.utils.ObjectUtils


internal abstract class DataBaseHandler protected constructor(
    context: Context?,
    val tablename: String
) :
    SQLiteOpenHelper(context, "motelanimal", null, 1) {

    init {
        onCreate(this.writableDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $tablename;")
    }

    protected fun save(values: ContentValues?): Long {
        return this.writableDatabase.insert(tablename, null, values)
    }

    protected fun update(registro: ContentValues?, id: Int): Int {
        return this.writableDatabase.update(tablename, registro, "_id = ?", arrayOf(id.toString()))
    }

    fun delete(id: Int): Int {
        return this.writableDatabase.delete(tablename, "_id = ?", arrayOf(id.toString()))
    }

    fun findOneBy(field: String, value: String): Cursor? {
        val registros = this.writableDatabase.query(
            tablename,
            null, "$field = ?", arrayOf(value),
            null, null, null
        )
        if (ObjectUtils.isNotEmpty(registros) && registros.moveToNext()) {
            registros.moveToPrevious()
            return registros
        }
        return null
    }

    fun findList(orderBy: String? = null): Cursor? {
        try {
            val registros =
                this.writableDatabase.query(tablename, null, null, null, null, null, orderBy)
            if (ObjectUtils.isNotEmpty(registros) && registros.moveToNext()) {
                registros.moveToPrevious()
                return registros
            }
        } catch (ignored: Exception) {
        }
        return null
    }

    fun getColumn(cursor: Cursor, columnName: String?): String? {
        return try {
            cursor.getString(cursor.getColumnIndexOrThrow(columnName))
        } catch (e: Exception) {
            null
        }
    }
}