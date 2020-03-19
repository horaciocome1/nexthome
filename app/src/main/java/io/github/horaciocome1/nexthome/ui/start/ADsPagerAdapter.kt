package io.github.horaciocome1.nexthome.ui.start

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ADsPagerAdapter(
    fragmentManager: FragmentManager,
    private val pageTitles: ArrayList<CharSequence>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments: ArrayList<Fragment> = arrayListOf(
        RentingFragment(),
        SellingFragment(),
        SavedFragment()
    )

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = pageTitles[position]

}