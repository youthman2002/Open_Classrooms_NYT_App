package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_notification.*

class Notification : AppCompatActivity() {
    var selectedCheckBox = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val artPreferences = getSharedPreferences("art", 0)
        val businessPreferences = getSharedPreferences("business", 0)
        val entreprenerPreferences = getSharedPreferences("entrepreneur", 0)
        val politicsPreferences = getSharedPreferences("politics", 0)
        val sportsPreferences = getSharedPreferences("sports", 0)
        val travelPreferences = getSharedPreferences("travel", 0)
        val notificationPreferences = getSharedPreferences("notification", 0)

        if(artPreferences.contains("checked") && artPreferences.getBoolean("checked",false) == true) {
            cbArt.setChecked(true)
        }else {
            cbArt.setChecked(false)
        }
        if(businessPreferences.contains("checked") && businessPreferences.getBoolean("checked",false) == true) {
            cbBusiness.setChecked(true)
        }else {
            cbBusiness.setChecked(false)
        }
        if(entreprenerPreferences.contains("checked") && entreprenerPreferences.getBoolean("checked",false) == true) {
            cbEntrepreneurs.setChecked(true)
        }else {
            cbEntrepreneurs.setChecked(false)
        }
        if(politicsPreferences.contains("checked") && politicsPreferences.getBoolean("checked",false) == true) {
            cbPolitics.setChecked(true)
        }else {
            cbPolitics.setChecked(false)
        }
        if(sportsPreferences.contains("checked") && sportsPreferences.getBoolean("checked",false) == true) {
            cbSports.setChecked(true)
        }else {
            cbSports.setChecked(false)
        }
        if(travelPreferences.contains("checked") && travelPreferences.getBoolean("checked",false) == true) {
            cbTravel.setChecked(true)
        }else {
            cbTravel.setChecked(false)
        }
        if(notificationPreferences.contains("checked") && notificationPreferences.getBoolean("checked",false) == true) {
            notificationSwitch.setChecked(true)
        }else {
            notificationSwitch.setChecked(false)
        }

        cbArt.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b:Boolean) {
                if (cbArt.isChecked()) {
                    artPreferences.edit().putBoolean("checked", true).apply()
                } else {
                    artPreferences.edit().putBoolean("checked", false).apply()}}
        })
        cbBusiness.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b:Boolean) {
                if (cbBusiness.isChecked()) {
                    businessPreferences.edit().putBoolean("checked", true).apply()
                } else {
                    businessPreferences.edit().putBoolean("checked", false).apply()}}
        })
        cbEntrepreneurs.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b:Boolean) {
                if (cbEntrepreneurs.isChecked()) {
                    entreprenerPreferences.edit().putBoolean("checked", true).apply()
                } else {
                    entreprenerPreferences.edit().putBoolean("checked", false).apply()}}
        })
        cbPolitics.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b:Boolean) {
                if (cbPolitics.isChecked()) {
                    politicsPreferences.edit().putBoolean("checked", true).apply()
                } else {
                    politicsPreferences.edit().putBoolean("checked", false).apply()}}
        })
        cbSports.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b:Boolean) {
                if (cbSports.isChecked()) {
                    sportsPreferences.edit().putBoolean("checked", true).apply()
                } else {
                    sportsPreferences.edit().putBoolean("checked", false).apply()}}
        })
        cbTravel.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b:Boolean) {
                if (cbTravel.isChecked()) {
                    travelPreferences.edit().putBoolean("checked", true).apply()
                } else {
                    travelPreferences.edit().putBoolean("checked", false).apply()}}
        })
        notificationSwitch.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b:Boolean) {
                if (notificationSwitch.isChecked()) {
                    notificationPreferences.edit().putBoolean("checked", true).apply()
                } else {
                    notificationPreferences.edit().putBoolean("checked", false).apply()}}
        })

//        TODO:  At least one MUST be selected before leaving




    }
}