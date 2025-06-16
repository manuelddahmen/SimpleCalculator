package one.empty3.apps.simplecalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var textViewPrivacyLink: TextView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy) // Ensure this matches your layout file name

        title = getString(R.string.title_activity_privacy_policy)

        webView = findViewById(R.id.webViewPrivacy)
        textViewPrivacyLink = findViewById(R.id.textViewPrivacyLink) // If using it

        // Configure WebView
        webView.webViewClient = WebViewClient() // Ensures links open within the WebView
        webView.settings.javaScriptEnabled = true // Enable JavaScript if needed

        // Load the updated privacy policy URL
        val privacyPolicyUrl = "https://empty3.app/android/privacy.html" // <<< UPDATED URL
        webView.loadUrl(privacyPolicyUrl)

        // The TextView defined in XML with autoLink="web" will handle the link automatically.
        // If you were setting it programmatically, you'd update the URL here too.
    }

    // Handle back press within WebView
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}