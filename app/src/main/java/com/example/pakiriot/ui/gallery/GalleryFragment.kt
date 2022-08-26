package com.example.pakiriot.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pakiriot.MyAdapter
import com.example.pakiriot.R
import com.example.pakiriot.dataSensor
import com.example.pakiriot.databinding.FragmentGalleryBinding
import com.google.firebase.database.*

class GalleryFragment : Fragment() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var fpArray:ArrayList<dataSensor>
    private lateinit var fpRecyclerView: RecyclerView
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fpRecyclerView = view.findViewById(R.id.DataSensorListHistory)
        fpRecyclerView.layoutManager = LinearLayoutManager(context)
        fpRecyclerView.setHasFixedSize(true)

        fpArray = arrayListOf<dataSensor>()

//        swipeRefreshLayout?.isRefreshing = true
        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/history")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
//                fpArray = ArrayList()
//                ArrayList<dataSensor>()()() = ArrayList<dataSensor>()
                    for (fpSnapshot in snapshot.children){
                        val fp = fpSnapshot.getValue(dataSensor::class.java)
                        fpArray.add(fp!!)
                    }
                var adapter = MyAdapter(fpArray)
                fpRecyclerView.adapter = adapter
                adapter.setItems(fpArray);
                adapter.notifyDataSetChanged();
//                swipeRefreshLayout?.isRefreshing = false
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}