package com.stilyan.shareit

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_account_settings.*

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    //declaration for cropping image
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null
    //private var ImageView img
    private var CODE_IMG_GALLERY = 1
    private var SAMPLED_CROPPED_IMG_NAME = "SampleCropImg"

    internal var image_selected_uri: Uri?=null
    internal var originalImage: Bitmap?=null
    internal lateinit var finalImage: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Profile Pictures")


        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this@AccountSettingsActivity, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        close_profile_btn.setOnClickListener {
            val intent = Intent(this@AccountSettingsActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}