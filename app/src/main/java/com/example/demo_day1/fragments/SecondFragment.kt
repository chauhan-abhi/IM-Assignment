package com.example.demo_day1.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_day1.R
import com.example.demo_day1.adapters.ContactListAdapter
import com.example.demo_day1.model.Contact

class SecondFragment : Fragment() {


    private lateinit var rvContact: RecyclerView
    val contactList: ArrayList<Contact> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        rvContact = view.findViewById(R.id.rv_contact_list)

        addContacts()
        rvContact.layoutManager = LinearLayoutManager(context)
        rvContact.adapter = ContactListAdapter(contactList)

        return view
    }

    private fun addContacts() {
        //val contactsJSON = readJSONFromAsset(activity!!.applicationContext)

        for (i in 1..9) {
            contactList.add(
                Contact(
                    "AAAA$i",
                    "aaaa@aaaa$i.com",
                    "999064924$i",
                    "https://avatars1.githubusercontent.com/u/20797673?s=460&v=4"
                )
            )
        }
    }


}
