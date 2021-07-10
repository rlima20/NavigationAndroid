package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private val CHAVE_LOGADO = "LOGADO"

class LoginRepository(private val preference: SharedPreferences) {

    fun loga() = salva(true)

    fun desloga() = salva(false)

    fun estaLogado(): Boolean = preference
        .getBoolean(CHAVE_LOGADO, false)

    private fun salva(estado: Boolean) {
        preference.edit {
            putBoolean(CHAVE_LOGADO, estado)
        }
    }
}
