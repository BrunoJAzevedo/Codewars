package com.example.brunoazevedo.codewars.views.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(private var users : ArrayList<User>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>()  {

    fun updateUsers(newUsers : List<User>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = UsersViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,false)
    )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    class UsersViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val name = view.Name
        private val rank = view.Rank
        private val laguage = view.Language
        private val points = view.Points

        fun bind(user : User) {
            name.text = user.username
            val rankPosition = user.leaderboardPosition
            rank.text = "Rank: $rankPosition"
            laguage.text = "Language"
            points.text = "Points"
        }
    }
}