package kh.edu.rupp.quiz1.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kh.edu.rupp.quiz1.databinding.ActivityMainBinding
import kh.edu.rupp.quiz1.model.ProfileModel
import kh.edu.rupp.quiz1.model.UiStateStatus
import kh.edu.rupp.quiz1.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe the UI state
        viewModel.profileModelUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    // Show loading state

                    // You can show a progress bar or any loading indicator here
                }
                UiStateStatus.success -> {

                    // Data loaded successfully, update UI
                    displayProfile(uiState.data)
                }
                UiStateStatus.error -> {
                    // Handle error state

                    // You can show an error message or retry option here

                }
            }
        })

        // Load profile data
        viewModel.loadProfileModel()
    }

    @SuppressLint("SetTextI18n")
    private fun displayProfile(profileModel: ProfileModel?) {
        Picasso.get().load(profileModel?.data?.profileImage).into(binding.imgProfile)
        Picasso.get().load(profileModel?.data?.coverImage).into(binding.imgBackground)
        binding.txtBio.text = profileModel?.data?.bio
        binding.txtJob.text = profileModel?.data?.job
        binding.txtFriendNumber.text = "${profileModel?.data?.friendCount} friends"
        binding.txtWorkplace.text = profileModel?.data?.workPlace
        binding.txtMaritalStatus.text = profileModel?.data?.maritalStatus
        binding.txtTopBar.text = "${profileModel?.data?.firstName} ${profileModel?.data?.lastName}"
        binding.txtProfileName.text = "${profileModel?.data?.firstName} ${profileModel?.data?.lastName}"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
