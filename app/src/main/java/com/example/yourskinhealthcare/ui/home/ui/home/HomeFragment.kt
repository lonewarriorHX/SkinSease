package com.example.yourskinhealthcare.ui.home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView

import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourskinhealthcare.authentication.LoginActivity
import com.example.yourskinhealthcare.databinding.FragmentHomeBinding

import com.example.yourskinhealthcare.detail.DetailActivity1
import com.example.yourskinhealthcare.detail.DetailActivity2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*

import com.google.firebase.auth.FirebaseAuth


/*class HomeFragment : AppCompatActivity() {
    // Initializing the ImageView
    var rImage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.HomeFragment)

        // getting ImageView by its id
        rImage = findViewById(R.id.rImage1)

        // we will get the default FirebaseDatabase instance
        val firebaseDatabase = FirebaseDatabase.getInstance()

        // we will get a DatabaseReference for the database root node
        val databaseReference = firebaseDatabase.reference

        // Here "image" is the child node value we are getting
        // child node data in the getImage variable
        val getImage = databaseReference.child("image")

        // Adding listener for a single change
        // in the data at this location.
        // this listener will triggered once
        // with the value of the data at the location
        getImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                // getting a DataSnapshot for the location at the specified
                // relative path and getting in the link variable
                val link = dataSnapshot.getValue(String::class.java)!!

                // loading that data into rImage
                // variable which is ImageView
                Picasso.get().load(link).into(rImage)
            }

            // this will called when any problem
            // occurs in getting data
            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                // we are showing that error message in toast
                Toast.makeText(this@MainActivity, "Error Loading Image", Toast.LENGTH_SHORT).show()
            }
        })
    }
}*/

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
            btn5.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity2::class.java))
                }
            }
            btn2.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity1::class.java))
                }
            }
            btn3.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity1::class.java))
                }
            }
            btn4.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, DetailActivity1::class.java))
                }
            }
        }
    }

}
