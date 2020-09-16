package br.com.heiderlopes.lembretedecompras.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.heiderlopes.lembretedecompras.dao.LembretedeComprasRoomDatabase
import br.com.heiderlopes.lembretedecompras.models.Produto
import br.com.heiderlopes.lembretedecompras.repository.ProdutoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProdutoRepository

    val produtos: LiveData<List<Produto>>

    init {
        val produtoDao = LembretedeComprasRoomDatabase.getDatabase(application).produtoDao()
        repository = ProdutoRepository(produtoDao)
        produtos = repository.produtos
    }

    fun insert(produto: Produto) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(produto)
    }

    fun apagar() = viewModelScope.launch(Dispatchers.IO) {
        repository.apagar()
    }
}
