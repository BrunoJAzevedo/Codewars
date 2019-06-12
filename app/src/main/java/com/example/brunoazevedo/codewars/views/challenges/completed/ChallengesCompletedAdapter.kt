package com.example.brunoazevedo.codewars.views.challenges.completed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.model.CompletedChallengeData
import kotlinx.android.synthetic.main.item_completed_challenges.view.*

class ChallengesCompletedAdapter(
    private var _completedChallenges : ArrayList<CompletedChallengeData>,
    private val _listener : OnItemClickListener) :
    RecyclerView.Adapter<ChallengesCompletedAdapter.CompletedChallengesViewHolder>() {

    private lateinit var _onBottomReachedListener : OnBottomReachedListener

    fun updateCompletedChallenges(newUsers : List<CompletedChallengeData>) {
        _completedChallenges.clear()
        _completedChallenges.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _completedChallenges.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CompletedChallengesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_completed_challenges, parent,false)
    )

    override fun onBindViewHolder(holder: CompletedChallengesViewHolder, position: Int) {
        holder.bind(_completedChallenges[position], _listener)
        if (position == _completedChallenges.size - 1){

            _onBottomReachedListener.onBottomReached()

        }
    }

    fun setOnBottomReachedListener(listener: OnBottomReachedListener) {
        _onBottomReachedListener = listener
    }

    class CompletedChallengesViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val name = view.Challenge_Name
        private val id = view.Challenge_ID
        private val slug = view.Challenge_Slug
        private val laguages = view.Challenge_Languages
        private val completedAt = view.Challenge_CompletedAt

        fun bind(challenge : CompletedChallengeData, listener : OnItemClickListener) {
            name.text = challenge.name
            id.text = challenge.id
            slug.text = challenge.slug
            laguages.text = challenge.languages.toString()
            completedAt.text = challenge.completedAt

            itemView.setOnClickListener { listener.onItemClick(challenge) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(challenge : CompletedChallengeData?)
    }
}