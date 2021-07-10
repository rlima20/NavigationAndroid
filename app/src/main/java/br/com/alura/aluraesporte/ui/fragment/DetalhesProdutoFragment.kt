package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.ui.viewmodel.DetalhesProdutoViewModel
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.detalhes_produto.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesProdutoFragment : Fragment() {

    /**
     * Recebendo argumentos com o safeArgs
     * Vamos ter que acessar os argumentos do safeArgs colocar dentro de uma variável chamada argumento
     * juntamente com um property delegated que é a ideia do lazy mas é justamente feito para os argumentos
     * de navegação. Então vamos ter um byNavArgs. Com esse NavArgs vamos indicar os argumentos que esperamos
     * que é da DetalhesProdutoFragmentArgs. Dessa maneira vamos ter acesso aos argumentos que definimos no grafo
     * de navegação para o detalhes de produto fragment.
     */
    private val argumento by navArgs<DetalhesProdutoFragmentArgs>()

    /**
     * Já aqui eu digo que a partir do argumento eu tenho acesso ao produtoId.
     */
    private val produtoId by lazy {
        argumento.produtoId
    }
    private val viewModel: DetalhesProdutoViewModel by viewModel { parametersOf(produtoId) }
    private val loginViewModel: LoginViewModel by viewModel()

    private val controlador by lazy{
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menu_lista_produtos_deslogar){
            loginViewModel.desloga()
            vaiParaTelaLogin()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun vaiParaTelaLogin() {
        val direcao = DetalhesProdutoFragmentDirections
            .actionGlobalLogin()
        controlador.navigate(direcao)
    }
}