package com.company.jmixhello.security

import io.jmix.security.model.SecurityScope
import io.jmix.security.role.annotation.ResourceRole
import io.jmix.security.role.annotation.SpecificPolicy
import io.jmix.securityflowui.role.UiMinimalPolicies
import io.jmix.securityflowui.role.annotation.ViewPolicy

@ResourceRole(name = "UI: minimal access", code = UiMinimalRole.CODE, scope = [SecurityScope.UI])
interface UiMinimalRole : UiMinimalPolicies {

    companion object {
        const val CODE = "ui-minimal"
    }

    @ViewPolicy(viewIds = ["tsu_MainView"])
    fun main()

    @ViewPolicy(viewIds = ["tsu_LoginView"])
    @SpecificPolicy(resources = ["ui.loginToUi"])
    fun login()
}
