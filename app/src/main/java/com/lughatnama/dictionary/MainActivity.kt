package com.lughatnama.dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.lughatnama.dictionary.data.DictionaryRepository
import com.lughatnama.dictionary.ui.DictionaryApp
import com.lughatnama.dictionary.ui.DictionaryViewModel
import com.lughatnama.dictionary.ui.DictionaryViewModelFactory
import com.lughatnama.dictionary.ui.theme.LughatNamaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelProvider(
            this,
            DictionaryViewModelFactory(DictionaryRepository(applicationContext)),
        )[DictionaryViewModel::class.java]
        setContent {
            LughatNamaTheme {
                DictionaryApp(viewModel)
            }
        }
    }
}
