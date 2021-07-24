package br.com.alura.aluraesporte.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EstadoAppViewModel : ViewModel() {

    /**
     * Uma vez que o LiveData não pode ser alterado de fora da classe, para conseguir mudar
     * internamente eu faço a modificação do seu get().
     * Dessa maneira ele vai receber um outro LiveData que pode ser mutável.
     *
     * Aqui estou trabalhando com uma classe que vai encapsular todos os componentes que eu quero
     * ou não quero adicionar no aplicativo. Então eu crio uma inner classe que vai manter a configuração
     * de qualquer componente visual que eu queira usar.
     */
    val appBar: LiveData<ComponentesVisuais> get() = _componentes

    private var _componentes: MutableLiveData<ComponentesVisuais> =
        MutableLiveData<ComponentesVisuais>().also {
        it.value = temComponentes
    }

    /**
     * Apresenta ou não apresenta os componentes visuais
     */
    var temComponentes: ComponentesVisuais = ComponentesVisuais()
        set(value) {
            field = value
            _componentes.value = value
        }
}

/**
 * Essa classe é responsável por buscar as configurações dos componentes visuais.
 * Appnar bottom são false de início.
 */
class ComponentesVisuais(
    val appBar: Boolean = false,
    val bottomNavigation: Boolean = false
)
