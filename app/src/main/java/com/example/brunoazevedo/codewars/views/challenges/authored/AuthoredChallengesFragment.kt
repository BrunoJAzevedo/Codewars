package com.example.brunoazevedo.codewars.views.challenges.authored

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.model.AuthoredChallengeData
import com.example.brunoazevedo.codewars.utils.INFO_CHALLENGE
import com.example.brunoazevedo.codewars.utils.USER_STRING
import com.example.brunoazevedo.codewars.viewmodel.AuthoredChallengesViewModel
import com.example.brunoazevedo.codewars.views.challenges.ChallengeInfoActivity
import kotlinx.android.synthetic.main.challenges_fragment.*

class AuthoredChallengesFragment : Fragment() {

    companion object {
        private const val KEY: String = ""

        @JvmStatic
        fun newInstance(url: String) =
            AuthoredChallengesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, url)
                }
            }
    }

    private var _username : String? = ""
    lateinit var _authoredChallengesViewModel : AuthoredChallengesViewModel
    private lateinit var _authoredChallengesAdapter : AuthoredChallengesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //reusing fragment layout
        val rootView = inflater.inflate(R.layout.challenges_fragment, container, false)

        _username = arguments?.getString(USER_STRING)

        _authoredChallengesViewModel = activity?.run {
            ViewModelProviders.of(this).get(AuthoredChallengesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        _authoredChallengesViewModel.getAuthoredChallenges(_username)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = object:AuthoredChallengesAdapter.OnItemClickListener {
            override fun onItemClick(challenge: AuthoredChallengeData?) {
                val intent = Intent(context, ChallengeInfoActivity::class.java)
                intent.putExtra(INFO_CHALLENGE, challenge?.id)
                startActivity(intent)
            }
        }

        _authoredChallengesAdapter = AuthoredChallengesAdapter(arrayListOf(), listener)

        val linearLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            context, linearLayoutManager.orientation
        )

        challenges_recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = _authoredChallengesAdapter
            itemAnimator = DefaultItemAnimator()
        }
        challenges_recyclerView.addItemDecoration(dividerItemDecoration)

        observeAuthoredChallenges()
    }

    private fun observeAuthoredChallenges() {
        _authoredChallengesViewModel.authoredChallenges.observe(this, Observer { challenges ->
            challenges?.let {
                if (challenges.isEmpty()) {
                    Toast.makeText(context, R.string.no_authored_challenges, Toast.LENGTH_LONG).show()
                } else _authoredChallengesAdapter.updateAuthoredChallenges(it)
            }
        })

        _authoredChallengesViewModel.loadError.observe(this, Observer { isError ->
            isError?.let {
                if (it) {
                    Toast.makeText(context, _authoredChallengesViewModel.errorMessage,
                        Toast.LENGTH_LONG).show()
                }
            }
        })

        _authoredChallengesViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                challenges_loading_view.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }
}