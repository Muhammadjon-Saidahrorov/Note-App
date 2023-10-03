package uz.gita.noteappmn.presentation.screen.create

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import uz.gita.noteappmn.R
import uz.gita.noteappmn.data.source.LocalStorage
import uz.gita.noteappmn.databinding.CreateScreenBinding
import uz.gita.noteappmn.databinding.ScreenHomeBinding
import uz.gita.noteappmn.utils.myApply

class CreateScreen : Fragment(R.layout.create_screen) {
    private val binding by viewBinding(CreateScreenBinding::bind)
    private val localStorage = LocalStorage.getInstance()
    var logic = localStorage?.getLogic()
    var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (logic == false) {
            findNavController().navigate(R.id.action_createScreen_to_loginScreen)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myApply {

            editCreatePasword.addTextChangedListener{
                editCreatePasword.setTextColor(Color.parseColor("#000000"))
                editConfirmPasword.setTextColor(Color.parseColor("#000000"))
            }

            editConfirmPasword.addTextChangedListener{
                editCreatePasword.setTextColor(Color.parseColor("#000000"))
                editConfirmPasword.setTextColor(Color.parseColor("#000000"))
            }

            create.setOnClickListener {

                val password = editCreatePasword.text.toString()
                val confirm = editConfirmPasword.text.toString()


                password.forEach {
                    if (it.toString() != "1" && it.toString() != "2" &&
                        it.toString() != "3" && it.toString() != "4" &&
                        it.toString() != "5" && it.toString() != "6" &&
                        it.toString() != "7" && it.toString() != "8" &&
                        it.toString() != "9" && it.toString() != "0"
                    ) {
                        Toast.makeText(requireContext(), "Only Number!", Toast.LENGTH_SHORT).show()
                        editCreatePasword.setTextColor(Color.parseColor("#D62C2C"))
                        editConfirmPasword.setTextColor(Color.parseColor("#D62C2C"))
                        check = false
                        return@setOnClickListener
                    } else {
                        editCreatePasword.setTextColor(Color.parseColor("#000000"))
                        editConfirmPasword.setTextColor(Color.parseColor("#000000"))
                        check = true
                    }
                }


                if (password != "" && password == confirm && logic == true) {
                    Toast.makeText(requireContext(),"Created Password!", Toast.LENGTH_SHORT).show()
                    localStorage?.savePassword(password)
                    logic = false
                    localStorage?.saveLogic(logic!!)
                    findNavController().navigate(R.id.action_createScreen_to_lockScreen)
                } else {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                    YoYo.with(Techniques.Shake).duration(600).playOn(binding.editCreatePasword)
                    YoYo.with(Techniques.Shake).duration(600).playOn(binding.editConfirmPasword)
                    editCreatePasword.setTextColor(Color.parseColor("#D62C2C"))
                    editConfirmPasword.setTextColor(Color.parseColor("#D62C2C"))
                }

            }


        }

    }
}