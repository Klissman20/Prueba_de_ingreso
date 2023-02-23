package com.example.pruebadeingreso.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.R
import com.example.pruebadeingreso.UserDetailActivity
import com.example.pruebadeingreso.databinding.UserItemBinding
import com.example.pruebadeingreso.model.User

open class UserAdapter(
    private val mUsers: ArrayList<User>,
    listener: OnItemClickListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var mListener: OnItemClickListener? = null

    init{
        this.mListener = listener
    }

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

    interface OnItemClickListener {fun onItemClick(user: User)}

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemBinding.bind(itemView)
        fun bind(){
            val user: User = mUsers[adapterPosition]
            binding.userName.text = user.name
            binding.userPhone.text = user.phone
            binding.userEmail.text = user.email
            binding.pubBtn.setOnClickListener{
                val intent = Intent(itemView.context, UserDetailActivity::class.java).apply {
                    putExtra("userId", user.id)
                    putExtra("userName", user.name)
                    putExtra("userPhone", user.phone)
                    putExtra("userEmail",user.email)
                }
                itemView.context.startActivity(intent)
            }
            itemView.setOnClickListener {
                mListener?.onItemClick(user)
            }
        }
    }
}