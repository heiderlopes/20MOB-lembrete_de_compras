package br.com.heiderlopes.lembretedecompras.ui.main

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.heiderlopes.lembretedecompras.R
import br.com.heiderlopes.lembretedecompras.models.Produto
import br.com.heiderlopes.lembretedecompras.ui.novoproduto.NovoProdutoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private val novoProdutoRequestCode = 1
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rvProdutos)
        val adapter = ProdutoListaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.produtos.observe(this, Observer { produtos ->
            // Update the cached copy of the words in the adapter.
            produtos?.let { adapter.setProdutos(it) }
        })

        val fabNovoProduto = findViewById<FloatingActionButton>(R.id.fabNovoProduto)
        fabNovoProduto.setOnClickListener {
            val intent = Intent(this@MainActivity, NovoProdutoActivity::class.java)
            startActivityForResult(intent, novoProdutoRequestCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btDelete -> {
                confirmaExclusao().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    
    private fun confirmaExclusao(): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Lembrete de Compras")
            .setMessage("Deseja apagar sua lista?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Apagar") { dialog, _ ->
                mainViewModel.apagar()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") {
                    dialog, _ -> dialog.dismiss()
            }
            .create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == novoProdutoRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NovoProdutoActivity.EXTRA_REPLY)?.let {
                val produto = Produto(it)
                mainViewModel.insert(produto)
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Nenhum produto informado",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}



