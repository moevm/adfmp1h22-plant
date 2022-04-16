package com.artex.plants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditSortFragment : BottomSheetDialogFragment() {

    lateinit var sortNameAscend: Button
    lateinit var sortNameDescend: Button
    lateinit var sortDateAscend: Button
    lateinit var sortDateDescend: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sort_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortNameAscend = view.findViewById(R.id.sort_by_name_ascend_btn)
        sortNameDescend = view.findViewById(R.id.sort_by_name_descend_btn)
        sortDateAscend = view.findViewById(R.id.sort_by_date_ascend_btn)
        sortDateDescend = view.findViewById(R.id.sort_by_date_descend_btn)

        sortNameAscend.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("sortKey", "sortNameAscend")
            findNavController().popBackStack()
        }
        sortNameDescend.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("sortKey", "sortNameDescend")
            findNavController().popBackStack()
        }
        sortDateAscend.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("sortKey", "sortDateAscend")
            findNavController().popBackStack()
        }
        sortDateDescend.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("sortKey", "sortDateDescend")
            findNavController().popBackStack()
        }

    }

}