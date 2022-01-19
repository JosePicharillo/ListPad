package br.edu.ifsp.listpad.model

import java.io.Serializable

class Tarefa(
    var id: Int? = null,
    var nome: String = "",
    var descricao: String = "",
    var flag: Int? = null,
    var categoria: String = ""
) : Serializable