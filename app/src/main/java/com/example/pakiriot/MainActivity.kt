package com.example.pakiriot

import android.content.Intent
import android.os.Bundle
import android.system.Os.close
import android.system.Os.open
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.pakiriot.databinding.ActivityMainBinding
import com.example.pakiriot.ui.gallery.GalleryFragment
import com.example.pakiriot.ui.home.HomeFragment
import com.example.pakiriot.ui.slideshow.SlideshowFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var toogle: ActionBarDrawerToggle

    lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mAuth = FirebaseAuth.getInstance()
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open , R.string.close )
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_gallery -> replaceFragment(GalleryFragment(), it.title.toString())
                R.id.nav_slideshow -> replaceFragment(SlideshowFragment(), it.title.toString())
                R.id.nav_logout ->
                    logout()
            }
            true
        }
        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/D6 BB 95 E2/saldo")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                val data : Int? = value.toInt()
//                Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                if (data!! <= 0){
                    val notificationHelper = NotificationHelper(this@MainActivity)
                    notificationHelper.sendHighPriorityNotification("Saldo Tidak Cukup Mohon TOPUP SALDO",
                        "", MainActivity::class.java)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })
//        dbRef = FirebaseDatabase.getInstance().getReference("KualitasUdara/gas")
//        dbRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.value.toString()
//                val data:Double? = value.toDouble()
////                Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
//                if (data!! >= 125){
//                    val notificationHelper = NotificationHelper(this@MainActivity)
//                    notificationHelper.sendHighPriorityNotification("CO2 Melebihi Ambang Batas!",
//                        "", HomeFragment::class.java)
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, "Failed To Get Data", Toast.LENGTH_LONG).show()
//            }
//        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onContextItemSelected(item)
    }
    private fun replaceFragment(fragment: Fragment, title:String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }
    private fun logout(){
        val currentUser: FirebaseUser? = mAuth.getCurrentUser()
        if (currentUser == null) {
            Toast.makeText(this, "current user null", Toast.LENGTH_SHORT).show()
        } else {
//            startActivity(Intent(this, NavigationBarActivity::class.java))
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

    }
}