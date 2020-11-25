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

class MainViewInf : AppCompatActivity() {
    private var notificationManager: NotificationManager? = null
    private var offerRepository = OffresRepository()

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
        notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        val id = "c.eip.neoconnect"
        val name = "Notification Influenceur"
        val description = "Notification pour une influenceur"
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

        runBlocking {
            val response = offerRepository.getAllOffers(
                token = token!!,
                color = null,
                brand = null,
                subject = null,
                sex = null,
                order = null,
                popularity = null
            )
            if (response.isNotEmpty()) {
                var nbOffers = 0
                response.forEach { element ->
                    if (element.status != "accepted" || element.status != "refused" || element.status != "pending") {
                        nbOffers += 1
                    }
                }
                val cntrText = "$nbOffers nouvelles offres ont été postés"
                val notification = Notification.Builder(applicationContext, channelId)
                    .setContentTitle("Nouvelles offres").setContentText(cntrText)
                    .setSmallIcon(R.drawable.logo).setChannelId(channelId).build()
                notificationManager?.notify(101, notification)
            } else {
            }
        }
    }
}
