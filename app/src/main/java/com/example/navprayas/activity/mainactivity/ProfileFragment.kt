package com.example.navprayas.activity.mainactivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.navprayas.R
import com.example.navprayas.databinding.FragmentProfileBinding
import com.google.api.ResourceProto.resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = binding.etName
        val email = binding.etEmailAddress
        val clas = binding.etClass
        val mobileNumber = binding.etMobileNumber
        val address = binding.etAddress
        val profilePic = binding.profileImage
        val changeImage = binding.changeImage
        imageUri = null
        changeImage.setOnClickListener {
            openImagePicker()
        }
        binding.btnEdit.setOnClickListener {
            name.isEnabled = true
            clas.isEnabled = true
            mobileNumber.isEnabled = true
            address.isEnabled = true
            changeImage.visibility = View.VISIBLE
            name.requestFocus()
        }
        binding.btnSave.setOnClickListener {
            name.isEnabled = false
            clas.isEnabled = false
            mobileNumber.isEnabled = false
            address.isEnabled = false
            changeImage.visibility = View.GONE
            viewModel.setName(name.text.toString())
            viewModel.setClass(clas.text.toString())
            viewModel.setAdrress(address.text.toString())
            viewModel.setMobileNumber(mobileNumber.text.toString())
            viewModel.student.value?.email= Firebase.auth.currentUser?.email.toString()
            Log.d("Reps", "xyz ${viewModel.student.value}")
            viewModel.updateUser()
        }
        viewModel.student.observe(viewLifecycleOwner) {
            name.setText(it.name)
            email.setText(it.email)
            clas.setText(it.clas)
            mobileNumber.setText(it.mobileNumber)
            address.setText(it.address)
            val imageUri = it.image
            Glide.with(this).load(imageUri).into(profilePic)
            if(imageUri.toString()=="null") {
                Log.d("Repos", "xyz $imageUri")
                Glide.with(this).load(R.drawable.ic_baseline_person_24).into(profilePic)

            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            Log.d("Repos", "xyz $imageUri")
            Glide.with(this).load(imageUri).into(binding.profileImage)
            viewModel.uploadImage(imageUri!!)
        }
    }

}
