package islom.din.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel() : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private var _text: MutableLiveData<String> = MutableLiveData("Hello world this is text :)")
    val text: LiveData<String> = _text

    private var _secondsLiveData: MutableLiveData<String> = MutableLiveData()
    val secondsLiveData: LiveData<String> = _secondsLiveData



    //1)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://random-data-api.com/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //2)
    private val service: BankService = retrofit.create(BankService::class.java)

    private val callback: Callback<Bank> = object : Callback<Bank> {
        override fun onResponse(call: Call<Bank>, response: Response<Bank>) {
            // еще не означает, что запрос успешный
            if(response.isSuccessful) {
                // точно успешный
                val bank = response.body()
                _text.postValue("ID: ${bank?.id}\nUID: ${bank?.uid}\nAccount number: ${bank?.accNum}")
            }
        }

        override fun onFailure(call: Call<Bank>, t: Throwable) {
            Log.d("retrofit", "Failed -> ${t.message}")
        }
    }

    fun generateText() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _secondsLiveData.postValue("1")
            delay(1000)
            _secondsLiveData.postValue("2")
            delay(1000)
            _secondsLiveData.postValue("3")
            delay(500)
            service.getBank().enqueue(callback)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
    // Retrofit and Room работают здесь
}