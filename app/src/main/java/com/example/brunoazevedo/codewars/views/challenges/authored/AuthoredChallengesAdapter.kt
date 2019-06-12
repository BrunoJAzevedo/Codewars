package com.example.brunoazevedo.codewars.views.challenges.authored

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.model.AuthoredChallengeData
import kotlinx.android.synthetic.main.item_challenge.view.*

class AuthoredChallengesAdapter(
    private var authoredChallenges : ArrayList<AuthoredChallengeData>,
    private val listener : OnItemClickListener
) : RecyclerView.Adapter<AuthoredChallengesAdapter.AuthoredChallengesViewHolder>()  {

    fun updateAuthoredChallenges(newUsers : List<AuthoredChallengeData>) {
        authoredChallenges.clear()
        authoredChallenges.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun getItemCount() = authoredChallenges.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = AuthoredChallengesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent,false)
    )

    override fun onBindViewHolder(holder: AuthoredChallengesViewHolder, position: Int) {
        holder.bind(authoredChallenges[position], listener)
    }

    class AuthoredChallengesViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val name = view.Challenge_Name

        fun bind(challenge : AuthoredChallengeData, listener : OnItemClickListener) {
            name.text = challenge.name

            itemView.setOnClickListener { listener.onItemClick(challenge) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(challenge : AuthoredChallengeData?)
    }
}