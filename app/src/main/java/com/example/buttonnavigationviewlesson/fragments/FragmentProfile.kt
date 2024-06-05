package com.example.buttonnavigationviewlesson.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.buttonnavigationviewlesson.databinding.FragmentProfileBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentProfile : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = Firebase.auth

        binding.SignUp.setOnClickListener {
            binding.apply {
                if (!TextUtils.isEmpty(edEmail.text.toString()) && !TextUtils.isEmpty(edPassword.text.toString())) {
                    mAuth.createUserWithEmailAndPassword(
                        edEmail.text.toString().trim(),
                        edPassword.text.toString().trim()
                    ).addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful) {
                            showSigned()
                            sendEmailVer()
                            Log.d("MyLog", "Регистрация прошла успешно")
                            Toast.makeText(
                                activity,
                                "Регистрация прошла успешно",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("MyLog", "Регистрация не прошла")
                            Toast.makeText(
                                activity,
                                "Регистрация не прошла",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } else {
                    Toast.makeText(
                        activity,
                        "Поле для емейла и/или для пароля пустое",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.SignIn.setOnClickListener {
            binding.apply {
                if (!TextUtils.isEmpty(edEmail.text.toString()) && !TextUtils.isEmpty(edPassword.text.toString())) {
                    mAuth.signInWithEmailAndPassword(
                        edEmail.text.toString().trim(),
                        edPassword.text.toString().trim()
                    ).addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Авторизация прошла успешно",
                                Toast.LENGTH_SHORT
                            ).show()
                            showSigned()
                        } else {
                            Toast.makeText(
                                activity,
                                "Авторизация не прошла",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } else {
                    Toast.makeText(
                        activity,
                        "Поле для емейла и/или для пароля пустое",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.bSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            notSigned()
        }

//        binding.bStart.setOnClickListener {
//            val intent = Intent(this@FragmentProfile, MainActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun showSigned() {
        val user = mAuth.currentUser

        if (user?.isEmailVerified == true) {
            binding.apply {

                tvUserEmail.visibility = View.VISIBLE
                tvUserEmail.text = ("Вы вошли как: " + mAuth.currentUser?.email)
                bStart.visibility = View.VISIBLE
                bSignOut.visibility = View.VISIBLE
                edEmail.visibility = View.GONE
                edPassword.visibility = View.GONE
                SignIn.visibility = View.GONE
                SignUp.visibility = View.GONE
            }
        } else {
            Toast.makeText(
                activity,
                "Не пройдена верификация",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun notSigned() {
        binding.apply {
            tvUserEmail.visibility = View.GONE
            bStart.visibility = View.GONE
            bSignOut.visibility = View.GONE
            edEmail.visibility = View.VISIBLE
            edPassword.visibility = View.VISIBLE
            SignIn.visibility = View.VISIBLE
            SignUp.visibility = View.VISIBLE
        }
    }

    private fun sendEmailVer() {
        mAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    activity,
                    "Проверьте ваш email и подтвердите регистрацию",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    activity,
                    "Не удалось отправить сообщение на почту, проверьте адресс электронной почты",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val cUser = mAuth.currentUser

        if (cUser != null) {
            binding.apply {
                showSigned()
            }
            Toast.makeText(activity, "Пользователь уже в системе", Toast.LENGTH_SHORT).show()
        } else {
            notSigned()
            Toast.makeText(activity, "Пользователь не авторизирован", Toast.LENGTH_SHORT).show()
        }
    }
}