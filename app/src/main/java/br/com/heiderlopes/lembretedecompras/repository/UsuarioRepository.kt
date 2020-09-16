package br.com.heiderlopes.lembretedecompras.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.heiderlopes.lembretedecompras.models.RequestState
import br.com.heiderlopes.lembretedecompras.models.Usuario

class UsuarioRepository(val context: Context) {


    fun logar(usuario: Usuario): MutableLiveData<RequestState<Boolean>> {

        val response = MutableLiveData<RequestState<Boolean>>()

        response.value = RequestState.Loading

        if (usuario.email == "usuario@fiap.com.br" &&
            usuario.senha == "123456"
        ) {
            val pref = context.getSharedPreferences("lembretedecompras", 0)
            val editor = pref.edit()
            editor.putString("email", usuario.email)
            editor.apply()
            response.value = RequestState.Success(true)
        } else {
            response.value = RequestState.Error(Throwable("Usuário ou senha inválido"))
        }

        return response
    }

    fun getUsuarioLogado(): LiveData<RequestState<String>> {
        val response = MutableLiveData<RequestState<String>>()

        val pref = context.getSharedPreferences("lembretedecompras", 0)
        val email = pref.getString("email", "") ?: ""
        response.value = RequestState.Success(email)

        return response
    }

}