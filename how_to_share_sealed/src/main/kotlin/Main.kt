package org.example

import arrow.core.Either
import arrow.core.right

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

data object OK
sealed class 新規訂正ワーニング {
    data object 共通ワーニング1 : 新規訂正ワーニング()
}

sealed class 画面別ワーニング {
    data object 画面別ワーニング1 : 画面別ワーニング()
}

data class 新規エラー(val 共通エラー: 新規訂正エラー, val 共通エラー2: 訂正CFエラー)
data class 新規チェックOk(val 共通ワーニング: 新規訂正ワーニング?, val 成功: OK? = OK) {
    fun hasWarning(): Boolean {
        return 共通ワーニング != null
    }

    data class 訂正エラー(val 共通エラー: 新規訂正エラー, val エラー2: 訂正CFエラー, val エラー3: 訂正個別エラー)
    data class チェックサービスエラー結果(val エラー1: 新規訂正エラー, val サービスエラー: サービスエラー)
    data class チェックサービスOK結果(
        val 共通ワーニング: 新規訂正ワーニング?,
        val 画面別ワーニング: 画面別ワーニング?,
        val 成功: OK? = OK
    )

    fun test(): Either<新規エラー, 新規チェックOk> {
        return 新規チェックOk(新規訂正ワーニング.共通ワーニング1, OK).right()
    }

    fun main() {
        val 新規登録チェックエラー = 新規エラー(新規訂正エラー.件数0件, 訂正CFエラー.決済済みエラー)
        when (新規登録チェックエラー.共通エラー) {
            新規訂正エラー.件数0件 -> println("件数0件")
            新規訂正エラー.検索時異なる -> println("検索時異なる")
        }

        when (新規登録チェックエラー.共通エラー2) {
            訂正CFエラー.決済済みエラー -> println("決済済みエラー")
        }

        val サービスレスポンス =
            チェックサービスエラー結果(新規登録チェックエラー.共通エラー, サービスエラー.サービスエラー1)

        when (val result = test()) {
            is Either.Left -> TODO("呼び出し元に返す")
            is Either.Right -> {
                //画面固有の処理を実施

                //前回処理ワーニング引き継いで返す
                when (val warnings = result.value.共通ワーニング) {
                    新規訂正ワーニング.共通ワーニング1 -> チェックサービスOK結果(
                        warnings,
                        画面別ワーニング.画面別ワーニング1
                    )

                    null -> チェックサービスOK結果(
                        null,
                        null,
                        OK
                    )
                }

            }
        }
    }
}