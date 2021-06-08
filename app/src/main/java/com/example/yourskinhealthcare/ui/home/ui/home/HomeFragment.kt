package com.example.yourskinhealthcare.ui.home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourskinhealthcare.databinding.FragmentHomeBinding
import com.example.yourskinhealthcare.detail.*

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    var rImage1: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            rImage1 = binding.rImage1
            val btn = binding.text1
            val btn2 = binding.text2
            val btn3 = binding.text3
            val btn4 = binding.text4
            val btn5 = binding.text5

            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.reference
            val getImage = databaseReference.child("image1")
            getImage.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {

                    val link = dataSnapshot.getValue(String::class.java)!!
                    Picasso.get().load(link).into(rImage1)
                }

                override fun onCancelled(@NonNull databaseError: DatabaseError) {
                    Toast.makeText(requireActivity(), "Error Loading Image", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            btn.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity1::class.java))
                }
            }
            btn2.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity2::class.java))
                }
            }
            btn3.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity4::class.java))
                }
            }
            btn4.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity3::class.java))
                }
            }
            btn5.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity5::class.java))
                }
            }
        }
    }

}
