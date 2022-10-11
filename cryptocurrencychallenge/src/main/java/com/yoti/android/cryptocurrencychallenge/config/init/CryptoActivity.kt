package com.yoti.android.cryptocurrencychallenge.config.init

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.yoti.android.cryptocurrencychallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoActivity : AppCompatActivity() {
    private val navController by lazy { binding.navHost.getFragment<NavHostFragment>().navController }
    private lateinit var binding: CryptoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CryptoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showToolbar()
    }

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()

    override fun onBackPressed() {
        if (!onSupportNavigateUp()) super.onBackPressed()
    }

    private fun showToolbar() {
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.assets))
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(this, navController, appBarConfiguration)
    }
}