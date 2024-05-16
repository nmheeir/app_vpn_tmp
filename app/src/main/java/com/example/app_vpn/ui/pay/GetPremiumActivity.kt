package com.example.app_vpn.ui.pay

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_vpn.data.local.Subcription
import com.example.app_vpn.databinding.ActivityPremiumBinding
import com.example.app_vpn.ui.custom.CustomBenefitAdapter
import com.example.app_vpn.ui.custom.CustomSubcriptionAdapter
import com.example.app_vpn.util.enable
import com.example.app_vpn.util.listBenefit
import com.example.app_vpn.util.listSubcription
import com.example.app_vpn.util.toast


class GetPremiumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPremiumBinding

    private lateinit var customBenefitAdapter: CustomBenefitAdapter
    private lateinit var customSubcriptionAdapter: CustomSubcriptionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPremiumBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.btnSubcription.enable(false)

        // Quyền lợi
        customBenefitAdapter = CustomBenefitAdapter(this, listBenefit)

        // Thiết lập ListView để sử dụng CustomBenefitAdapter
        binding.listViewBenefits.adapter = customBenefitAdapter

        // Tắt activity
        binding.btnexit.setOnClickListener {
            finish()
        }

        // Chọn gói
        customSubcriptionAdapter = CustomSubcriptionAdapter(listSubcription) { position ->
            binding.btnSubcription.enable(true)

            binding.btnSubcription.setOnClickListener {
                subcriptionPremium(listSubcription[position])
            }
        }

        binding.listViewSubcription.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listViewSubcription.adapter = customSubcriptionAdapter
    }

    private fun subcriptionPremium(subcription: Subcription) {
        val bundle = Bundle().apply {
            putString("amount", subcription.price)
        }
        val intent = Intent(this, PaymentVipActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }
}