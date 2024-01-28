package com.ayan.nexxiotaskapp

import com.ayan.nexxiotaskapp.viewmodels.MainViewModel
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayan.nexxiotaskapp.adapters.ListAdapter
import com.ayan.nexxiotaskapp.databinding.ActivityMainBinding
import com.ayan.nexxiotaskapp.interfaces.ListClickListener
import com.ayan.nexxiotaskapp.models.Response

class MainActivity : AppCompatActivity(), ListClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var response: Response
    private val REQUEST_IMAGE_CAPTURE = 1
    private var selectedImagePosition: Int? = null
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val LOG = "Nexxio_APP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the activity full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        viewModel.initializeJsonData(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        viewModel.response.observe(this) {
            response = it
            binding.rcv.layoutManager = LinearLayoutManager(applicationContext)
            binding.rcv.adapter = ListAdapter(it, this)
        }
    }

    override fun radioButtonCLicked(position: Int, text: String) {
        (binding.rcv.adapter as ListAdapter).updateSelectedRadioBtn(position, text)
    }

    override fun imageItemClicked(position: Int, type: Int) {
        when (type) {
            1 -> { // openCamera
                selectedImagePosition = position
                requestCameraPermission()
            }
            2 -> { //Removed img & notify
                (binding.rcv.adapter as ListAdapter).notifyItemChanged(position)
            }
        }

    }

    override fun commentItemInteracted(position: Int, comment: String?, isCommentBoxVisible: Boolean) {
        (binding.rcv.adapter as ListAdapter).updateCommentItem(
            position,
            isCommentBoxVisible,
            comment
        )
    }


    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, open the camera
            openCamera()
        } else {
            // Request camera permission from the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Handle the captured image
            val imageBitmap = data?.extras?.get("data") as Bitmap
            (binding.rcv.adapter as ListAdapter).updatePhoto(selectedImagePosition!!, imageBitmap)
        }
        selectedImagePosition = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted, open the camera
                    openCamera()
                } else {
                    selectedImagePosition = null
                    Toast.makeText(
                        this,
                        "Camera permission required to take photos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_submit -> {
                // Handle the "submit" button click
                onSubmitButtonClick()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onSubmitButtonClick() {
        // Log data of all items in the RecyclerView along with current user input
        val updatedList = (binding.rcv.adapter as ListAdapter).getUpdatedList()
        for (item in updatedList) {
            Log.d(LOG, "Item ID: ${item.id}, Text: ${item.comment}, option: ${item.selectedRadioButtonText}")
        }
    }

}