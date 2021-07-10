package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProdutosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProdutosViewModel
import kotlinx.android.synthetic.main.lista_produtos.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ListaProdutosFragment : BaseFragment() {

    private val viewModel: ProdutosViewModel by viewModel()
    private val adapter: ProdutosAdapter by inject()
    private val controlador by lazy{
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        buscaProdutos()
    }

    private fun buscaProdutos() {
        viewModel.buscaTodos().observe(this, Observer { produtosEncontrados ->
            produtosEncontrados?.let {
                adapter.atualiza(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.lista_produtos,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        lista_produtos_recyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = { produtoSelecionado ->
            vaiParaDetalhesDoProduto(produtoSelecionado.id)
        }
        lista_produtos_recyclerview.adapter = adapter
    }

    /**
     * Existem classes que são geradas durante o processo de build assim que tivermos o safeArgs.
     * Vamos acessar o que conhecemos como direções possíveis de um destino, que é a partir de uma
     * classe gerada para cada destino dentro do Navigation.
     * Nesse caso por exemplo na lista de produtos, vamos ter a ListaProdutosFragmentDirections.
     * A partir desse directions vamos ter disponível todas as direções possíveis desse destino, que vai ser
     * uma referênci na qual vai manter tanto o id da ação quanto também os seus argumentos.
     * Então a direção vai ser igual à essa classe passando o argumento necessário.
     * As direções do nosso ListaProdutos tem essa ação. actionListaProdutosToDetalhesProduto.
     */
    private fun vaiParaDetalhesDoProduto(produtoId: Long) {
        val direcoes = ListaProdutosFragmentDirections
            .actionListaProdutosToDetalhesProduto(produtoId)
        controlador.navigate(direcoes)
    }
}
