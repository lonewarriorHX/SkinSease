package com.example.yourskinhealthcare.ui.home.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourskinhealthcare.authentication.LoginActivity
import com.example.yourskinhealthcare.databinding.FragmentProfileBinding
import com.example.yourskinhealthcare.ui.home.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.HashMap

class ProfileFragment : Fragment() {

    companion object {
        const val TAG = "TAG"
    }

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    private lateinit var userId: String
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var user: FirebaseUser
    private lateinit var storageReference: StorageReference

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        userId = auth.currentUser!!.uid
        user = auth.currentUser!!
        storageReference = FirebaseStorage.getInstance().reference

        val documentReference = fStore.collection("users").document(
            userId
        )

        documentReference.addSnapshotListener(
            requireActivity()
        ) { documentSnapshot, e ->
            if (documentSnapshot!!.exists()) {
                binding.profileFullName.setText(documentSnapshot.getString("fName"))
                binding.profileEmail.setText(documentSnapshot.getString("email"))
            } else {
                Log.d("tag", "onEvent: Document do not exists")
            }
        }

        val profileRef = storageReference.child(
            "users/" + auth.currentUser!!
                .uid + "/profile.jpg"
        )
        profileRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(binding.profilePicture)
        }
        binding.profilePicture.setOnClickListener {
            val openGalleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(openGalleryIntent, 1000)
        }
        binding.btnEditProfile.setOnClickListener(View.OnClickListener {
            if (binding.profileFullName.text.toString()
                    .isEmpty() || binding.profileEmail.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(
                    requireActivity(),
                    "One or Many fields are empty.",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            val email = profileEmail.text.toString()
            user.updateEmail(email).addOnSuccessListener {
                val docRef = fStore.collection("users").document(
                    user.uid
                )
                val edited: MutableMap<String, Any> =
                    HashMap()
                edited["email"] = email
                edited["fName"] = binding.profileFullName.text.toString()
                docRef.update(edited).addOnSuccessListener {
                    Toast.makeText(requireActivity(), "Profile Updated", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }

            }.addOnFailureListener { e ->
                Toast.makeText(
                    requireActivity(),
                    e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.btnResetPassword.setOnClickListener(View.OnClickListener { v ->
            val resetMail = EditText(v.context)
            val passwordResetDialog = AlertDialog.Builder(v.context)
            passwordResetDialog.setTitle("Reset Password ?")
            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.")
            passwordResetDialog.setView(resetMail)
            passwordResetDialog.setPositiveButton(
                "Yes"
            ) { dialog, which -> // extract the email and send reset link
                val mail = resetMail.text.toString()
                auth.sendPasswordResetEmail(mail).addOnSuccessListener {
                    Toast.makeText(
                        requireActivity(),
                        "Reset Link Sent To Your Email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        requireActivity(),
                        "Error ! Reset Link is Not Sent" + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            passwordResetDialog.setNegativeButton(
                "No"
            ) { dialog, which ->
            }
            passwordResetDialog.create().show()
        })

        documentReference.addSnapshotListener(
            requireActivity()
        ) { documentSnapshot, e ->
            if (documentSnapshot!!.exists()) {
                binding.profileFullName.setText(documentSnapshot.getString("fName"))
                binding.profileEmail.setText(documentSnapshot.getString("email"))
            } else {
                Log.d("tag", "onEvent: Document do not exists")
            }
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val imageUri = data!!.data

                //profileImage.setImageURI(imageUri);
                uploadImageToFirebase(imageUri)
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri?) {
        // upload image to firebase storage
        val fileRef =
            storageReference.child("users/" + auth.currentUser!!.uid + "/profile.jpg")
        fileRef.putFile(imageUri!!).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(binding.profilePicture)
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}