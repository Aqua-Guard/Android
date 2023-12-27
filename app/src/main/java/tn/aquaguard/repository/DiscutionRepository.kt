package tn.aquaguard.repository

import okhttp3.RequestBody
import retrofit2.Response
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Discution
import tn.aquaguard.models.SendMessageRequest
import tn.aquaguard.network.RetrofitClient

class DiscutionRepository {
    suspend fun getallmessagesofthisreclamation(reclamationId : String): List<Discution>? {
        val response = RetrofitClient.discutionservice.getallmessagesofthisreclamation(reclamationId)

        return if (response.isSuccessful) {
            response.body()
        } else {
             return null
        }
    }

     suspend fun sendmessage(relamationId :String ,message:String):Discution?{
         val requistmessage = SendMessageRequest(relamationId,message)
      return RetrofitClient.discutionservice.sendmessage(requistmessage).body()
     }

    suspend fun deleteonlyforuser(id: String):String?{
        return RetrofitClient.discutionservice.deleteonlyforuser(id).body()
    }

    suspend fun deleteforall(id: String):String?{
        return RetrofitClient.discutionservice.deleteonlyforuser(id).body()
    }
}