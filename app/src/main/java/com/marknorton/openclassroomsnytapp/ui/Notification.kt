package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_notification.*

class Notification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val searchPreferences = getSharedPreferences("search", 0)
        val artPreferences = getSharedPreferences("art", 0)
        val businessPreferences = getSharedPreferences("business", 0)
        val entrepreneurPreferences = getSharedPreferences("entrepreneur", 0)
        val politicsPreferences = getSharedPreferences("politics", 0)
        val sportsPreferences = getSharedPreferences("sports", 0)
        val travelPreferences = getSharedPreferences("travel", 0)
        val notificationPreferences = getSharedPreferences("notification", 0)


        val mySearch = searchPreferences.getString("search","Enter Search Term")
        etSearch.setText(mySearch)

        cbArt.isChecked =
            artPreferences.contains("checked") && artPreferences.getBoolean("checked",false)
        cbBusiness.isChecked = businessPreferences.contains("checked") && businessPreferences.getBoolean("checked",false)
        cbEntrepreneurs.isChecked = entrepreneurPreferences.contains("checked") && entrepreneurPreferences.getBoolean("checked",false)
        cbPolitics.isChecked = politicsPreferences.contains("checked") && politicsPreferences.getBoolean("checked",false)
        cbSports.isChecked = sportsPreferences.contains("checked") && sportsPreferences.getBoolean("checked",false)
        cbTravel.isChecked = travelPreferences.contains("checked") && travelPreferences.getBoolean("checked",false)
        notificationSwitch.isChecked = notificationPreferences.contains("checked") && notificationPreferences.getBoolean("checked",false)

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
//                Toast.makeText(applicationContext, "ATC Text=$etSearch", Toast.LENGTH_LONG).show()
//                searchPreferences.edit().putString("search", etSearch.text.toString()).apply()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val mySearches=etSearch.text.toString()
//                Toast.makeText(applicationContext, "OTC Text=$mySearch", Toast.LENGTH_SHORT).show()
                searchPreferences.edit().putString("search", mySearches).apply()
            }
        })

        cbArt.setOnCheckedChangeListener { _, _ ->
            if (cbArt.isChecked) {
                artPreferences.edit().putBoolean("checked", true).apply()
            } else {
                artPreferences.edit().putBoolean("checked", false).apply()
            }
        }
        cbBusiness.setOnCheckedChangeListener { _, _ ->
            if (cbBusiness.isChecked) {
                businessPreferences.edit().putBoolean("checked", true).apply()
            } else {
                businessPreferences.edit().putBoolean("checked", false).apply()
            }
        }
        cbEntrepreneurs.setOnCheckedChangeListener { _, _ ->
            if (cbEntrepreneurs.isChecked) {
                entrepreneurPreferences.edit().putBoolean("checked", true).apply()
            } else {
                entrepreneurPreferences.edit().putBoolean("checked", false).apply()
            }
        }
        cbPolitics.setOnCheckedChangeListener { _, _ ->
            if (cbPolitics.isChecked) {
                politicsPreferences.edit().putBoolean("checked", true).apply()
            } else {
                politicsPreferences.edit().putBoolean("checked", false).apply()
            }
        }
        cbSports.setOnCheckedChangeListener { _, _ ->
            if (cbSports.isChecked) {
                sportsPreferences.edit().putBoolean("checked", true).apply()
            } else {
                sportsPreferences.edit().putBoolean("checked", false).apply()
            }
        }
        cbTravel.setOnCheckedChangeListener { _, _ ->
            if (cbTravel.isChecked) {
                travelPreferences.edit().putBoolean("checked", true).apply()
            } else {
                travelPreferences.edit().putBoolean("checked", false).apply()
            }
        }
        notificationSwitch.setOnCheckedChangeListener { _, _ ->
            if (notificationSwitch.isChecked) {
                notificationPreferences.edit().putBoolean("checked", true).apply()
                if ((!cbArt.isChecked) && (!cbBusiness.isChecked) && (!cbEntrepreneurs.isChecked) && (!cbPolitics.isChecked) && (!cbSports.isChecked) && (!cbTravel.isChecked) || (etSearch.text.isEmpty())) {
                    Toast.makeText(
                        this@Notification,
                        "You MUST choose at least 1 Category and Search Term",
                        Toast.LENGTH_LONG
                    ).show()
                    notificationPreferences.edit().putBoolean("checked", false).apply()
                    notificationSwitch.isChecked = false
                }
            } else {
                notificationPreferences.edit().putBoolean("checked", false).apply()
            }
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}