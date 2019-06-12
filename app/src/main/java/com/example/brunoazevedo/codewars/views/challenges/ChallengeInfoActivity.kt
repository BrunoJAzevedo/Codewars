package com.example.brunoazevedo.codewars.views.challenges

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.utils.info_challenge
import com.example.brunoazevedo.codewars.viewmodel.ChallengeInfoViewModel
import kotlinx.android.synthetic.main.activity_challenge_info.*
import kotlinx.android.synthetic.main.users_fragment.*

class ChallengeInfoActivity : AppCompatActivity() {

    val TAG = "ChallengeInfoActivity"

    private var _challengeInfo : String? = ""

    private lateinit var _challengeInfoViewModel: ChallengeInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_info)

        _challengeInfo = intent.getStringExtra(info_challenge)

        _challengeInfoViewModel = ViewModelProviders.of(this).get(ChallengeInfoViewModel::class.java)
        
        _challengeInfoViewModel.getChallengeInfo(_challengeInfo)

        observeChallengeInfo()
    }

    private fun observeChallengeInfo() {
        _challengeInfoViewModel._challenge.observe(this, Observer { challenge ->
            challenge_info_name.text = challenge?.name
            challenge_info_approvedby.text = challenge?.approvedBy?.username
            challenge_info_category.text = challenge?.category
            challenge_info_createdby.text = challenge?.createdBy?.username
            challenge_info_description.text = challenge?.description
            challenge_info_languages.text = challenge?.languages.toString()
            challenge_info_total_attempts.text = challenge?.totalAttempts.toString()
            challenge_info_total_completed.text = challenge?.totalCompleted.toString()
            challenge_info_total_stars.text = challenge?.totalStars.toString()
            challenge_info_total_tags.text = challenge?.tags.toString()
            challenge_info_url.text = challenge?.url
            challenge_info_vote_score.text = challenge?.voteScore.toString()
        })

        _challengeInfoViewModel._loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                challenge_info_loading_view.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }
}