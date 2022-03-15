package com.example.mednow.enfermeiro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mednow.R
import com.example.mednow.model.Enfermeiro

class UsersRecyclerAdapter (private val listEnfermeiros: List<Enfermeiro>) : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_recycler, parent, false)
        return UserViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.textViewName.text = listEnfermeiros[position].name
        holder.textViewEmail.text = listEnfermeiros[position].email
        holder.textViewCoren.text = listEnfermeiros[position].coren
        holder.textViewPassword.text = listEnfermeiros[position].password
    }
    override fun getItemCount(): Int {
        return listEnfermeiros.size
    }
    /**
     * ViewHolder class
     */
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: AppCompatTextView
        var textViewEmail: AppCompatTextView
        var textViewCoren: AppCompatTextView
        var textViewPassword: AppCompatTextView

        init {
            textViewName = view.findViewById(R.id.textViewName) as AppCompatTextView
            textViewEmail = view.findViewById(R.id.textViewEmail) as AppCompatTextView
            textViewCoren = view.findViewById(R.id.textViewCoren) as AppCompatTextView
            textViewPassword = view.findViewById(R.id.textViewPassword) as AppCompatTextView
        }
    }
}