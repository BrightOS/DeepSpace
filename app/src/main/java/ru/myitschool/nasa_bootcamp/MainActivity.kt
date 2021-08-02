package ru.myitschool.nasa_bootcamp

import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.databinding.ActivityMainBinding
import ru.myitschool.nasa_bootcamp.databinding.NavHeaderMainBinding
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil
import ru.myitschool.nasa_bootcamp.utils.errorEmailIsNotVerified

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Makes the StatusBar and NavigationBar truly transparent
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
        stopLoadingAnimation(false)

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    // open drawer from fragment
    fun openDrawer() {
        if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    fun changeHeader() {
        val mFirebaseUser = MFirebaseUser()
        if (mFirebaseUser.isUserAuthenticated()) {
            mFirebaseUser.viewModelScope.launch {
                mFirebaseUser.getUserAvatar().observe(this@MainActivity) {
                    navHeaderMainBinding.userAvatar.setOnClickListener { }
                    when (it) {
                        is Data.Ok -> {
                            navHeaderMainBinding.userAvatar.setImageBitmap(it.data)
                        }
                        is Data.Error -> {
                            // navHeaderMainBinding.userAvatar.setImageBitmap(null)
                            navHeaderMainBinding.userAvatar.foreground =
                                getDrawable(R.drawable.ic_photo_mini)
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

    fun startLoadingAnimation() {
        MainScope().launch {
            prepareLoadingView()
            loading_progress_bar.visibility = View.VISIBLE

            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, false)
            drawer_layout?.let {
                TransitionManager.beginDelayedTransition(it, sharedAxis)
            }

            loading_root.visibility = View.VISIBLE
        }
    }

    fun stopLoadingAnimation(showCheckIcon: Boolean = false) {
        MainScope().launch {
            if (showCheckIcon) {
                var sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, true)
                loading_root?.let {
                    TransitionManager.beginDelayedTransition(it, sharedAxis)
                }

                loading_progress_bar.visibility = View.GONE
                done_pic.visibility = View.VISIBLE

                GlobalScope.launch {
                    delay(1000)
                    MainScope().launch {
                        sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                        drawer_layout?.let {
                            TransitionManager.beginDelayedTransition(it, sharedAxis)
                        }

                        loading_root.visibility = View.GONE
                    }
                }
            } else {
                val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                drawer_layout?.let {
                    TransitionManager.beginDelayedTransition(it, sharedAxis)
                }

                loading_root.visibility = View.GONE
            }
        }
    }

    fun showError(errorText: String = "Произошла непредвиденная ошибка.") {
        MainScope().launch {
            var sharedAxis: MaterialSharedAxis

            error_text.text = errorText

            if (loading_root.visibility == View.GONE) {
                prepareLoadingView()

                error_pic.visibility = View.VISIBLE
                error_text.visibility = View.VISIBLE

                sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)

                drawer_layout?.let {
                    TransitionManager.beginDelayedTransition(it, sharedAxis)
                }

                loading_root.visibility = View.VISIBLE
            } else {

                sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, true)
                loading_root?.let {
                    TransitionManager.beginDelayedTransition(it, sharedAxis)
                }

                loading_progress_bar.visibility = View.GONE
                error_pic.visibility = View.VISIBLE
                error_text.visibility = View.VISIBLE
            }

            GlobalScope.launch {
                delay(1000)
                MainScope().launch {
                    sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                    drawer_layout?.let {
                        TransitionManager.beginDelayedTransition(it, sharedAxis)
                    }

                    loading_root.visibility = View.GONE
                }
            }
        }
    }

    private fun prepareLoadingView() {
        loading_progress_bar.visibility = View.GONE
        error_pic.visibility = View.GONE
        error_text.visibility = View.GONE
        done_pic.visibility = View.GONE
    }
}