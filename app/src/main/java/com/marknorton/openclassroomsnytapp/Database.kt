 package com.marknorton.openclassroomsnytapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
* Created by Mark Norton on 12/12/2019.
* This includes the Database Calls to sqLite.
*     --- Headlines are saved in this database.  They are marked once they are viewed
*     --- This serves to allow the software not to repeat notifications and show unviewed articles in a different color
*/
class Database(context: Context):
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(sqLiteDatabase:SQLiteDatabase) {
        // Create the initial Database
        val sql =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_HEADLINE VARCHAR,$COLUMN_VIEWED INTEGER)"
        sqLiteDatabase.execSQL(sql)
        val contentValues = ContentValues()

            // Database will contain 3 columns: ID, Headline and Viewed
            contentValues.put(COLUMN_HEADLINE, "None")
            contentValues.put(COLUMN_VIEWED, 0)
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues)

    }
    override fun onUpgrade(sqLiteDatabase:SQLiteDatabase, i:Int, i1:Int) {
        // Re-Create the database in the event of an upgrade or major change
        val sql =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_HEADLINE VARCHAR,$COLUMN_VIEWED INTEGER)"
        sqLiteDatabase.execSQL(sql)
        val contentValues = ContentValues()

        // Database will contain 3 columns: ID, Headline and Viewed
        contentValues.put(COLUMN_HEADLINE, "None")
        contentValues.put(COLUMN_VIEWED, 0)
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
    }
    internal fun addHeadline(headline:String) {
        // Add the headline to the database
        val sqLiteDatabase = this.writableDatabase
        val strSQL =
            "INSERT INTO $TABLE_NAME ($COLUMN_HEADLINE, $COLUMN_VIEWED) VALUES ('$headline', 0)"
        sqLiteDatabase.execSQL(strSQL)
    }
    internal fun addViewed(headline:String) {
        // Add the Viewed Headline to the database
        val sqLiteDatabase = this.writableDatabase
        val strSQL =
            "UPDATE $TABLE_NAME SET $COLUMN_VIEWED = 1 WHERE $COLUMN_HEADLINE = '$headline'"
        sqLiteDatabase.execSQL(strSQL)
    }
    @SuppressLint("Recycle")
    internal fun getHeadline(headline:String):Cursor {
        // Get the day's mood from the database
        val sqLiteDatabase = this.writableDatabase
        return (sqLiteDatabase.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_HEADLINE = '$headline'", null))
    }
    companion object {
        private const val DB_NAME = "NYTDatabase"
        private const val TABLE_NAME = "headlines"
        private const val COLUMN_ID = "id"
        private const val COLUMN_HEADLINE = "headline"
        private const val COLUMN_VIEWED = "viewed"
        private const val DB_VERSION = 1
    }
}