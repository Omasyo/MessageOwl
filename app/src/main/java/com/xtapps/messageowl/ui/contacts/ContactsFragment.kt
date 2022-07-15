package com.xtapps.messageowl.ui.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.Contacts
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialSharedAxis
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val contacts = hashMapOf<String, String>()

        val result =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
***REMOVED***
        if (result == PackageManager.PERMISSION_GRANTED) {
            val phones = requireContext().contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
    ***REMOVED***,
                null,
                null,
                null
***REMOVED***
            if (phones != null) {
                Log.d("Contacts", phones.columnNames.toList().toString())
                while (phones.moveToNext()) {
                    val name: String =
                        phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phoneNumber: String =
                        phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    Log.d("Contacts", "Name: $name, Phone: $phoneNumber")
                    contacts[name] = phoneNumber.replace(" ","")
                ***REMOVED***
                Log.d("Contacts", "Size = ${phones.count***REMOVED*** new = ${contacts.size***REMOVED***")
                Log.d("Contacts", contacts.toString())
                phones.close()
            ***REMOVED***
        ***REMOVED*** else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_CONTACTS
    ***REMOVED***, 2
***REMOVED***
        ***REMOVED***

        val viewModel =
            ViewModelProvider(this).get(ContactsViewModel::class.java)

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pageName = getString(R.string.contacts)
        val textView: TextView = binding.textDashboard
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = pageName
        ***REMOVED***
        return root
    ***REMOVED***

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    ***REMOVED***
***REMOVED***