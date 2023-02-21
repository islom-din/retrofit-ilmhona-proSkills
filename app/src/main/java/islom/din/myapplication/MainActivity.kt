package islom.din.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("retrofit", "onCreate: ")

        //1)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://random-data-api.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2)
        val service: BankService = retrofit.create(BankService::class.java)

        //3)
        val callback: Callback<Bank> = object : Callback<Bank> {
            override fun onResponse(call: Call<Bank>, response: Response<Bank>) {
                // еще не означает, что запрос успешный
                if(response.isSuccessful) {
                    // точно успешный
                    val bank = response.body()
                    Log.d("retrofit", "ID: ${bank?.id}")
                    Log.d("retrofit", "UID: ${bank?.uid}")
                    Log.d("retrofit", "Account number: ${bank?.accNum}")
                }
            }

            override fun onFailure(call: Call<Bank>, t: Throwable) {
                Log.d("retrofit", "Failed -> ${t.message}")
            }
        }

        //4)
        service.getBank().enqueue(callback)

    }
}