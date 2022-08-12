package com.example.pakiriot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pakiriot.R
import com.example.pakiriot.TopUpFragment
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
        val fragmenManager = childFragmentManager

        view.findViewById<Button>(R.id.btnCameraParkirHome).setOnClickListener {
            updateFingerprint.show(fragmenManager, dialogfragmentCameraParkir::class.java.simpleName)
        }
        view.findViewById<Button>(R.id.btnTopUpHome).setOnClickListener {
            updateFingerprintw.show(fragmenManager, TopUpFragment::class.java.simpleName)
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

//        val webView = view.findViewById<WebView>(R.id.webView)

//        webView.webViewClient = WebViewClient()
//        webView.settings.javaScriptEnabled = true
//        // this will load the url of the website
//        webView.loadUrl("http://192.168.228.207")
//        // if you want to enable zoom feature
////        webView.webViewClient = HelloWebViewClient()
//        WebView.setWebContentsDebuggingEnabled(false)
//        webView.settings.setSupportZoom(true)
//        webView.reload()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}