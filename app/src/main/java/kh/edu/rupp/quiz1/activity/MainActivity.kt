package kh.edu.rupp.quiz1.activity.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kh.edu.rupp.quiz1.api.ApiService
import kh.edu.rupp.quiz1.databinding.ActivityMainBinding
import kh.edu.rupp.quiz1.model.ProfileModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create<ApiService>()

        apiService.loadProfile().enqueue(object : Callback<ProfileModel> {
            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                displayProfile(response.body())
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {

            }
        })
    }

    private fun displayProfile(profileModel: ProfileModel?) {
        Picasso.get().load(profileModel?.getData()?.profileImage).into(binding.imgProfile)
        Picasso.get().load(profileModel?.getData()?.coverImage).into(binding.imgBackground)
        binding.txtBio.setText(profileModel?.getData()?.bio)
        binding.txtJob.setText(profileModel?.getData()?.job)
        binding.txtFriendNumber.setText(profileModel?.getData()?.friendCount.toString() + " friends")
        binding.txtWorkplace.setText(profileModel?.getData()?.workPlace)
        binding.txtMaritalStatus.setText(profileModel?.getData()?.maritalStatus)
        binding.txtTopBar.setText(profileModel?.getData()?.firstName + " " + profileModel?.getData()?.lastName)
        binding.txtProfileName.setText(profileModel?.getData()?.firstName + " " + profileModel?.getData()?.lastName)
    }

}