package com.example.buttonnavigationviewlesson.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.buttonnavigationviewlesson.databinding.FragmentContentBinding

class FragmentContent : Fragment() {

    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val label = arguments?.getString("LabelProduct")

        val args: FragmentContentArgs by navArgs()
        val label = args.LabelProduct
        (activity as AppCompatActivity).supportActionBar?.title = label
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentContent()
    }
}