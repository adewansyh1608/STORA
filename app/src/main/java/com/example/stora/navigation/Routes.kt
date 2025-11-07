package com.example.stora.navigation
object Routes {
    const val INVENTORY_SCREEN = "inventory"

    const val HOME_SCREEN = "home"
    const val DETAIL_SCREEN = "detail/{itemId}"
    const val ADD_ITEM_SCREEN = "add_item"

    const val AUTH_SCREEN = "auth"
    const val LOANS_SCREEN = "loans"

    fun detailScreen(itemId: String) = "detail/$itemId"
}