package com.example.pruebadeingreso.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.R
import com.example.pruebadeingreso.UserDetail
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
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        var name_tv = itemView.findViewById<TextView>(R.id.user_name)
        var phone_tv = itemView.findViewById<TextView>(R.id.user_phone)
        var email_tv = itemView.findViewById<TextView>(R.id.user_email)
        var pub_btn = itemView.findViewById<Button>(R.id.pub_btn)

        fun bind(){
            val user: User = mUsers[adapterPosition]
            name_tv.text = user.name
            phone_tv.text = user.phone
            email_tv.text = user.email
            pub_btn.setOnClickListener{
                try {
                    val i = Intent(itemView.context, UserDetail::class.java).apply {
                        putExtra("name", user.getName())
                        putExtra("phone", user.getPhone())
                        putExtra("email", user.getEmail())
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