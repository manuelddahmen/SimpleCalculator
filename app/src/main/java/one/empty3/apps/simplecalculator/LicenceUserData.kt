package one.empty3.apps.simplecalculator

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View

class LicenceUserData : AppCompatActivity() {
    private lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_licence_user_data)
        this.main = intent.extras?.get("class") as MainActivity
    }
    public fun backToMain() {
        val intent: Intent = Intent(this, MainActivity::class.java).apply {
        }
        startActivity(intent)
    }


    fun openLink(view: View) {
        val uri : Uri = Uri.parse(getString(R.string.POLICY_URI))
        val intent:Intent  = Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}