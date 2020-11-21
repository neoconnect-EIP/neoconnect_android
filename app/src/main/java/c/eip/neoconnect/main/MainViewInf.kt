package c.eip.neoconnect.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import c.eip.neoconnect.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewInf : AppCompatActivity() {
    /**
     * Creation Activity Inf.
     * Setup BottomNav
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main_view_inf)
        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_inf_fragment) as NavHostFragment?
                ?: return
        val navController = host.navController
        findViewById<BottomNavigationView>(R.id.nav_view).setupWithNavController(navController)
    }
}
