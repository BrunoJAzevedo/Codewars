package com.example.brunoazevedo.codewars.views.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.utils.languagePoints
import com.example.brunoazevedo.codewars.utils.userBestLanguage
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(private var users : ArrayList<User>, private val listener : OnItemClickListener) :
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
        holder.bind(users[position], listener)
    }

    class UsersViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val name = view.Name
        private val rank = view.Rank
        private val laguage = view.Language
        private val points = view.Points

        fun bind(user : User, listener : OnItemClickListener) {
            name.text = user.username
            val rankPosition = user.leaderboardPosition
            rank.text = "Rank: $rankPosition"
            val bestLanguage = userBestLanguage(user.ranks.languages)
            laguage.text = "Best Language: $bestLanguage"
            val bestLanguagePoints = languagePoints(user.ranks.languages.get(bestLanguage))
            points.text = "Best Language Points: $bestLanguagePoints"

            itemView.setOnClickListener { listener.onItemClick(user) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user : User?)
    }
}