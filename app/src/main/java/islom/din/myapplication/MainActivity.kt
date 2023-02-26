package islom.din.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var button: Button

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        textView = findViewById(R.id.text_view)
        button = findViewById(R.id.button)

        setListeners()
        observe()
    }

    private fun setListeners() {
        button.setOnClickListener {
            viewModel.generateText()
        }
    }

    private fun observe() {
        with(viewModel) {
            text.observe(this@MainActivity) { newString ->
                textView.text = newString
            }

            secondsLiveData.observe(this@MainActivity) { newSecond ->
                textView.text = newSecond
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}