package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_review.*
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.properties.Delegates

class ReviewFragment : Fragment(R.layout.fragment_review) {
    val args: ReviewFragmentArgs by navArgs()
    private var systemUI by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).main_loading?.startLoadingAnimation()

        Glide.with(this)
            .load(args.url)
            .apply(RequestOptions().override(background.width / 2, background.height / 2))
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    Glide.with(this@ReviewFragment)
                        .load(resource)
                        .into(background)

                    val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, true)
                    TransitionManager.beginDelayedTransition(review_root, sharedAxis)

                    (activity as MainActivity).main_loading?.stopLoadingAnimation()
                    background.visibility = View.VISIBLE
                    download.visibility = View.VISIBLE
                    set_as_wallpaper.visibility = View.VISIBLE

                    download.setOnClickListener {
                        saveImageToStorage(
                            requireContext(),
                            resource.toBitmap(),
                            args.url.substringAfterLast('/'),
                            false
                        )
                    }

                    set_as_wallpaper.setOnClickListener {
                        saveImageToStorage(
                            requireContext(),
                            resource.toBitmap(),
                            args.url.substringAfterLast('/'),
                            true
                        )
                    }
                }

            })

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().window.decorView.let {
            systemUI = it.systemUiVisibility
            it.systemUiVisibility =
                it.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    private fun beginDownload(
        context: Context,
        bitmap: Bitmap,
        displayName: String,
        setAsWallpaper: Boolean
    ) {
        val mimeType = "image/${
            displayName.substringAfterLast('.').lowercase(Locale.getDefault()).let {
                when (it) {
                    "jpg" -> "jpeg"
                    "bmp" -> "x-ms-bmp"
                    "wbmp" -> "vnd.wap.wbmp"
                    else -> it
                }
            }
        }"
        val imageOutStream: OutputStream
        val uri: Uri?

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/DeepSpace"
                )
        }

        uri =
            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )

        println(getRealPathFromURI(context, uri))
        println(uri)

        imageOutStream = context.contentResolver.openOutputStream(uri!!)!!

        if (setAsWallpaper) {
            val intent2 = Intent(Intent.ACTION_ATTACH_DATA)
            intent2.addCategory(Intent.CATEGORY_DEFAULT)
            intent2.setDataAndType(uri, "image/*")
            intent2.putExtra("mimeType", "image/*")
            intent2.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            requireActivity().startActivity(
                Intent.createChooser(
                    intent2,
                    "Set image as:"
                )
            )
        }

        Toast.makeText(
            context,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                "The image is saved in the Pictures/DeepSpace folder."
            else
                "The image is saved in the Pictures folder in your root directory.",
            Toast.LENGTH_SHORT
        )
            .show()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)
        imageOutStream.close()
    }

    @Throws(IOException::class)
    private fun saveImageToStorage(
        context: Context,
        bitmap: Bitmap,
        displayName: String,
        setAsWallpaper: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            beginDownload(context, bitmap, displayName, setAsWallpaper)
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            )
                beginDownload(context, bitmap, displayName, setAsWallpaper)
            else {
                Toast.makeText(
                    context,
                    "The app needs additional permissions to save images to the device memory.",
                    Toast.LENGTH_LONG
                )
                    .show()
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        123
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        123
                    )
                }
            }
        }
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri?): String {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            123 -> {
                Toast.makeText(context, "Required permits have been issued.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
