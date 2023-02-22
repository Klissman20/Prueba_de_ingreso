package com.example.pruebadeingreso.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.R
import com.example.pruebadeingreso.UserDetailActivity
import com.example.pruebadeingreso.databinding.UserItemBinding
import com.example.pruebadeingreso.model.User

open class UserAdapter(private val mUsers: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.user_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = UserItemBinding.bind(itemView)
        fun bind(){
            val user: User = mUsers[adapterPosition]
            binding.userName.text = user.name
            binding.userPhone.text = user.phone
            binding.userEmail.text = user.email
            binding.pubBtn.setOnClickListener{
                try {
                    val i = Intent(itemView.context, UserDetailActivity::class.java).apply {
                        putExtra("id", user.id)
                        putExtra("name", user.name)
                        putExtra("phone", user.phone)
                        putExtra("email", user.email)
                    }
                    itemView.context.startActivity(i)
                }catch (e: ActivityNotFoundException){
                    showAlert("Algo salió mal")
                }

            }
        }

        fun showAlert(msg: String) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Ups!")
            builder.setMessage(msg)
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

}