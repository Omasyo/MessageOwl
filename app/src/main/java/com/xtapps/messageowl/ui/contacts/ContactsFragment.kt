package com.xtapps.messageowl.ui.contacts

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentContactsBinding
import com.xtapps.messageowl.models.ContactWithNumber
import com.xtapps.messageowl.ui.home.HomeFragmentDirections
import com.xtapps.messageowl.ui.room.RoomFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ContactsViewModel by activityViewModels {
        with((activity?.application as MessageOwlApplication).appDatabase) {
            ContactsViewModelFactory(userDao(), chatRoomDao())
        ***REMOVED***
    ***REMOVED***

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
        val result =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
***REMOVED***
        if (result == PackageManager.PERMISSION_GRANTED) {
            retrieveContacts()
        ***REMOVED*** else {
            requestPermission.launch(Manifest.permission.READ_CONTACTS)
        ***REMOVED***

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ContactsRecyclerViewAdapter({ image ->

            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setBackground(image)
                .show()

        ***REMOVED***) { contactId ->
            viewModel.getPrivateRoom(contactId).asLiveData().observe(viewLifecycleOwner) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToRoomFragment(it.id)
                findNavController().navigate(action)
            ***REMOVED***
        ***REMOVED***

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        recyclerView = binding.contactsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter



        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.contacts.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    adapter.submitList(it)
                ***REMOVED***
        ***REMOVED***

        return root
    ***REMOVED***

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
            if (success) {
                retrieveContacts()
            ***REMOVED***
        ***REMOVED***

    private fun retrieveContacts() {
        CoroutineScope(Dispatchers.IO).launch {
            val contacts = hashSetOf<ContactWithNumber>()

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
                while (phones.moveToNext()) {
                    val name: String =
                        phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phoneNumber: String =
                        phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    contacts.add(ContactWithNumber(name, formatPhone(phoneNumber)))
                ***REMOVED***
                Log.d("Contacts", "Size = ${phones.count***REMOVED*** new = ${contacts.size***REMOVED***")
                Log.d("Contacts", contacts.toString())
                phones.close()
            ***REMOVED***
            viewModel.submitContacts(contacts)
        ***REMOVED***
    ***REMOVED***

    private fun formatPhone(number: String): String {
        val phoneUtil = PhoneNumberUtil.getInstance()

        try {
            val numberProto = phoneUtil.parse(
                number,
                (requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
                    .networkCountryIso.uppercase()
***REMOVED***
            Log.d("Contacts", "formatPhone: before $number")
            val formatted =
                phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
            return formatted.replace(" ", "")
        ***REMOVED*** catch (e: NumberParseException) {
            System.err.println("NumberParseException was thrown: $number $e")

        ***REMOVED***
        Log.d("Contacts", "formatPhone: after $number")
        return "Invalid Number"
    ***REMOVED***

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    ***REMOVED***
***REMOVED***