package com.yeyosystem.youtubeapi.tools

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity: AppCompatActivity() {

    //lateinit var auth: FirebaseAuth
    protected var photoURI: Uri? = null
    protected var mCurrentPhotoPath: String? = null
    private lateinit var dialog: Dialog

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loadAuth()
        //loadTheme()
        //createProgress()
    }

    fun toActivity(
        baseActivity: BaseActivity,
        activity: Class<*>,
        finish: Boolean,
        bundle: Bundle?
    ) {

        val intent = Intent(baseActivity, activity)
        if (bundle != null)
            intent.putExtras(bundle)
        startActivity(intent)
        if (finish) {
            finish()
        }
    }

   /* private fun loadAuth(){
        auth = FirebaseAuth.getInstance()
    }*/

    /*private fun createProgress() {
        dialog = Dialog(this)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_progress_bar)
        dialog.setCancelable(false)
    }*/

   /* protected fun loadProgress() {
        dialog.show()
    }*/


    /*protected fun closeProgress() {
        dialog.dismiss()
    }*/

    fun checkAndRequestPermission(permissions: Array<String>, requestCode: Int): Boolean {
        val permissionNeeded = arrayListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionNeeded.add(permission)
            }
        }
        if (!permissionNeeded.isNullOrEmpty()) {
            ActivityCompat.requestPermissions(this, permissionNeeded.toTypedArray(), requestCode)
            return false
        }
        return true
    }

    /*fun alertGoToSettings(requestCode: Int) {
        DialogUtil.showSimpleDialog(
            this.resources.getString(R.string.message_notice_title),
            this.resources.getString(R.string.message_permission_body),
            ContextCompat.getDrawable(this, R.drawable.ic_warning),
            this.resources.getString(R.string.button_go_to_settings), null, { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, requestCode)
            }, null, this
        )
    }*/

    private fun loadTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun getPickImageChooserIntent() {
        val options = arrayOf<CharSequence>(
            "Take photo", "Choose from library",
            "Cancel"
        )
        val builder: AlertDialog.Builder = AlertDialog.Builder(
            this
        )

        builder.setTitle("Select")

        builder.setItems(options) { dialog, which ->
            when {
                options[which] == "Take photo" -> {
                    try {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, 1)
                    } catch (ex: ActivityNotFoundException) {
                        Log.d(
                            "BaseActivity",
                            "Whoops - your device doesn't support capturing images!"
                        )
                    }
                }
                options[which] == "Choose from library" -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.type = "image/*"
                    if (intent.resolveActivity(packageManager) != null) {
                        var photoFile: File? = null
                        try {
                            photoFile = createImageFile()
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                        }
                        if (photoFile != null) {
                            val values = ContentValues()
                            values.put(MediaStore.Images.Media.TITLE, "MyPicture")
                            values.put(
                                MediaStore.Images.Media.DESCRIPTION,
                                "Photo taken on " + System.currentTimeMillis()
                            )
                            photoURI = contentResolver.insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                            )

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(intent, 0)
                        }
                    }

                }
                options[which] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        val dialog = builder.create()
        //dialog.window!!.attributes.windowAnimations = R.style.dialog_animation
        dialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }
}