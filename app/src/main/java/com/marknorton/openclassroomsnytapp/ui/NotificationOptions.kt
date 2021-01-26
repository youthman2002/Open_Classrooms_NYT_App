package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.ART
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.BUSINESS
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.ENTREPRENEUR
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.NOTIFICATION
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.POLITICS
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SEARCH
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SEARCHSTRING
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SPORTS
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.TRAVEL
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationOptions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val searchPreferences = getSharedPreferences(SEARCH, 0)
        val artPreferences = getSharedPreferences(ART, 0)
        val businessPreferences = getSharedPreferences(BUSINESS, 0)
        val entrepreneurPreferences = getSharedPreferences(ENTREPRENEUR, 0)
        val politicsPreferences = getSharedPreferences(POLITICS, 0)
        val sportsPreferences = getSharedPreferences(SPORTS, 0)
        val travelPreferences = getSharedPreferences(TRAVEL, 0)
        val notificationPreferences = getSharedPreferences(NOTIFICATION, 0)

        val mySearch = searchPreferences.getString(SEARCH, "Enter Search Term")
        etSearch.setText(mySearch)

        cbArt.isChecked =
            artPreferences.contains("checked") && artPreferences.getBoolean("checked", false)
        cbBusiness.isChecked =
            businessPreferences.contains("checked") && businessPreferences.getBoolean(
                "checked",
                false
            )
        cbEntrepreneurs.isChecked = entrepreneurPreferences.contains("checked") && entrepreneurPreferences.getBoolean("checked",false)
        cbPolitics.isChecked = politicsPreferences.contains("checked") && politicsPreferences.getBoolean("checked",false)
        cbSports.isChecked = sportsPreferences.contains("checked") && sportsPreferences.getBoolean("checked",false)
        cbTravel.isChecked = travelPreferences.contains("checked") && travelPreferences.getBoolean("checked",false)
        notificationSwitch.isChecked = notificationPreferences.contains("checked") && notificationPreferences.getBoolean("checked",false)

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val mySearches = etSearch.text.toString()
                searchPreferences.edit().putString(SEARCH, mySearches).apply()
            }
        })

        cbArt.setOnCheckedChangeListener { _, _ ->
            if (cbArt.isChecked) {
                artPreferences.edit().putBoolean(ART, true).apply()
            } else {
                artPreferences.edit().putBoolean(ART, false).apply()
            }
        }
        cbBusiness.setOnCheckedChangeListener { _, _ ->
            if (cbBusiness.isChecked) {
                businessPreferences.edit().putBoolean(BUSINESS, true).apply()
            } else {
                businessPreferences.edit().putBoolean(BUSINESS, false).apply()
            }
        }
        cbEntrepreneurs.setOnCheckedChangeListener { _, _ ->
            if (cbEntrepreneurs.isChecked) {
                entrepreneurPreferences.edit().putBoolean(ENTREPRENEUR, true).apply()
            } else {
                entrepreneurPreferences.edit().putBoolean(ENTREPRENEUR, false).apply()
            }
        }
        cbPolitics.setOnCheckedChangeListener { _, _ ->
            if (cbPolitics.isChecked) {
                politicsPreferences.edit().putBoolean(POLITICS, true).apply()
            } else {
                politicsPreferences.edit().putBoolean(POLITICS, false).apply()
            }
        }
        cbSports.setOnCheckedChangeListener { _, _ ->
            if (cbSports.isChecked) {
                sportsPreferences.edit().putBoolean(SPORTS, true).apply()
            } else {
                sportsPreferences.edit().putBoolean(SPORTS, false).apply()
            }
        }
        cbTravel.setOnCheckedChangeListener { _, _ ->
            if (cbTravel.isChecked) {
                travelPreferences.edit().putBoolean(TRAVEL, true).apply()
            } else {
                travelPreferences.edit().putBoolean(TRAVEL, false).apply()
            }
        }
        notificationSwitch.setOnCheckedChangeListener { _, _ ->
            if (notificationSwitch.isChecked) {
                notificationPreferences.edit().putBoolean(NOTIFICATION, true).apply()
                if ((!cbArt.isChecked) && (!cbBusiness.isChecked) && (!cbEntrepreneurs.isChecked) && (!cbPolitics.isChecked) && (!cbSports.isChecked) && (!cbTravel.isChecked) || (etSearch.text.isEmpty())) {
                    Toast.makeText(
                        this@NotificationOptions,
                        getString(R.string.chooseOne),
                        Toast.LENGTH_LONG
                    ).show()
                    notificationPreferences.edit().putBoolean(NOTIFICATION, false).apply()
                    notificationSwitch.isChecked = false
                }
            } else {
                notificationPreferences.edit().putBoolean(NOTIFICATION, false).apply()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}