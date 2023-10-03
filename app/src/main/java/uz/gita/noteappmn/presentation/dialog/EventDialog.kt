package uz.gita.noteappmn.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.noteappmn.R

class EventDialog : BottomSheetDialogFragment() {
    private var clickLockButtonListener: (() -> Unit)? = null
    private var clickDeleteButtonListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.event_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<LinearLayoutCompat>(R.id.lineEdit).setOnClickListener {
            clickLockButtonListener?.invoke()
            dismiss()
        }
        view.findViewById<LinearLayoutCompat>(R.id.lineDelete).setOnClickListener {
            clickDeleteButtonListener?.invoke()
            dismiss()
        }
    }

    fun setClickLockButtonListener(block: () -> Unit) {
        clickLockButtonListener = block
    }

    fun setClickDeleteButtonListener(block: () -> Unit) {
        clickDeleteButtonListener = block
    }
}