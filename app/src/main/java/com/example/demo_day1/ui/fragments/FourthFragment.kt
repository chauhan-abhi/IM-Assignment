package com.example.demo_day1.ui.fragments


import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.demo_day1.R
import com.example.demo_day1.ui.activities.MainActivity
import com.example.demo_day1.base.ImagePickerActivity
import com.example.demo_day1.data.db.RegisterUserDbHelper
import com.example.demo_day1.data.db.UserContract
import com.example.demo_day1.interfaces.UpdateProfileInterface
import com.example.demo_day1.utils.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_fourth.*

class FourthFragment : Fragment() {

    private var uri: Uri? = null
    lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var navController: NavController
    private lateinit var updateProfileListener: UpdateProfileInterface

    private val REQUEST_IMAGE = 100

    private var fullName: String? = ""
    private var email: String? = ""
    private var password: String? = ""
    private var mobile: String? = ""
    private var imageUri: String? = ""
    lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentParams()
        ImagePickerActivity.clearCache(context)
        window = activity!!.window
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar(window)
    }


    private fun getIntentParams() {
        fullName = arguments?.getString(FULL_NAME_KEY)
        email = arguments?.getString(EMAIL_KEY)
        mobile = arguments?.getString(MOBILE_KEY)
        password = arguments?.getString(PASSWORD_KEY)
        imageUri = arguments?.getString(PROFILE_PIC_URI)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fourth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Update Profile"

        saveProfileButton.setOnClickListener {
            validateFields()
        }

        editProfileImageFab.setOnClickListener {
            requestCameraPermission()
        }
        dbHelper = RegisterUserDbHelper(context)
        populateFields()
        navController = Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)

    }

    private fun populateFields() {
        if (imageUri != "") {
            profileCircleImageView.setImageURI(Uri.parse(imageUri))
        } else {
            profileCircleImageView.setImageResource(R.drawable.avatar)
        }
        mobileTextView.text = mobile
        editTextName.setText(fullName)
        editTextEmail.setText(email)

    }

    private fun requestCameraPermission() {
        if (android.os.Build.VERSION.SDK_INT > 22) {
            Dexter.withActivity(activity as MainActivity)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions()
                        }

                        if (report.isAnyPermissionPermanentlyDenied) {
                            // showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }

                }).check()
        } else {
            showImagePickerOptions()
        }
    }

    private fun showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(
            context as MainActivity,
            object : ImagePickerActivity.PickerOptionListener {
                override fun onTakeCameraSelected() {
                    launchCameraIntent()
                }

                override fun onChooseGallerySelected() {
                    launchGalleryIntent()
                }

            })
    }

    private fun launchGalleryIntent() {
        Intent(context, ImagePickerActivity::class.java).apply {

            putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE)
            putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
            putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
            putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

            startActivityForResult(this, REQUEST_IMAGE)
        }
    }

    private fun launchCameraIntent() {
        Intent(context, ImagePickerActivity::class.java).apply {
            putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE)

            // setting aspect ratio
            putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
            putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
            putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

            // setting maximum bitmap width and height
            putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
            putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
            putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)

            startActivityForResult(this, REQUEST_IMAGE)
        }
    }


    private fun validateFields() {
        if (editTextName.isValidFullName(context!!) &&
            editTextEmail.isValidEmail(context!!) &&
            editTextPassword.isValidPassword(context!!)
        ) {
            fullName = editTextName.text.toString()
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()
            updateDetails()
        }
    }

    private fun updateDetails() {
        val db = dbHelper.writableDatabase
        val projection = arrayOf(
            BaseColumns._ID
        )
        val selection = "${UserContract.User.COLUMN_NAME_CONTACT} = ?"
        val selectionArgs = arrayOf(mobile)
        val sortOrder = "${BaseColumns._ID} ASC"

        val cursor = db.query(
            UserContract.User.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        cursor.moveToFirst()
        val rowid = cursor.getLong(cursor.getColumnIndex("_id"))

        val values = ContentValues().apply {
            put(UserContract.User.COLUMN_NAME_FULLNAME, fullName)
            put(UserContract.User.COLUMN_NAME_EMAIL, email)
            put(UserContract.User.COLUMN_NAME_PASSWORD, password)
            if (uri != null) {
                imageUri = uri.toString()
            }
            put(UserContract.User.COLUMN_PROFILE_PIC_URI, imageUri)
        }

        val updatedRowId = db.update(
            UserContract.User.TABLE_NAME,
            values,
            "_id = ?", arrayOf(rowid.toString())
        )
        syncSharedPref()
        Toast.makeText(context as MainActivity, "Details updated successfully", Toast.LENGTH_LONG).show()
        Log.d("UPDATED ROW ID", updatedRowId.toString())
        activity!!.hideKeyBoard()
        updateProfileListener.onProfileUpdated()
        navController.navigate(R.id.action_fourthFragment_to_homeFragment)
    }

    private fun syncSharedPref() {
        val sharedPref: SharedPreferences = activity!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putBoolean(PREF_NAME, true)
        editor.putString(FULL_NAME_KEY, fullName)
        editor.putString(EMAIL_KEY, email)
        editor.putString(PASSWORD_KEY, password)
        editor.putString(PROFILE_PIC_URI, imageUri.toString())
        editor.apply()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    uri = data.getParcelableExtra("path")
                    try {
                        //setImageView
                        profileCircleImageView.setImageURI(null)
                        profileCircleImageView.setImageURI(uri)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun setUpdateProfileListener(callback: UpdateProfileInterface) {
        this.updateProfileListener = callback
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UpdateProfileInterface) {
            updateProfileListener = context
        } else {
            throw RuntimeException("$context must implement FragmentEvent")
        }
    }
}
