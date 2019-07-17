package com.example.demo_day1.fragments


import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.demo_day1.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.demo_day1.utils.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager

import kotlinx.android.synthetic.main.fragment_profile.*


class FirstFragment : Fragment() {


    lateinit var navController: NavController
    private lateinit var root: ConstraintLayout
    private lateinit var constraintLayout1: ConstraintSet
    private lateinit var constraintLayout2: ConstraintSet


    private var fullName: String? = ""
    private var email: String? = ""
    private var password: String? = ""
    private var mobile: String? = ""
    private var imageUri: String? = ""
    private lateinit var bundle: Bundle
    private var isOpen = false
    lateinit var window: Window


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentParams()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        window = activity!!.window
        hideStatusBar(window)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar!!.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        root = view.findViewById(R.id.constraint_layout_closed)
        navController = Navigation.findNavController(activity as Activity, R.id.nav_host_fragment)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_profile_fab.setOnClickListener {
            // open fourth fragment
            navController.navigate(R.id.fourthFragment, bundle)

        }
        setAnimation()
        populateFields()


    }

    private fun setAnimation() {
        constraintLayout1 = ConstraintSet()
        constraintLayout1.clone(root)
        constraintLayout2 = ConstraintSet()
        constraintLayout2.clone(context, R.layout.profile_expanded)

        profileCircleImageView.setOnClickListener {
            TransitionManager.beginDelayedTransition(constraint_layout_closed)
            val constraint = if (isOpen) constraintLayout1 else constraintLayout2
            constraint.applyTo(constraint_layout_closed)
            isOpen = !isOpen
        }
    }

    private fun populateFields() {
        if (imageUri != "") {
            profileCircleImageView.setImageURI(Uri.parse(imageUri))
        } else {
            profileCircleImageView.setImageResource(R.drawable.avatar)
        }
        fullNameTV.text = fullName
        mobileTextView.text = mobile

    }

    private fun getIntentParams() {
        fullName = arguments!!.getString(FULL_NAME_KEY)
        email = arguments!!.getString(EMAIL_KEY)
        mobile = arguments!!.getString(MOBILE_KEY)
        password = arguments!!.getString(PASSWORD_KEY)
        imageUri = arguments!!.getString(PROFILE_PIC_URI)
        bundle = bundleOf(
            FULL_NAME_KEY to fullName,
            EMAIL_KEY to email,
            MOBILE_KEY to mobile,
            PASSWORD_KEY to password,
            PROFILE_PIC_URI to imageUri
        )
    }


    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar!!.show()
        showStatusBar(window)


    }

}
