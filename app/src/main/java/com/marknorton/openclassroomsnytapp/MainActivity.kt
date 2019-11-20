package com.marknorton.openclassroomsnytapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.ui.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

var counter =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setSupportActionBar(toolbar)

        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopStoriesFragment(), "Top Stories")
        adapter.addFragment(MostPopularFragment(), "Most Popular")
        adapter.addFragment(TechnologyFragment(), "Technology")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var selectedOption = ""
        when (item?.itemId) {
            R.id.menuAbout -> selectedOption = "about"
            R.id.menuHelp -> selectedOption = "help"
            R.id.menuNotification -> selectedOption = "notification"
            R.id.menuSearch -> selectedOption = "search"
//            R.id.exit -> selectedOption = "exit"
//            R.id.menuBack -> selectedOption = "back"
        }
        when(selectedOption){
            "about" ->{  val intent = Intent(this, About::class.java)
  //              intent.putExtra("uid", userUID)
                startActivity(intent)}
            "help"->{  val intent = Intent(this, Help::class.java)
                startActivity(intent)}
            "notification" ->{val intent = Intent(this, Notification::class.java)
                startActivity(intent)}
            "search" ->{
                val intent = Intent(this, SearchArticles::class.java)
                startActivity(intent)}


//                val intent = Intent(this, SearchActivity::class.java)
//                startActivity(intent)}
//            "exit" ->{finish()}
//            "back" ->{this.onBackPressed()
//                return true
//            }
        }

        return super.onOptionsItemSelected(item)
    }



    private fun showDialog(){
        lateinit var dialog: AlertDialog
        val arrayCategories = arrayOf("Art","Business","Entrepreneurs","Politics","Sports","Travel")
        val arrayChecked = booleanArrayOf(true,false,false,false,false,false)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Category . . . ")
        builder.setMultiChoiceItems(arrayCategories, arrayChecked, {dialog,which,isChecked->
            arrayChecked[which] = isChecked
            val category = arrayCategories[which]
//            Toast.makeText(this,"$category clicked.", Toast.LENGTH_SHORT).show()

        })

        builder.setPositiveButton("OK") { _, _ ->
            var categories = ""
            for (i in 0 until arrayCategories.size) {
                val checked = arrayChecked[i]
                if (checked) {
                    counter++

                    if((arrayCategories[i]) == "Art") {
                        categories += "\"Art\" "
                    }
                    if((arrayCategories[i]) == "Business") {
                        categories += "\"Business\" "
                    }
                    if((arrayCategories[i]) == "Entrepreneurs") {
                        categories += "\"Entrepreneurs\" "
                    }
                    if((arrayCategories[i]) == "Politics") {
                        categories += "\"Politics\" "
                    }
                    if((arrayCategories[i]) == "Sports") {
                        categories += "\"Sports\" "
                    }
                    if((arrayCategories[i]) == "Travel") {
                        categories += "\"Travel\" "
                    }
                }
            }
            if(counter < 1){
                Toast.makeText(this,"You MUST choose at least 1 Category.", Toast.LENGTH_SHORT).show()
                showDialog()
            }
            val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("Categories", categories)
            startActivity(intent)
        }
        dialog = builder.create()
        dialog.show()
        return
    }
}


