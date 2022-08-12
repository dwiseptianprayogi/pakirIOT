package com.example.pakiriot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopUpFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var database: DatabaseReference
    private lateinit var etPass: TextInputEditText
    private lateinit var dbRef: DatabaseReference

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
        return inflater.inflate(R.layout.fragment_top_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topup = view.findViewById<TextInputEditText>(R.id.etNpmDialogFragment)
        val pin = view.findViewById<TextInputEditText>(R.id.etPasswordDialogFragmentVal)

        val btn = view.findViewById<Button>(R.id.btnSimpanDialogFragment)

            dbRef = Firebase.database.reference
            database = Firebase.database.reference
            dbRef = FirebaseDatabase.getInstance().getReference("parkiran/D6 BB 95 E2/pin")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pinVal = snapshot.value.toString()
                    dbRef = FirebaseDatabase.getInstance().getReference("parkiran/D6 BB 95 E2/saldo")
                    dbRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            btn.setOnClickListener {
                                val pinText = pin.text.toString()
                                if (pinVal != pinText){
                                    Toast.makeText(context, "Passwod Salah", Toast.LENGTH_SHORT).show()
                                } else{
                                    val value = snapshot.value.toString()
                                    val data : Int? = value.toInt()
//                Toast.makeText(context, value, Toast.LENGTH_SHORT).show()

                                    val text = topup.text.toString()
                                    val hitung1 : Int? = text.toInt()
                                    val tambahSaldo = hitung1!!+data!!
                                    database = Firebase.database.reference
                                    database.child("parkiran/D6 BB 95 E2/saldo").setValue(tambahSaldo)
                                        .addOnSuccessListener {
//                            Toast.makeText(this, "Succsess Update Data", Toast.LENGTH_SHORT).show()
                                            dismiss()

                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Failed Update Data", Toast.LENGTH_SHORT).show()
                                        }
                                }


//                    dismiss()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(applicationContext, "Failed To Get Data", Toast.LENGTH_LONG).show()
                        }
                    })

                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
                }
            })



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TopUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}