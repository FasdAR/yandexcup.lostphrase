package ru.fasdev.yandexlostphrase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.children

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<LinearLayout>(R.id.layout_m).children.forEachIndexed { index, view ->
            view.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java).putExtra(MainActivity.path_f, "${index}.txt"))
            }
        }
    }
}