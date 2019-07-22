package com.example.demo_day1.ui.contactList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

    private lateinit var rvContact: RecyclerView
    private lateinit var progressBar: ProgressBar
    val contactList: ArrayList<Contact> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        rvContact = view.findViewById(R.id.rv_contact_list)
        progressBar = view.findViewById(R.id.progressBar)

        //addContacts()
        rvContact.layoutManager = LinearLayoutManager(context)
        rvContact.setHasFixedSize(true)
        rvContact.adapter = ContactListAdapter(R.layout.contact_list_item, contactList)
        (activity as MainActivity).supportActionBar?.title = "Contacts List"

        //(activity as AppCompatActivity).supportActionBar!!.hide()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Injector.appComponent.inject(this)

        contactListViewModel = ViewModelProviders.of(this, contactsViewModelFactory).get(
            ContactListViewModel::class.java
        )
        observeViewModelResults()
        fetchContactsFromViewModel()
    }


    private fun fetchContactsFromViewModel() {
        contactListViewModel.fetchContacts()
    }

    private fun observeViewModelResults() {
        contactListViewModel.contactsResult().observe(this, Observer<List<Contact>> {
            if (it != null) {
                ContactListAdapter(R.layout.contact_list_item, it).let { adapter ->
                    rvContact.adapter = adapter
                    adapter.setData(it)
                }
            }
        })

        contactListViewModel.contactsError().observe(this, Observer<String> {
            if (it != null) {
                showSnackBar(activity as FragmentActivity, "Some error")
            }
        })

        contactListViewModel.loadingVisibility().observe(this, Observer<Boolean> {
            if (it == false) progressBar.visibility = View.GONE else progressBar.visibility = View.VISIBLE
        })

    }

    /*  override fun onResume() {
          super.onResume()
          if (isNetworkStatusAvailable(context!!)) {
              FetchUserAsyncTask().execute()
          } else {
              progressBar.visibility = View.GONE
              //Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
              showSnackBar(activity as FragmentActivity, "No Internet Connection")
          }
      }*/


}
