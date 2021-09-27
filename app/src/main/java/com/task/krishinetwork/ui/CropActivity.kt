package com.task.krishinetwork.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.task.krishinetwork.R
import com.task.krishinetwork.api.CropApi
import com.task.krishinetwork.api.model.Data
import com.task.krishinetwork.api.repo.CropRepo
import com.task.krishinetwork.databinding.ActivityCropBinding
import com.task.krishinetwork.ui.adapter.CropAdapter
import com.task.krishinetwork.ui.viewmodel.OnlineCropViewModel
import com.task.krishinetwork.utils.*
import com.task.krishinetwork.database.CropDatabase
import com.task.krishinetwork.database.entities.Crop
import com.task.krishinetwork.ui.viewmodel.OfflineCropViewModel


class CropActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCropBinding

    private lateinit var onlineViewModel: OnlineCropViewModel
    private var cropList = mutableListOf<Crop>()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var offlineCropViewModel: OfflineCropViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropBinding.inflate(layoutInflater)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.white)

        loadingDialog = LoadingDialog(this)

        offlineCropViewModel = getOfflineCropViewModel()
        onlineViewModel = getOnlineCropViewModel()

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.fetchButton.setOnClickListener {
            if(!isInternetAvailable(applicationContext)) {toast(this, "Connection Error") }
            else getCropDataFromApi()
        }

        offlineCropViewModel.readAllCrops.observe(this, {
            if(it == null) {
                getCropDataFromApi()
            } else {
                val adapter = CropAdapter(it.other_mandi)
                binding.recyclerView.adapter = adapter
            }
        })

    }

    private fun getCropDataFromApi() {
        loadingDialog.startLoading()
        onlineViewModel.getCropDataFromApi()
        onlineViewModel.cropList.observe(this, {response->
            if(loadingDialog.isLoading()) loadingDialog.stopLoading()
            when(response) {
                is ApiResponseHandler.Success -> {
                    val adapter = CropAdapter(response.value.data.other_mandi)
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.adapter = adapter
                    saveDataToDatabase(response.value.data)
                }

                is ApiResponseHandler.Failure -> toast(this, "Error")
            }
        })
    }

    private fun saveDataToDatabase(otherMandi: Data) {
        offlineCropViewModel.readAllCrops.removeObservers(this)
        offlineCropViewModel.readAllCrops.observe(this, {
            val crop = Crop(0, otherMandi.other_mandi)
            offlineCropViewModel.addCropData(crop)
        })
    }

    private fun getOfflineCropViewModel(): OfflineCropViewModel {
        val dataSource = CropDatabase.getDatabase(application).cropDao()
        val viewModelFactory = DatabaseViewModelFactory(dataSource, application)
        return ViewModelProvider(this, viewModelFactory).get(OfflineCropViewModel::class.java)
    }

    private fun getOnlineCropViewModel() : OnlineCropViewModel{
        val api = RetrofitInstance.buildApi(CropApi::class.java)
        val repo = CropRepo(api)
        val factory = ApiViewModelFactory(repo)
        return ViewModelProvider(this, factory).get(OnlineCropViewModel::class.java)
    }
}