package com.example.demo_day1.ui.contactList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_day1.R
import com.example.demo_day1.activities.MainActivity
import com.example.demo_day1.adapters.ContactListAdapter
import com.example.demo_day1.data.remote.model.Contact
import com.example.demo_day1.di.Injector
import com.example.demo_day1.utils.showSnackBar
import javax.inject.Inject

class ContactListFragment : Fragment() {

    @Inject
    lateinit var contactsViewModelFactory: ContactsViewModelFactory
    lateinit var contactListViewModel: ContactListViewModel
    private lateinit var binding: com.example.demo_day1.databinding.FragmentSecondBinding

    val contactList: ArrayList<Contact> = ArrayList()
    private var contactAdapter = ContactListAdapter(R.layout.contact_list_item, contactList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Injector.appComponent.inject(this)

        contactListViewModel = ViewModelProviders.of(this, contactsViewModelFactory).get(
            ContactListViewModel::class.java
        )
        binding.rvContactList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvContactList.setHasFixedSize(true)
        binding.rvContactList.adapter = contactAdapter
        observeViewModelResults()
        binding.viewModel = contactListViewModel
        fetchContactsFromViewModel()
    }


    private fun fetchContactsFromViewModel() {
        contactListViewModel.fetchContacts()
    }

    private fun observeViewModelResults() {
        contactListViewModel.contactsResult().observe(this, Observer<List<Contact>> {
            if (!it.isNullOrEmpty()) {
                contactAdapter.setData(it)
            }
        })

        contactListViewModel.contactsError().observe(this, Observer<String> {
            if (!it.isNullOrEmpty()) {
                showSnackBar(activity as FragmentActivity, R.string.contacts_error.toString())
            }
        })

    }
}
