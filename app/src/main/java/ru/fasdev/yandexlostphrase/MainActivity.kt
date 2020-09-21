package ru.fasdev.yandexlostphrase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.fasdev.yandexlostphrase.manager.PseudoCodeManager
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity()
{
    companion object {
        const val path_f = "path_f"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val path: String = intent.getStringExtra(path_f) ?: ""

        val phraseView = findViewById<LostPhraseView>(R.id.phrase_view)
        val pseudoCodeManager: PseudoCodeManager = PseudoCodeManager()
        val phrase = pseudoCodeManager.getLostPhrase(getFile(path))

        phraseView.setPhase(phrase)

        Log.d("PHRASE", phrase.toString())
    }

    fun getFile(path: String): String {
        var buffer: ByteArray? = null
        val inputStream: InputStream

        try {
            inputStream = assets.open(path)
            val size: Int = inputStream.available()
            buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val str_data = String(buffer!!)

        return str_data
    }
}