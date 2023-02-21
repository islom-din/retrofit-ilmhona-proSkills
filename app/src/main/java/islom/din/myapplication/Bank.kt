package islom.din.myapplication

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

data class Bank(
    val id: Int,
    val uid: String,
    @SerializedName("account_number")
    val accNum: String
    //.....
)

interface BankService {
    @GET("banks/")
    fun getBank(): Call<Bank>
}

