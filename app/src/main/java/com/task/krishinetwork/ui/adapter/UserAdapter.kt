package com.task.krishinetwork.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.loadAny
import coil.transform.CircleCropTransformation
import com.task.krishinetwork.R
import com.task.krishinetwork.database.entities.User
import com.task.krishinetwork.databinding.ViewUserBinding

class UserAdapter(private val list: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ViewUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {

                if(this.image == null) {
                    binding.userImg.load(R.drawable.dummy_man) {
                        transformations(CircleCropTransformation())
                    }
                }
                else {
                   binding.userImg.loadAny(this.image) {
                       transformations(CircleCropTransformation())
                   }
                }

                binding.userName.text = this.name
                binding.userEmail.text = this.email
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}