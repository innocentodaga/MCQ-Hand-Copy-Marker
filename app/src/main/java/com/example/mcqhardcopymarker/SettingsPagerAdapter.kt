package com.example.mcqhardcopymarker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class SettingsPagerAdapter(fragment: FragmentActivity) : androidx.viewpager2.adapter.FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> GeneralSettingsFragment()
            1 -> AccountSettingsFragment()
            else -> AdvancedSettingsFragment()
        }
    }
}