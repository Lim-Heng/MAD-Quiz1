package kh.edu.rupp.quiz1.api;

import kh.edu.rupp.quiz1.model.Profile;
import kh.edu.rupp.quiz1.model.ProfileModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("fb-profile.json")
    Call<ProfileModel> loadProfile();
}
