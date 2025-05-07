package com.example.mcqhardcopymarker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mcqhardcopymarker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize your UI components here
        setupClickListeners()
        setupSwitches()
    }

    private fun setupClickListeners() {
        binding.btnEditProfile.setOnClickListener {
            // Handle edit profile
        }

        binding.layoutChangePassword.setOnClickListener {
            // Handle password change
        }

        binding.layoutTwoFactor.setOnClickListener {
            // Handle 2FA
        }

        binding.btnExportData.setOnClickListener {
            // Handle export
        }

        binding.btnImportData.setOnClickListener {
            // Handle import
        }

        binding.btnClearCache.setOnClickListener {
            // Clear cache
        }

        binding.btnDeleteData.setOnClickListener {
            // Delete data
        }

        binding.btnLogout.setOnClickListener {
            // Logout
        }
    }

    private fun setupSwitches() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // Handle theme change
        }

        binding.switchHighContrast.setOnCheckedChangeListener { _, isChecked ->
            // Handle high contrast
        }

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            // Handle notifications
        }

        binding.switchAutoScan.setOnCheckedChangeListener { _, isChecked ->
            // Handle auto-scan
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}