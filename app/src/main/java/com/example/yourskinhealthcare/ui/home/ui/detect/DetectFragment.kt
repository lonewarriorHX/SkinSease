package com.example.yourskinhealthcare.ui.home.ui.detect

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourskinhealthcare.databinding.FragmentDetectBinding
import com.example.yourskinhealthcare.main.CameraActivity
import com.example.yourskinhealthcare.main.DetectActivity

class DetectFragment : Fragment() {

    private lateinit var detectViewModel: DetectViewModel
    private var _binding: FragmentDetectBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detectViewModel =
            ViewModelProvider(this).get(DetectViewModel::class.java)

        _binding = FragmentDetectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cobaTes.setOnClickListener {
            val intent = Intent(requireActivity(), DetectActivity::class.java)
            startActivity(intent)
        }

        binding.btnCamDetect.setOnClickListener {
            val intent = Intent(requireActivity(), CameraActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}