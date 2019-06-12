package com.example.brunoazevedo.codewars.views.challenges.completed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.model.CompletedChallengeData
import com.example.brunoazevedo.codewars.utils.info_challenge
import com.example.brunoazevedo.codewars.utils.userString
import com.example.brunoazevedo.codewars.viewmodel.CompleteChallengesViewModel
import com.example.brunoazevedo.codewars.views.challenges.ChallengeInfoActivity
import com.example.brunoazevedo.codewars.views.challenges.ChallengesActivity
import kotlinx.android.synthetic.main.challenges_fragment.*

class ChallengesCompletedFragment : Fragment() {

    companion object {
        private const val KEY: String = ""

        @JvmStatic
        fun newInstance(url: String) =
            ChallengesCompletedFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, url)
                }
            }
    }

    private var _username : String? = ""

    private lateinit var _completedChallengesViewModel : CompleteChallengesViewModel
    private lateinit var _challengesCompletedAdapter : ChallengesCompletedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.challenges_fragment, container, false)

        _username = arguments?.getString(userString)

        _completedChallengesViewModel = activity?.run {
            ViewModelProviders.of(this).get(CompleteChallengesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        _completedChallengesViewModel.getCompletedChallengesPageInit(_username)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = object:ChallengesCompletedAdapter.OnItemClickListener {
            override fun onItemClick(challenge: CompletedChallengeData?) {
                val intent = Intent(context, ChallengeInfoActivity::class.java)
                if (!challenge?.id.isNullOrEmpty()) {
                    intent.putExtra(info_challenge, challenge?.id)
                } else {
                    intent.putExtra(info_challenge, challenge?.slug)
                }
                startActivity(intent)
            }
        }

        _challengesCompletedAdapter = ChallengesCompletedAdapter(arrayListOf(), listener)
        _challengesCompletedAdapter.setOnBottomReachedListener(object : OnBottomReachedListener {
            override fun onBottomReached() {
                _completedChallengesViewModel.getCompletedChallengesPage(_username)
            }
        })

        challenges_recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = _challengesCompletedAdapter
            itemAnimator = DefaultItemAnimator()
        }


        observeCompletedChallenges()
    }

    private fun observeCompletedChallenges() {
        _completedChallengesViewModel._challengesCompleted.observe(this, Observer { challenges ->
            challenges?.let {
                _challengesCompletedAdapter.updateCompletedChallenges(it) }
        })

        _completedChallengesViewModel._error.observe(this, Observer { isError ->
            isError?.let {
                if (it) {
                    Toast.makeText(context, context?.resources?.getText(R.string.error_text),
                        Toast.LENGTH_LONG).show()
                }
            }
        })

        _completedChallengesViewModel._loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                challenges_loading_view.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }
}