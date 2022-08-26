package com.example.pakiriot

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [bookingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class bookingFragment : DialogFragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var cal = Calendar.getInstance()
    var booking : TextView? = null
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef2: DatabaseReference

    var lahanBooking : String? = null

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
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val languages = resources.getStringArray(R.array.Languages)
        booking = view.findViewById(R.id.tvJadwalBookingParkirVal)
        booking!!.text = "--/--/----"

        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.spinnerLahanParkir)
//        val size: String = spinner.getSelectedItem().toString()
//        val spinner_pos = spinner.selectedItemPosition
//        val size_values = resources.getStringArray(R.array.Languages)
//        val size = Integer.valueOf(size_values[spinner_pos]) // 12, 16, 20

        val adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.Languages,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

//        if (spinner != null) {
//            val adapter = ArrayAdapter(requireContext(),
//                android.R.layout.simple_spinner_item, languages)
//            spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//                    Toast.makeText(requireContext(),
//                        getString(R.string.selected_item) + " " +
//                                "" + languages[position], Toast.LENGTH_SHORT).show()
//                    lahanBooking = languages[position]
//
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                    return
//                }
//            }
//        } else{
//            return
//        }

//        Toast.makeText(context,
//            size, Toast.LENGTH_SHORT).show()

        val button_date = view.findViewById<Button>(R.id.datePicker1)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                updateDateInView()
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val tanggal = sdf.format(cal.time)
//                Toast.makeText(context, tanggal, Toast.LENGTH_SHORT).show()
                booking!!.text = sdf.format(cal.time)
            }
        }

        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(requireContext(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
        view.findViewById<Button>(R.id.btnCancelDialogFragment).setOnClickListener {
            dismiss()
        }
        val pin = view.findViewById<TextInputEditText>(R.id.etPasswordDialogFragmentVal)
        dbRef = Firebase.database.reference
        dbRef = FirebaseDatabase.getInstance().getReference("parkiran/D6 BB 95 E2/pin")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pinVal = snapshot.value.toString()
                view.findViewById<Button>(R.id.btnSimpanDialogFragment).setOnClickListener{
                    val pinText = pin.text.toString()
                    if (pinVal != pinText){
                        Toast.makeText(context, "Passwod Salah", Toast.LENGTH_SHORT).show()
                    } else {
                        dbRef2 = Firebase.database.reference
                        dbRef2.child("parkiran/" + lahanBooking ).setValue("Parkir Booked")
                            .addOnSuccessListener {
//                            Toast.makeText(this, "Succsess Update Data", Toast.LENGTH_SHORT).show()
                                dismiss()

                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed Update Data", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
//    private fun updateDateInView() {
//        val myFormat = "MM/dd/yyyy" // mention the format you need
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        textview_date!!.text = sdf.format(cal.getTime())
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment bookingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            bookingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // On selecting a spinner item
        val item = parent.getItemAtPosition(position).toString()
        lahanBooking = item

        // Showing selected spinner item
//        Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
        // TODO Auto-generated method stub
    }
}