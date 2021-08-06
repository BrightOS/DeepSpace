package ru.myitschool.nasa_bootcamp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepositoryImpl
import ru.myitschool.nasa_bootcamp.databinding.ActivityMainBinding
import ru.myitschool.nasa_bootcamp.databinding.NavHeaderMainBinding
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    private lateinit var interactionResult: ActivityResultLauncher<Intent>

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

        interactionResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                println("In")
                if (it.resultCode == Activity.RESULT_OK) {
                    setImage(it.data)
                }
            }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    // enable close drawer on back pressed
    override fun onBackPressed() {
        main_loading.stopLoadingAnimation(false)
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    private fun setImage(data: Intent?) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
            navHeaderMainBinding.userAvatar.setImageBitmap(bitmap)
            val user = FirebaseAuth.getInstance()
            if (data?.data != null) {
                FirebaseStorage.getInstance().getReference("user_data/${user.uid}").putFile(data.data!!)
            }
        } catch (e: IOException) {
            Toast.makeText(
                this,
                "Please, try another photo! (${e.message})",
                Toast.LENGTH_SHORT
            ).show()
        }
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
                    navHeaderMainBinding.userAvatar.setOnClickListener {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        intent.putExtra("requestCode", PICK_IMAGE_REQUEST)
                        interactionResult.launch(Intent.createChooser(intent, "Select avatar"))
                    }
                    when (it) {
                        is Data.Ok -> {
                            navHeaderMainBinding.userAvatar.foreground = null
                            navHeaderMainBinding.userAvatar.setImageBitmap(it.data)
                        }
                        is Data.Error -> {
                            navHeaderMainBinding.userAvatar.setImageBitmap(null)
                            navHeaderMainBinding.userAvatar.foreground =
                                getDrawable(R.drawable.ic_photo_mini)
                        }
                    }
                }
            }
            //binding.navView.menu.findItem(R.id.createPostFragment).isVisible = true
            binding.navView.menu.findItem(R.id.createPostFragment).isEnabled = true
            navHeaderMainBinding.signIn.text = getString(R.string.sign_out)
            navHeaderMainBinding.signIn.setOnClickListener {
                mFirebaseUser.signOutUser(this)
                changeHeader()
            }
        } else {
            binding.navView.menu.findItem(R.id.createPostFragment).isEnabled = false
            //binding.navView.menu.findItem(R.id.createPostFragment).isVisible = false
            navHeaderMainBinding.userAvatar.setImageBitmap(null)
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