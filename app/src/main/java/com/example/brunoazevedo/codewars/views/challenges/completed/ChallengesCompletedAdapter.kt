package com.example.brunoazevedo.codewars.views.challenges.completed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.model.CompletedChallengeData
import kotlinx.android.synthetic.main.item_challenge.view.*

class ChallengesCompletedAdapter(
    private var _completedChallenges : ArrayList<CompletedChallengeData>,
    private val _listener : OnItemClickListener
) : RecyclerView.Adapter<ChallengesCompletedAdapter.CompletedChallengesViewHolder>() {

    private lateinit var _onBottomReachedListener : OnBottomReachedListener

    fun updateCompletedChallenges(newUsers : List<CompletedChallengeData>) {
        _completedChallenges.clear()
        _completedChallenges.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _completedChallenges.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CompletedChallengesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent,false)
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

        fun bind(challenge : CompletedChallengeData, listener : OnItemClickListener) {
            name.text = challenge.name
            itemView.setOnClickListener { listener.onItemClick(challenge) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(challenge : CompletedChallengeData?)
    }
}