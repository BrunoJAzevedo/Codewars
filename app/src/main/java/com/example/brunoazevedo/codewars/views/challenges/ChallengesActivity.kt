package com.example.brunoazevedo.codewars.views.challenges

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.utils.userString
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.example.brunoazevedo.codewars.utils.challengeString
import com.example.brunoazevedo.codewars.views.challenges.completed.ChallengesCompletedFragment
import kotlinx.android.synthetic.main.challenges_fragment.*


class ChallengesFragment : Fragment() {

    companion object {
        private const val KEY: String = ""

        @JvmStatic
        fun newInstance(url: String) =
            ChallengesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, url)
                }
            }
    }

    private var _username : String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.challenges_fragment, container, false)

        _username = arguments?.getString(userString)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottom_navigation.setOnNavigationItemSelectedListener(_OnNavigationItemSelectedListener)
        bottom_navigation.selectedItemId = R.id.challenges_completed
    }

    private val _OnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.challenges_completed -> {
                    displayCompletedChallengesFragment()
                    return true
                }
                R.id.challenges_authored -> {
                    displayAuthoredChallengesFragment()
                    return true
                }
            }
            return false
        }
    }

    private fun displayCompletedChallengesFragment() {
        val completedChallengesFragment : Fragment = ChallengesCompletedFragment.newInstance(challengeString)
        val args = Bundle()
        args.putString(userString, _username)
        completedChallengesFragment.arguments = args

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.challenges_fragment_container, completedChallengesFragment,
            "completedChallengeString"
        )?.addToBackStack(null)?.commit()
    }

    private fun displayAuthoredChallengesFragment() {

    }
}