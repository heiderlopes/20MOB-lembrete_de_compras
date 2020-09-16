package br.com.heiderlopes.lembretedecompras.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.heiderlopes.lembretedecompras.R
import br.com.heiderlopes.lembretedecompras.models.Produto

class ProdutoListaAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ProdutoListaAdapter.ProdutoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var produtos = emptyList<Produto>()

    inner class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val produtoItemView: TextView = itemView.findViewById(R.id.tvProduto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val itemView = inflater.inflate(R.layout.produto_item, parent, false)
        return ProdutoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.produtoItemView.text = produto.nome
    }

    override fun getItemCount() = produtos.size

    fun setProdutos(produtos: List<Produto>) {
        this.produtos = produtos
        notifyDataSetChanged()
    }


}
