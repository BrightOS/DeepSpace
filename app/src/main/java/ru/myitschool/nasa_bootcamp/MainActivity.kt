package ru.myitschool.nasa_bootcamp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.myitschool.nasa_bootcamp.data.model.models.Comment
import ru.myitschool.nasa_bootcamp.data.model.nasa.asteroids.AsteroidRepository
import ru.myitschool.nasa_bootcamp.databinding.ActivityMainBinding
import ru.myitschool.nasa_bootcamp.ui.comments.CommentsViewModelImpl
import ru.myitschool.nasa_bootcamp.ui.registration.RegistrationViewModelImpl

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)

        var test = CommentsViewModelImpl()
        var auth = RegistrationViewModelImpl()
        auth.viewModelScope.launch {
            auth.authenticateUser("abubakirov04@mail.ru", "123456")
        }
        test.listenForComments(2)
        test.comments.observe(this, {
            it?.let {
                for (comment in it) {
                    println(comment)
                }
            }
        })
        /*
        test.viewModelScope.launch {
            test.comments.observe(this@MainActivity, Observer {
                for (comment in it) {
                    println(comment)
                }
            })
        }

         */


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // enable close drawer on back pressed
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    // open drawer from fragment
    fun openDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.openDrawer(GravityCompat.START)
    }

}