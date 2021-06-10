package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private val CHAVE_LOGADO = "LOGADO"

class LoginRepository(private val preference: SharedPreferences) {

    /**
     * Função que vai logar
     */
    fun loga(){
        preference.edit{
            putBoolean(CHAVE_LOGADO, true)
        }
    }

    /**
     * Verifica se está logado
     */
    fun estaLogado(): Boolean = preference
        .getBoolean(CHAVE_LOGADO, false)

}
