package uz.gita.noteappmn.presentation.screen.login

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import uz.gita.noteappmn.R
import uz.gita.noteappmn.data.source.LocalStorage
import uz.gita.noteappmn.databinding.LoginScreenBinding
import uz.gita.noteappmn.utils.myApply

class LoginScreen : Fragment(R.layout.login_screen) {
    private val binding by viewBinding(LoginScreenBinding::bind)
    private val localStorage = LocalStorage.getInstance()
    private var code = StringBuilder("")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myApply {

            keyboardContiner.btOne.setOnClickListener {
                code.append("1")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))
            }
            keyboardContiner.btTwo.setOnClickListener {
                code.append("2")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))
            }
            keyboardContiner.btThree.setOnClickListener {
                code.append("3")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))
            }
            keyboardContiner.btFour.setOnClickListener {
                code.append("4")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))
            }
            keyboardContiner.btFive.setOnClickListener {
                code.append("5")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))

            }
            keyboardContiner.btSix.setOnClickListener {
                code.append("6")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))

            }
            keyboardContiner.btSeven.setOnClickListener {
                code.append("7")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))

            }
            keyboardContiner.btEight.setOnClickListener {
                code.append("8")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))

            }
            keyboardContiner.btNine.setOnClickListener {
                code.append("9")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))

            }
            keyboardContiner.btZero.setOnClickListener {
                code.append("0")
                editPasword.setText(code)
                binding.editPasword.setTextColor(Color.parseColor("#000000"))

            }
            keyboardContiner.btClear.setOnClickListener {
                if (code.isNotEmpty()) {
                    code.deleteCharAt(code.length - 1)
                    editPasword.setText(code)
                    binding.editPasword.setTextColor(Color.parseColor("#000000"))
                }
            }

            keyboardContiner.btClear.setOnLongClickListener {
                if (code.isNotEmpty()) {
                    code.clear()
                    editPasword.setText(code)
                    binding.editPasword.setTextColor(Color.parseColor("#000000"))
                }
                true
            }

            keyboardContiner.btConfirm.setOnClickListener {
                val password = editPasword.text.toString()

                if (localStorage?.getPassword() == password) {
                    findNavController().navigate(R.id.action_loginScreen_to_lockScreen)
                } else {
                    Toast.makeText(requireContext(), "ERROR!", Toast.LENGTH_SHORT).show()
                    binding.editPasword.setTextColor(Color.parseColor("#E63838"))
                    YoYo.with(Techniques.Shake).duration(600).playOn(binding.editPasword)
                }

            }

        }


        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.login_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {

                    R.id.clear_password -> {
                        val dialog = AlertDialog.Builder(requireContext())
                            .setCancelable(false)
                            .setTitle("Do you want to really delete password? ")
                            .setMessage("If you delete password, All lock notes will delete!")
                            .setPositiveButton(
                                "Yes",
                                (DialogInterface.OnClickListener { _, _ ->
                                    Toast.makeText(requireContext(),"Deleted Password!", Toast.LENGTH_SHORT).show()
                                    localStorage?.saveLogic(true)
                                    localStorage?.saveLogicDelete(false)
                                    findNavController().navigate(R.id.action_loginScreen_to_createScreen)
                                })
                            )
                            .setNegativeButton(
                                "No",
                                (DialogInterface.OnClickListener { d, _ ->
                                    d.dismiss()
                                })
                            )
                            .create()
                        dialog.show()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)
    }

}