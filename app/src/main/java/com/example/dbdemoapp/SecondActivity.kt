package com.example.dbdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.newrelic.agent.android.NewRelic
import com.newrelic.agent.android.analytics.NetworkEventTransformer
import com.newrelic.agent.android.logging.AgentLog
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //NR-43437 - If the following is implemented onCreate, everything would be fine
//        Thread{
//            getHttpRequest()
//            postHttpRequest()
//        }.start()
    }

    override fun onResume() {
        super.onResume()
        //NR-43437 - If the following is implemented in onResume, eventListener is not triggered
//        Thread{
//            getHttpRequest()
//            postHttpRequest()
//        }.start()
    }

    fun getHttpRequest(){
        val url = URL("https://reqres.in/api/users?page=1")
        val connection = url.openConnection()
        BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
            var line: String?
            while (inp.readLine().also { line = it } != null) {
                println(line)
            }
        }
    }

    fun postHttpRequest(){
        val url = URL("https://postman-echo.com/post")
        val postData = "foo1=bar1&foo2=bar2"

        val conn = url.openConnection()
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.setRequestProperty("Content-Length", postData.length.toString())

        DataOutputStream(conn.getOutputStream()).use { it.writeBytes(postData) }
        BufferedReader(InputStreamReader(conn.getInputStream())).use { bf ->
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                println(line)
            }
        }
    }
}