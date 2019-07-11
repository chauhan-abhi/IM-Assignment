package com.example.demo_day1.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demo_day1.R
import androidx.appcompat.app.AppCompatActivity


class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar!!.hide()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar!!.show()

    }

}
