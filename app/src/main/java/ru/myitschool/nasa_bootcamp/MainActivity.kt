package ru.myitschool.nasa_bootcamp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.databinding.ActivityMainBinding
import ru.myitschool.nasa_bootcamp.databinding.NavHeaderMainBinding
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Эта штука делает прозрачную строку состояния и бар системной
        // навигации по-настоящему прозрачными.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        } else {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor = Color.TRANSPARENT
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)

        navHeaderMainBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        changeHeader()
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

    fun changeHeader() {
        val mFirebaseUser = MFirebaseUser()
        if (mFirebaseUser.isUserAuthenticated()) {
            mFirebaseUser.viewModelScope.launch {
                mFirebaseUser.getUserAvatar().observe(this@MainActivity) {
                    navHeaderMainBinding.userAvatar.setOnClickListener {  }
                    when (it) {
                        is Data.Ok -> {
                            navHeaderMainBinding.userAvatar.setImageBitmap(it.data)
                        }
                        is Data.Error -> {
                            navHeaderMainBinding.userAvatar.foreground = getDrawable(R.drawable.ic_photo_mini)
                            //navHeaderMainBinding.userAvatar.setImageBitmap(null)
                        }
                    }
                }
            }
            navHeaderMainBinding.signIn.text = getString(R.string.sign_out)
            navHeaderMainBinding.signIn.setOnClickListener {
                mFirebaseUser.signOutUser()
                changeHeader()
            }
        } else {
            navHeaderMainBinding.userAvatar.foreground = getDrawable(R.drawable.ic_login_mini)
            navHeaderMainBinding.userAvatar.setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                findNavController(R.id.nav_host_fragment).navigate(R.id.login)
            }
            navHeaderMainBinding.signIn.text = getString(R.string.log_in)
            navHeaderMainBinding.signIn.setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                findNavController(R.id.nav_host_fragment).navigate(R.id.login)
            }
        }

        DimensionsUtil.setMargins(
            navHeaderMainBinding.userAvatar,
            0,
            DimensionsUtil.getStatusBarHeight(resources),
            0,
            0
        )
    }
}