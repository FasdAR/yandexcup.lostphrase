package ru.fasdev.yandexlostphrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.fasdev.yandexlostphrase.manager.PseudoCodeManager

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phraseView = findViewById<LostPhraseView>(R.id.phrase_view)
        val pseudoCodeManager: PseudoCodeManager = PseudoCodeManager()
        val phrase = pseudoCodeManager.getLostPhrase(getCode())

        phraseView.setPhase(phrase)

        Log.d("PHRASE", phrase.toString())
    }

    private fun getCode(): String = "400 400\n" +
            "10\n" +
            "rectangle 69.000 280.000 24.000 24.000 0.000 black\n" +
            "1\n" +
            "move 251.000 72.000 10000 cycle\n" +
            "rectangle 256.000 188.000 24.000 48.000 0.000 black\n" +
            "0\n" +
            "rectangle 232.000 152.000 72.000 24.000 0.000 black\n" +
            "0\n" +
            "rectangle 35.000 400.000 24.000 24.000 0.000 black\n" +
            "1\n" +
            "move 285.000 -96.000 10000 cycle\n" +
            "rectangle 244.000 248.000 48.000 24.000 0.000 black\n" +
            "0\n" +
            "rectangle 300.000 117.000 24.000 48.000 0.000 black\n" +
            "1\n" +
            "move 112.000 164.000 5000\n" +
            "rectangle 136.000 200.000 72.000 24.000 0.000 black\n" +
            "0\n" +
            "rectangle 160.000 236.000 24.000 48.000 0.000 black\n" +
            "0\n" +
            "rectangle 208.000 224.000 24.000 72.000 44.797 black\n" +
            "1\n" +
            "rotate -44.797 5000\n" +
            "rectangle 232.000 200.000 24.000 24.000 0.000 black\n" +
            "0"
}