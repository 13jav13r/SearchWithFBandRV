package com.example.buttonnavigationviewlesson.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buttonnavigationviewlesson.adapter.AdapterRVHomeFrag
import com.example.buttonnavigationviewlesson.adapter.Product
import com.example.buttonnavigationviewlesson.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentHome : Fragment(), AdapterRVHomeFrag.Listener {

    private lateinit var binding: FragmentHomeBinding
    private val PRODUCT_KEY = "Product"
    private val PRODUCT_1_KEY = "Product1"
    private lateinit var adapter: AdapterRVHomeFrag
    private lateinit var listProduct: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rcView.layoutManager = GridLayoutManager(activity, 2)
            listProduct = ArrayList()
            adapter = AdapterRVHomeFrag(listProduct, this@FragmentHome)
            rcView.adapter = adapter
            readingFromDatabase()
        }
    }

    private fun readingFromDatabase() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(PRODUCT_KEY)

        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (listProduct.size > 0) {
                    listProduct.clear()
                }
                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        listProduct.add(product!!)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentHome()
    }

    override fun onClick(product: Product) {
        val action = FragmentHomeDirections.actionFragmentHomeToFragmentContent(product.moduleName)

        val controller = findNavController()
        controller.navigate(action)
    }
}