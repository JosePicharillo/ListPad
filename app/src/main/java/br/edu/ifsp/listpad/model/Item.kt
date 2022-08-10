package br.edu.ifsp.listpad.model

import java.io.Serializable

class Item(
    var id: Int? = null,
    var descricao: String = "",
    var flag: Int? = null,
    var idList: Int? = null
) : Serializable