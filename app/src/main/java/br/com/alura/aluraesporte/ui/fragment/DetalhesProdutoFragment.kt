package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.ui.activity.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.DetalhesProdutoViewModel
import kotlinx.android.synthetic.main.detalhes_produto.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Recebendo argumentos com o safeArgs
 * Vamos ter que acessar os argumentos do safeArgs colocar dentro de uma variável chamada argumento
 * juntamente com um property delegated que é a ideia do lazy mas é justamente feito para os argumentos
 * de navegação. Então vamos ter um byNavArgs. Com esse NavArgs vamos indicar os argumentos que esperamos
 * que é da DetalhesProdutoFragmentArgs. Dessa maneira vamos ter acesso aos argumentos que definimos no grafo
 * de navegação para o detalhes de produto fragment.
 */


class DetalhesProdutoFragment : BaseFragment() {

    private val argumento by navArgs<DetalhesProdutoFragmentArgs>()

    private val produtoId by lazy {
        argumento.produtoId
    }
    private val viewModel: DetalhesProdutoViewModel by viewModel { parametersOf(produtoId) }

    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    private val controlador by lazy{
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.detalhes_produto,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temAppBar = true
        buscaProduto()
        configuraBotaoComprar()
    }

    private fun configuraBotaoComprar() {
        detalhes_produto_botao_comprar.setOnClickListener {
            viewModel.produtoEncontrado.value?.let{
                vaiParaPagamento()
            }
        }
    }

    private fun vaiParaPagamento() {
        val direcoes = DetalhesProdutoFragmentDirections.actionDetalhesProdutoToPagamento(produtoId)
        controlador.navigate(direcoes)
    }

    private fun buscaProduto() {
        viewModel.produtoEncontrado.observe(viewLifecycleOwner, Observer {
            it?.let { produto ->
                detalhes_produto_nome.text = produto.nome
                detalhes_produto_preco.text = produto.preco.formatParaMoedaBrasileira()
            }
        })
    }
}