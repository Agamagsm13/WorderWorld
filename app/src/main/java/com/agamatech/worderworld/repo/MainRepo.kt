package com.agamatech.worderworld.repo

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.net.ntp.NTPUDPClient
import org.apache.commons.net.ntp.TimeInfo
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection


@Singleton
class MainRepo @Inject constructor() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    val TIME_SERVER = "time-a.nist.gov"

    fun getTime() =
        scope.async {
            try {
                val timeClient = NTPUDPClient()
                val inetAddress = InetAddress.getByName(TIME_SERVER)
                val timeInfo: TimeInfo = timeClient.getTime(inetAddress)
                val returnTime: Long = timeInfo.message.transmitTimeStamp.time //server time
                val time = Date(returnTime)
                Log.e("getCurrentNetworkTime", "Time from $TIME_SERVER: $time")
                return@async time.toString()
            } catch (e: Exception) {
                return@async ("oooops")
            }
        }

    private fun getDate(time: Long): String {
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time * 1000
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        return dateFormat.format(cal.time)
    }
}