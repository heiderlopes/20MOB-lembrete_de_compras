package br.com.heiderlopes.lembretedecompras.ui.novoproduto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import br.com.heiderlopes.lembretedecompras.R

class NovoProdutoActivity : AppCompatActivity() {

    private lateinit var etProduto: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_produto)
        etProduto = findViewById(R.id.etProduto)

        val button = findViewById<Button>(R.id.btSalvar)
        button.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(etProduto.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = etProduto.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "REPLY"
    }
}
