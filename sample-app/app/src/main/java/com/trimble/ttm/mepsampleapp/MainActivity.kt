/*
 * © 2022 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trimble.ttm.mepsampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by lazy { MainViewModel(application) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.ignition.observe(this) {
            binding.ignition.set(it)
        }

        model.speed.observe(this) {
            binding.speedometer.speedTo(it)
        }

        model.customerId.observe(this) {
            binding.cidView.text = "CID: ${it.value}"
        }

        model.userName.observe(this) {
            it.let {
                binding.userInfoView?.text = "Name: ${it.firstName} ${it.lastName}"
            }
        }

        model.trip.observe(this) {
            binding.trip.set(it)
        }

        model.latency.observe(this) {
            binding.latencyChart.set(it)
        }
    }
}