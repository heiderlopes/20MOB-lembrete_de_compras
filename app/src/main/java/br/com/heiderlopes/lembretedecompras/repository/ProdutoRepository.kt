package br.com.heiderlopes.lembretedecompras.repository

import androidx.lifecycle.LiveData
import br.com.heiderlopes.lembretedecompras.dao.ProdutoDao
import br.com.heiderlopes.lembretedecompras.models.Produto

class ProdutoRepository(private val produtoDao: ProdutoDao) {

    val produtos: LiveData<List<Produto>> = produtoDao.getListaDeProdutos()

    suspend fun insert(produto: Produto) {
        produtoDao.insert(produto)
    }

    suspend fun apagar() {
        produtoDao.deleteAll()
    }
}
