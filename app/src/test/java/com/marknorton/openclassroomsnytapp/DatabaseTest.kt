package com.marknorton.openclassroomsnytapp

import android.database.Cursor
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by Mark Norton on 12/26/2020.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class DatabaseTest {

    @Test
    fun canGetMood() {
        val headline = "3"
        val db = Database(RuntimeEnvironment.application)
        val sqLiteDatabase = db.writableDatabase
        val strSQL =
            "INSERT INTO $TABLE_NAME ($COLUMN_HEADLINE, $COLUMN_VIEWED) VALUES ('$headline', 0)"
        sqLiteDatabase.execSQL(strSQL)
        val result: Cursor = db.getHeadline(headline)
        result.moveToFirst()
        val theHeadline = result.getString(1)

        assertEquals(headline, theHeadline)
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