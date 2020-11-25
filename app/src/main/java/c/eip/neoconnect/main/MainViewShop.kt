package c.eip.neoconnect.main


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.repository.OffresRepository
import c.eip.neoconnect.utils.DataGetter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.runBlocking

class MainViewShop : AppCompatActivity() {
    private var notificationManager: NotificationManager? = null
    private var offresRepository = OffresRepository()

    /**
     * Creation Activity Shop.
     * Setup BottomNav
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main_view_shop)
        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_shop_fragment) as NavHostFragment?
                ?: return
        val navController = host.navController
        findViewById<BottomNavigationView>(R.id.nav_view).setupWithNavController(navController)
        notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        val id = "c.eip.neoconnect"
        val name = "Notification Marque"
        val description = "Notification pour une marque"
        createNotificationChannel(id, name, description)
    }

    override fun onStart() {
        super.onStart()
        val id = "c.eip.neoconnect"
        Handler().postDelayed({
            sendNotification(id)
        }, 2000)
    }

    private fun createNotificationChannel(id: String, name: String, description: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.enableVibration(false)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun sendNotification(channelId: String) {
        val token = DataGetter.INSTANCE.getToken(applicationContext)
        val id = DataGetter.INSTANCE.getUserId(applicationContext)

        runBlocking {
            val response = offresRepository.getMyOfferShop(token = token!!, id = id)
            if (response.isNotEmpty()) {
                var i = 0
                response.forEach { element ->
                    element.apply.forEach { user ->
                        if (user.status == "pending")
                            i++
                    }
                }
                val cntrText = "Vous avez $i candidatures en attente"
                val notification = Notification.Builder(applicationContext, channelId)
                    .setContentTitle("Nouvelles candidatures en attente")
                    .setContentText(cntrText)
                    .setSmallIcon(R.drawable.logo).setChannelId(channelId).build()
                notificationManager?.notify(101, notification)
            }
        }
    }
}
