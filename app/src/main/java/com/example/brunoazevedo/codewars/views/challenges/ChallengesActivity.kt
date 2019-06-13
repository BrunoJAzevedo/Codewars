package com.example.brunoazevedo.codewars.views.challenges

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.utils.USER_STRING
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.brunoazevedo.codewars.utils.AUTHORED_CHALLENGES_STRING
import com.example.brunoazevedo.codewars.utils.COMPLETED_CHALLENGES_STRING
import com.example.brunoazevedo.codewars.views.challenges.authored.AuthoredChallengesFragment
import com.example.brunoazevedo.codewars.views.challenges.completed.ChallengesCompletedFragment
import kotlinx.android.synthetic.main.activity_challenge.*


class ChallengesActivity : AppCompatActivity() {

    private var username : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        username = intent.getStringExtra(USER_STRING)
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
        val completedChallengesFragment : Fragment = ChallengesCompletedFragment.newInstance(COMPLETED_CHALLENGES_STRING)
        val args = Bundle()
        args.putString(USER_STRING, username)
        completedChallengesFragment.arguments = args

        supportFragmentManager?.beginTransaction()?.replace(R.id.challenges_fragment_container, completedChallengesFragment,
            COMPLETED_CHALLENGES_STRING)?.commit()
    }

    private fun displayAuthoredChallengesFragment() {
        val authoredChallengesFragment : Fragment = AuthoredChallengesFragment.newInstance(AUTHORED_CHALLENGES_STRING)
        val args = Bundle()
        args.putString(USER_STRING, username)
        authoredChallengesFragment.arguments = args

        supportFragmentManager?.beginTransaction()?.replace(R.id.challenges_fragment_container, authoredChallengesFragment,
            AUTHORED_CHALLENGES_STRING)?.commit()
    }
}