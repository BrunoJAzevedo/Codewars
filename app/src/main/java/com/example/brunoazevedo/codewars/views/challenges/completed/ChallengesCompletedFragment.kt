package com.example.brunoazevedo.codewars.views.challenges.completed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brunoazevedo.codewars.R
import com.example.brunoazevedo.codewars.views.users.UsersFragment

class ChallengesCompletedFragment : Fragment() {

    companion object {
        private const val KEY: String = ""

        @JvmStatic
        fun newInstance(url: String) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, url)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.challenges_completed_fragment, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}