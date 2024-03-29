package tn.aquaguard.repository

import tn.aquaguard.models.Participation
import tn.aquaguard.network.RetrofitClient

class ParticipationRepository {
    suspend fun getParticiptations(): List<Participation>? {
        return  RetrofitClient.participationService.getParticipations().body();
    }

    suspend fun addParticipation(eventId: String) : String?{
        return RetrofitClient.participationService.addParticipation(eventId).body();
    }

    suspend fun isParticip(eventId: String?):Boolean?{
        return RetrofitClient.participationService.isParticipated(eventId).body();
    }

    suspend fun deleteParticipation(eventId: String):String?{
        return RetrofitClient.participationService.deleteParticipation(eventId).body();
    }
}