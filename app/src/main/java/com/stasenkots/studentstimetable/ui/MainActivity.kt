package com.stasenkots.studentstimetable.ui


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.stasenkots.logic.entity.User
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.SERVER_CLIENT_ID
import com.stasenkots.studentstimetable.databinding.ActivityMainBinding
import com.stasenkots.studentstimetable.showError
import com.stasenkots.studentstimetable.ui.setup.SetUpActivity
import com.stasenkots.studentstimetable.ui.timetable.TimeTableActivity


private const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        MobileAds.initialize(this){ }
        viewModel.isUserRegistered.observe(this, {
            if (it == false || User.groupId.isEmpty()) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestServerAuthCode(SERVER_CLIENT_ID)
                    .requestEmail()
                    .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
                val signInIntent: Intent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            } else {
                viewModel.loadFromDb()
            }
        })

        viewModel.isDataLoaded.observe(this, {
            startActivity(Intent(this, TimeTableActivity::class.java))
            finish()
        }
        )
        viewModel.errorBus.observe(this, {
            showError(binding.root, getString(R.string.unexpexted_error))
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
                data?.let { viewModel.loginUser(it) }
            User.updated.observe(this) {
                if (User.groupId.isEmpty()) {
                    startActivity(Intent(this, SetUpActivity::class.java))
                } else {
                    viewModel.loadFromDb()
                    startActivity(Intent(this, TimeTableActivity::class.java))
                }
                finish()
            }
        }
    }


}