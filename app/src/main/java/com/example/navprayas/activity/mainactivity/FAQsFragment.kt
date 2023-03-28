package com.example.navprayas.activity.mainactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navprayas.R
import com.example.navprayas.databinding.FragmentFAQsBinding

class FAQsFragment : Fragment() {
    private lateinit var binding: FragmentFAQsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFAQsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlus.setOnClickListener(){
            binding.cardAns.visibility = View.VISIBLE
            binding.btnPlus.visibility = View.GONE
            binding.btnMinus.visibility = View.VISIBLE
        }
        binding.btnMinus.setOnClickListener(){
            binding.cardAns.visibility = View.GONE
            binding.btnPlus.visibility = View.VISIBLE
            binding.btnMinus.visibility = View.GONE
        }
    }
}
