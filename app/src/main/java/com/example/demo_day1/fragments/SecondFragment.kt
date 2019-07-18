package com.example.demo_day1.fragments


import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_day1.R
import com.example.demo_day1.adapters.ContactListAdapter
import com.example.demo_day1.data.remote.model.Contact
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.demo_day1.activities.MainActivity
import com.example.demo_day1.utils.REMOTE_URL
import com.example.demo_day1.utils.isNetworkStatusAvailable
import com.example.demo_day1.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.Buffer


class SecondFragment : Fragment() {


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
        rvContact.adapter = ContactListAdapter(contactList)
        (activity as MainActivity).supportActionBar?.title = "Contacts List"

        //(activity as AppCompatActivity).supportActionBar!!.hide()

        return view
    }

    override fun onResume() {
        super.onResume()
        if (isNetworkStatusAvailable(context!!)) {
            FetchUserAsyncTask().execute()
        } else {
            progressBar.visibility = View.GONE
            //Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
            showSnackBar(activity as FragmentActivity, "No Internet Connection")
        }
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

    inner class FetchUserAsyncTask : AsyncTask<Void, Void, ArrayList<Contact>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
            rvContact.visibility = View.GONE
        }

        override fun onPostExecute(result: ArrayList<Contact>) {
            super.onPostExecute(result)
            progressBar.visibility = View.GONE
            rvContact.visibility = View.VISIBLE
            rvContact.adapter = ContactListAdapter(result)
        }

        override fun doInBackground(vararg url: Void): ArrayList<Contact> {
            var urlConnection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            var userResponse: String

            try {
                urlConnection =
                    URL("http://www.mocky.io/v2/5d2c25c53100000e00f5a68e").openConnection() as HttpURLConnection

                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty("Content-Type", "application/json")
                urlConnection.setRequestProperty("Accept", "application/json")
                urlConnection.connect()

                val inString = streamToString(urlConnection.inputStream)
                return parseUserResponse(inString)

            } catch (e: IOException) {
                Log.e("IOERROR", e.message + "")
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }
            return contactList
        }
    }

    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }

        return result
    }

    private fun parseUserResponse(userResponse: String): ArrayList<Contact> {
        val jsonArray = JSONArray(JSONObject(userResponse).getString("results"))
        for (i in 0 until jsonArray.length()) {
            val user = jsonArray[i] as JSONObject
            contactList.add(
                Contact(
                    user.getString("personName"),
                    user.getString("personEmail"),
                    user.getLong("contactNumber").toString(),
                    "https://avatars1.githubusercontent.com/u/20797673?s=460&v=4"
                )
            )
        }
        return contactList
    }
}
