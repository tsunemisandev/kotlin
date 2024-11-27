package org.example

sealed class 新規訂正エラー {
    data object 件数0件 : 新規訂正エラー()
    data object 検索時異なる : 新規訂正エラー()
}

sealed class 訂正CFエラー {
    data object 決済済みエラー : 訂正CFエラー()
}

sealed class 訂正個別エラー {
    data object 訂正個別エラー1
}

sealed class サービスエラー {
    data object サービスエラー1 : サービスエラー()
}

data class 新規エラー(val 共通エラー: 新規訂正エラー, val 共通エラー2: 訂正CFエラー)
data class 訂正エラー(val 共通エラー: 新規訂正エラー, val エラー2: 訂正CFエラー, val エラー3: 訂正個別エラー)

data class サービスレスポンス(val エラー1: 新規訂正エラー, val サービスエラー: サービスエラー)

fun main() {
    val 新規登録チェックエラー = 新規エラー(新規訂正エラー.件数0件, 訂正CFエラー.決済済みエラー)
    when (新規登録チェックエラー.共通エラー) {
        新規訂正エラー.件数0件 -> println("件数0件")
        新規訂正エラー.検索時異なる -> println("検索時異なる")
    }

    when (新規登録チェックエラー.共通エラー2) {
        訂正CFエラー.決済済みエラー -> println("決済済みエラー")
    }

    val サービスレスポンス = サービスレスポンス(新規登録チェックエラー.共通エラー, サービスエラー.サービスエラー1)
}