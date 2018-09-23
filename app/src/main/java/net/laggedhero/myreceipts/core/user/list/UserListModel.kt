package net.laggedhero.myreceipts.core.user.list

import net.laggedhero.myreceipts.core.data.model.User

data class UserListModel(
    val users: List<User>,
    val isLoading: Boolean,
    val errorMessage: String? = null
) {
    fun getUserNameList() = users.map { it.name }
}
