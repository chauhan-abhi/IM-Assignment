package com.example.demo_day1.fragments


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.TextView

import com.example.demo_day1.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.demo_day1.utils.*
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.mobileTextView
import kotlinx.android.synthetic.main.fragment_fourth.*


class FirstFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
    private val ALPHA_ANIMATIONS_DURATION = 200

    private var mIsTheTitleVisible = false
    private var mIsTheTitleContainerVisible = true

    private var mTitleContainer: LinearLayout? = null
    private var mTitle: TextView? = null
    lateinit var navController: NavController

    private var fullName: String? = ""
    private var email: String? = ""
    private var password: String? = ""
    private var mobile: String? = ""
    private var imageUri: String? = ""
    private lateinit var bundle: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar!!.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        navController = Navigation.findNavController(activity as Activity, R.id.nav_host_fragment)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_profile_fab.setOnClickListener {
            // open fourth fragment
            navController.navigate(R.id.fourthFragment, bundle)

        }
        populateFields()


    }

    private fun populateFields() {
        if (imageUri != "") {
            profileCircleImageView.setImageURI(Uri.parse(imageUri))
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


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val maxScroll = appBarLayout?.totalScrollRange
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll!!.toFloat()

        handleAlphaOnTitle(percentage)
        handleToolbarTitleVisibility(percentage)

    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle as View, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                mIsTheTitleVisible = true
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle as View, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
                mIsTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer as View, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
                mIsTheTitleContainerVisible = false
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer as View, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                mIsTheTitleContainerVisible = true
            }
        }
    }


    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE)
            AlphaAnimation(0f, 1f)
        else
            AlphaAnimation(1f, 0f)

        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }


    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar!!.show()

    }

}
