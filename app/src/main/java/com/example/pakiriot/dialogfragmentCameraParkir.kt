package com.example.pakiriot

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.marcoscg.ipcamview.IPCamView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dialogfragmentCameraParkir.newInstance] factory method to
 * create an instance of this fragment.
 */
class dialogfragmentCameraParkir : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialogcamera_parkir, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val webView = view.findViewById<WebView>(R.id.webView)
//
//        webView.webViewClient = WebViewClient()
//        webView.settings.javaScriptEnabled = true
//        // this will load the url of the website
//        webView.loadUrl("http://192.168.106.207")
//        // if you want to enable zoom feature
////        webView.webViewClient = HelloWebViewClient()
//        WebView.setWebContentsDebuggingEnabled(false)
//        webView.settings.setSupportZoom(true)
        val ipCamView: IPCamView = view.findViewById(R.id.ip_cam_view)
        ipCamView.setUrl("http://192.168.228.207")
        ipCamView.setInterval(1000) // In milliseconds, default 1000

        ipCamView.start()

        view.findViewById<Button>(R.id.btnCancelDialogfragment).setOnClickListener {
            dismiss()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment dialogfragmentCameraParkir.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            dialogfragmentCameraParkir().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}