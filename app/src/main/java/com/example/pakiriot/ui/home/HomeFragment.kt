package com.example.pakiriot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pakiriot.R
import com.example.pakiriot.TopUpFragment
import com.example.pakiriot.bookingFragment
import com.example.pakiriot.databinding.FragmentHomeBinding
import com.example.pakiriot.dialogfragmentCameraParkir
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dbRef: DatabaseReference
//    private lateinit var fpArray:ArrayList<fp>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val updateFingerprint = dialogfragmentCameraParkir()
        val updateFingerprintw = TopUpFragment()
        val updateFingerprint3 = bookingFragment()
        val fragmenManager = childFragmentManager

        view.findViewById<Button>(R.id.btnCameraParkirHome).setOnClickListener {
            updateFingerprint.show(fragmenManager, dialogfragmentCameraParkir::class.java.simpleName)
        }
        view.findViewById<Button>(R.id.btnTopUpHome).setOnClickListener {
            updateFingerprintw.show(fragmenManager, TopUpFragment::class.java.simpleName)
        }
        view.findViewById<Button>(R.id.btnBookingHome).setOnClickListener {
            updateFingerprint3.show(fragmenManager, bookingFragment::class.java.simpleName)
        }

        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/D6 BB 95 E2/saldo")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                val tvStat = view!!.findViewById<TextView>(R.id.tvDataSensorPm2_5HomeVal)
                tvStat.text = value
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })
        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/Lahan Parkir 1")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()

                if(value == "Parkir Booked" || value == "Parkir Terisi"){
                    view.findViewById<ImageView>(R.id.imgParkitLahan1).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan1TidakTersedia).visibility = View.VISIBLE
                }else{
                    view.findViewById<ImageView>(R.id.imgParkitLahan1TidakTersedia).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan1).visibility = View.VISIBLE
                }

                val tvStat = view!!.findViewById<TextView>(R.id.tvStatParkirLahan1)
                tvStat.text = value

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })

        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/Lahan Parkir 2")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                if(value == "Parkir Booked" || value == "Parkir Terisi"){
                    view.findViewById<ImageView>(R.id.imgParkitLahan2).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan2TidakTersedia).visibility = View.VISIBLE
                }else{
                    view.findViewById<ImageView>(R.id.imgParkitLahan2TidakTersedia).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan2).visibility = View.VISIBLE
                }
                val tvStat = view!!.findViewById<TextView>(R.id.tvStatParkirLahan2)
                tvStat.text = value

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })
        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/Lahan Parkir 3")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                if(value == "Parkir Booked" || value == "Parkir Terisi"){
                    view.findViewById<ImageView>(R.id.imgParkitLahan3).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan3TidakTersedia).visibility = View.VISIBLE
                }else{
                    view.findViewById<ImageView>(R.id.imgParkitLahan3TidakTersedia).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan3).visibility = View.VISIBLE
                }
                val tvStat = view!!.findViewById<TextView>(R.id.tvStatParkirLahan3)
                tvStat.text = value

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })
        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/Lahan Parkir 4")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                if(value == "Parkir Booked" || value == "Parkir Terisi"){
                    view.findViewById<ImageView>(R.id.imgParkitLahan4).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan4TidakTersedia).visibility = View.VISIBLE
                }else{
                    view.findViewById<ImageView>(R.id.imgParkitLahan4TidakTersedia).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan4).visibility = View.VISIBLE
                }
                val tvStat = view!!.findViewById<TextView>(R.id.tvStatParkirLahan4)
                tvStat.text = value

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })
        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/Lahan Parkir 5")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                if(value == "Parkir Booked" || value == "Parkir Terisi"){
                    view.findViewById<ImageView>(R.id.imgParkitLahan5).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan5TidakTersedia).visibility = View.VISIBLE
                }else{
                    view.findViewById<ImageView>(R.id.imgParkitLahan5TidakTersedia).visibility = View.INVISIBLE
                    view.findViewById<ImageView>(R.id.imgParkitLahan5).visibility = View.VISIBLE
                }
                val tvStat = view!!.findViewById<TextView>(R.id.tvStatParkirLahan5)
                tvStat.text = value

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