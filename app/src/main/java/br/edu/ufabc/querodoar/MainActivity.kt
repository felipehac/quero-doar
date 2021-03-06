package br.edu.ufabc.querodoar

import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.edu.ufabc.querodoar.databinding.ActivityMainBinding
import br.edu.ufabc.querodoar.ui.notifications.NotificationItemFragment
import br.edu.ufabc.querodoar.ui.notifications.NotificationsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val notificationViewModel: NotificationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.my_tool_bar)
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_donation, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bindNotificationEvents()
    }

    private fun bindNotificationEvents() {
        notificationViewModel.clickedItemId.observe(this) {
            it?. let {
                supportFragmentManager.commit{
                    NotificationItemFragment().apply {
                        binding.navHostFragmentActivityMain.let { container ->
                            replace(container.id, this)
                            addToBackStack(null)
                        }
                    }
                }
            }
        }
    }

}