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
                    showLoading()
                }
                UiStateStatus.success -> {
                    // Data loaded successfully, update UI
                    hideLoading()
                    displayProfile(uiState.data)
                }
                UiStateStatus.error -> {
                    // Handle error state
                    hideLoading()
                    showError(uiState.message ?: "Unknown error occurred.")
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

    private var loadingDialog: ProgressDialog? = null

    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
            loadingDialog!!.setMessage("Loading...")
            loadingDialog!!.setCancelable(false)
            loadingDialog!!.show()
        }
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showError(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
