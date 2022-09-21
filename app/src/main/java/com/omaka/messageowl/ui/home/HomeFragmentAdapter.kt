import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.omaka.messageowl.ui.chats.ChatsFragment
import com.omaka.messageowl.ui.contacts.ContactsFragment

class HomeFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2 // FIXME: do not hardcode

    override fun createFragment(position: Int): Fragment {
        // FIXME: hardcoding
        val fragment = when(position) {
            0 -> ChatsFragment()
            else -> ContactsFragment()
        ***REMOVED***
        fragment.arguments = Bundle().apply {

        ***REMOVED***
        return fragment
    ***REMOVED***
***REMOVED***