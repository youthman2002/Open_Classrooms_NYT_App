package com.marknorton.openclassroomsnytapp

import android.database.Cursor
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import androidx.test.core.app.ApplicationProvider.getApplicationContext

/**
 * Created by Mark Norton on 12/26/2020.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class DatabaseTest {

    @Test
    fun databaseTest_addHeadline() {
        val db = Database(getApplicationContext())
        db.addHeadline(headline)

        val sqLiteDatabase = db.writableDatabase
        val strSQL = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_HEADLINE = '$headline'"
        sqLiteDatabase.execSQL(strSQL)
        val result: Cursor = db.getHeadline(headline)
        result.moveToFirst()
        val theResult = result.getString(1)

        assertEquals(headline, theResult)
    }

    @Test
    fun databaseTest_getHeadline() {
        val db = Database(getApplicationContext())
        db.addHeadline(headline)
        val result: Cursor = db.getHeadline(headline)
        result.moveToFirst()
        val theResult = result.getString(1)

        assertEquals(headline, theResult)
    }

    @Test
    fun databaseTest_addViewed() {
        val db = Database(getApplicationContext())

        db.addHeadline(headline)
        db.addViewed(headline)
        val result: Cursor = db.getHeadline(headline)
        result.moveToFirst()
        val theResult = result.getString(2)

        assertEquals("1", theResult)
    }

    companion object {
        private const val headline = "The Test Headline"
        private const val TABLE_NAME = "headlines"
        private const val COLUMN_HEADLINE = "headline"
    }
}