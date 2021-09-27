package com.task.krishinetwork.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.task.krishinetwork.R
import com.task.krishinetwork.database.UserDatabase
import com.task.krishinetwork.databinding.ActivityMainBinding
import com.task.krishinetwork.ui.adapter.UserAdapter
import com.task.krishinetwork.ui.viewmodel.UserViewModel
import com.task.krishinetwork.utils.Constants
import com.task.krishinetwork.utils.DatabaseViewModelFactory
import com.task.krishinetwork.utils.showError

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val ERROR = "This field is required."
    private lateinit var viewModel: UserViewModel
    private var clickedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.white)

        viewModel = getUserViewModel()

    }



    override fun onStart() {
        super.onStart()

        binding.changePhoto.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePictureIntent, Constants.IMAGE_CAPTURE_CODE)
        }

        binding.submitButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val name = binding.nameEditText.text.toString().trim()

            val allFieldsAreValid = checkValidityOfLoginFields(email, name)

            if(allFieldsAreValid) {
                Log.d("image", clickedImage.toString())
                viewModel.addNewUser(name, email, clickedImage)
            }
        }

        binding.gotoNextscreen.setOnClickListener {
            startActivity(Intent(this, CropActivity::class.java))
        }

        viewModel.readAllUser.observe(this, {users->
            if(users.isEmpty()) {
                binding.emptyBannerImg.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyBannerImg.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                val adapter = UserAdapter(users)
                binding.recyclerView.adapter = adapter
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            clickedImage = data?.extras?.get("data") as Bitmap
            binding.imageEdit.load(clickedImage){
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun checkValidityOfLoginFields(email: String, name: String): Boolean {
        val isAllFieldsValid: Boolean

        if (email.isEmpty()) {
            isAllFieldsValid = false
            showError(binding.emailInputLayout, ERROR)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val message = "Email Invalid"
            showError(binding.emailInputLayout, message)
            isAllFieldsValid = false
        } else if (name.isEmpty()) {
            showError(binding.nameInputLayout, ERROR)
            isAllFieldsValid = false
        } else isAllFieldsValid = true

        return isAllFieldsValid

    }

    private fun getUserViewModel(): UserViewModel {
        val dataSource = UserDatabase.getDatabase(application).userDao()
        val viewModelFactory = DatabaseViewModelFactory(dataSource, application)
        return ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

}