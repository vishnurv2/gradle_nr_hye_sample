package com.example.dbdemoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.newrelic.agent.android.FeatureFlag
import com.newrelic.agent.android.NewRelic
import com.newrelic.agent.android.analytics.NetworkEventTransformer
import com.newrelic.agent.android.logging.AgentLog
import com.newrelic.agent.android.logging.AgentLogManager
import com.newrelic.agent.android.util.NetworkFailure
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Examples
//        matchReplaceStrings.put(/*match:*/ "^http(s{0,1}):\\/\\/(http).*/(\\d)\\d*", /*replace:*/null + "");
//        matchReplaceStrings.put(/*match:*/ "\\/api.(.*)\\.com/", /*replace:*/"\\/i-like-turtles.org\\/");
//        matchReplaceStrings.put(/*match:*/ "\\/square\\/", /*replace:*/ "\\/circle\\/");
//        matchReplaceStrings.put(/*match:*/ "(c2a7c39532239ff261be)", /*replace:*/ null + "");
//        matchReplaceStrings.put(/*match:*/ "5\\.25\\.0", /*replace:*/ "LATEST");

        var matchReplaceStrings: HashMap<String, String> = HashMap<String, String>();
        matchReplaceStrings.put("google.com", "test.go")
        matchReplaceStrings.put("reqres.in", "activity2.get")
        matchReplaceStrings.put("postman", "activity2.post")
        NewRelic.setEventListener(NetworkEventTransformer(matchReplaceStrings))

        NewRelic.enableFeature(FeatureFlag.NativeReporting)
//        NewRelic.enableFeature(FeatureFlag.FedRampEnabled)
//        NewRelic.withApplicationToken("AA8f0d153e428310876eac4255ba2abf6f75575c78-NRMA")
        NewRelic.withApplicationToken("AA47299f5f05f37319adab84290101a7d5b953cd86-NRMA")
            .withLogLevel(AgentLog.DEBUG)
            .start(this)

        var button = findViewById(R.id.button) as Button
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent);
        }

        var shutdonwButton = findViewById(R.id.shutdown) as Button
        shutdonwButton.setOnClickListener {
            NewRelic.shutdown();
        }

        var restartButton = findViewById(R.id.restart) as Button
        restartButton.setOnClickListener {
            var test = NewRelic.isStarted();
            if (test) {
                System.out.println("Agent already started");
            } else {
//                NewRelic.withApplicationToken("AA8f0d153e428310876eac4255ba2abf6f75575c78-NRMA")
                NewRelic.withApplicationToken("AA47299f5f05f37319adab84290101a7d5b953cd86-NRMA")
                    .withLogLevel(AgentLog.DEBUG)
                    .start(this)
            }
        }

        NewRelic.noticeNetworkFailure(
            "http://test.com",
            "GET",
            10000,
            20000,
            NetworkFailure.Unknown,
            "NetworkFailureTest"
        );
        NewRelic.recordMetric("testName", "testCategory", 0, 12.0, 0.0, null, null)
    }
}