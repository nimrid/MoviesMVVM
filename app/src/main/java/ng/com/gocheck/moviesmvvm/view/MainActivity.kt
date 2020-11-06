package ng.com.gocheck.moviesmvvm.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ng.com.gocheck.moviesmvvm.R

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open_nav,
            R.string.close_nav)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PopularFragment()).commit()
            nav_view.setCheckedItem(R.id.popular_movies)
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else
            super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.popular_movies -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PopularFragment()).commit()

            R.id.upcoming -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UpcomingMovieFragment()).commit()

            R.id.top_rated -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TopRatedFragment()).commit()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
