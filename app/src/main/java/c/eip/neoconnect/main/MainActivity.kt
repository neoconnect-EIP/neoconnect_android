package c.eip.neoconnect.main

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import c.eip.neoconnect.R

class MainActivity : AppCompatActivity() {

    /**
     * Création Activity
     * Mise en place de la navigation Landing Page / Connexion / Inscription
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val host: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
                        ?: return

        val navController = host.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }
        }
    }
}
