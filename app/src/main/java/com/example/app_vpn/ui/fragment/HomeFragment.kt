package com.example.app_vpn.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.app_vpn.R
import com.example.app_vpn.data.preferences.UserPreference
import com.example.app_vpn.databinding.FragmentHomeBinding
import com.example.app_vpn.ui.MainActivity
import com.example.app_vpn.ui.pay.GetPremiumActivity
import com.example.app_vpn.ui.viewmodel.ButtonViewModel
import com.example.app_vpn.ui.viewmodel.UserViewModel
import com.example.app_vpn.util.GoogleMobileAdsConsentManager
import com.example.app_vpn.util.JwtUtils
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

const val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val buttonViewModel: ButtonViewModel by activityViewModels()
    private lateinit var userPreference: UserPreference
    private var jwtUtils = JwtUtils()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        userPreference = UserPreference(requireContext())

        binding.button.setOnClickListener {
            if (buttonViewModel.isRunning) {
                stopPulse()
                binding.button.setText(R.string.start)
            } else {
                startPulse()
                showInterstitial()
                binding.button.setText(R.string.stop)
            }
            buttonViewModel.isRunning = !buttonViewModel.isRunning
        }

        // Khôi phục trạng thái của nút khi Fragment được hiển thị lại
        if (buttonViewModel.isRunning) {
            startPulse()
            binding.button.setText(R.string.stop)
        } else {
            stopPulse()
            binding.button.setText(R.string.start)
        }

        // Xử lí sự kiện click vào vương miện
        binding.crownVip.setOnClickListener {
            val intent = Intent(requireContext(), GetPremiumActivity::class.java)
            startActivity(intent)
        }

        // Xử lí sự kiện click hiện navigation drawer
        binding.btnNavigation.setOnClickListener {
            // Lấy ra DrawerLayout từ Activity chứa Fragment
            val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.main_drawer_layout)

            // Mở DrawerLayout để hiển thị NavigationView
            drawerLayout.openDrawer(GravityCompat.START)

            // Xử lí sự kiện nhấn vào Rate Us
        }

        return view
    }

    private fun showInterstitial() {
        userPreference.premiumKey.asLiveData().observe(viewLifecycleOwner) {token ->
            if (activity != null && activity is MainActivity && jwtUtils.extractPremiumType(token!!) == "F") {
                (activity as MainActivity).showInterstitial()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopPulse() // Dừng Handler khi Fragment bị destroy
    }

    private var handlerAnimation = Handler()
    private var runnable = object : Runnable {
        override fun run() {
            binding.imgAnimation1.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000)
                .withEndAction {
                    binding.imgAnimation1.scaleX = 1f
                    binding.imgAnimation1.scaleY = 1f
                    binding.imgAnimation1.alpha = 1f
                }

            binding.imgAnimation2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(700)
                .withEndAction {
                    binding.imgAnimation2.scaleX = 1f
                    binding.imgAnimation2.scaleY = 1f
                    binding.imgAnimation2.alpha = 1f
                }

            handlerAnimation.postDelayed(this, 1500)
        }
    }

    private fun startPulse() {
        handlerAnimation.postDelayed(runnable, 0)
    }

    private fun stopPulse() {
        handlerAnimation.removeCallbacks(runnable)
    }

}