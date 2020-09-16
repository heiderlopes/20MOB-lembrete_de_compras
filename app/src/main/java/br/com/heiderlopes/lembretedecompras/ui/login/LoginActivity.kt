package br.com.heiderlopes.lembretedecompras.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.heiderlopes.lembretedecompras.R
import br.com.heiderlopes.lembretedecompras.models.RequestState
import br.com.heiderlopes.lembretedecompras.models.Usuario
import br.com.heiderlopes.lembretedecompras.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var animacaoDoMascote : Animation
    private lateinit var animacaoDoFormulario : Animation

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        iniciarAnimacao()
        esconderTeclado()

        iniciarListener()
        iniciarViewModel()
        iniciarObserver()

        loginViewModel.getUsuarioLogado()
    }

    private fun iniciarListener() {
        btLogin.setOnClickListener {
            loginViewModel.logar(
                Usuario(etEmail.text.toString(),
                etPassword.text.toString())
            )
        }

        etPassword.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                ivLogin.speed = 2f
                ivLogin.setMinAndMaxProgress(0.0f, 0.65f)
            } else {
                ivLogin.speed = 1f
                ivLogin.setMinAndMaxProgress(0.65f, 1.0f)
            }
            ivLogin.playAnimation()
        }
    }

    private fun iniciarViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun iniciarObserver() {
        loginViewModel.loginState.observe(this, Observer { requestState ->

            when (requestState) {
                is RequestState.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is RequestState.Error -> {
                    val animShake = AnimationUtils.loadAnimation(this, R.anim.shake)
                    containerLogin.startAnimation(animShake)
                    tvPasswordFeedback.text = requestState.throwable.message
                }
                is RequestState.Loading -> {
                    Toast.makeText(this, "Carregando", Toast.LENGTH_LONG).show()
                }
            }

        })

        loginViewModel.usuarioLogadoState.observe(this, Observer { requestState ->

            when (requestState) {
                is RequestState.Success -> {
                    etEmail.setText(requestState.data)
                }
                is RequestState.Error -> {
                    etEmail.setText("")
                }
                is RequestState.Loading -> { }
            }

        })
    }

    private fun iniciarAnimacao() {
        animacaoDoMascote = AnimationUtils.loadAnimation(this,
            R.anim.frombottom
        )
        animacaoDoFormulario = AnimationUtils.loadAnimation(this,
            R.anim.frombottom2
        )

        containerLogin.startAnimation(animacaoDoMascote)
        ivLogin.startAnimation(animacaoDoFormulario)
    }

    private fun esconderTeclado() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }
}
