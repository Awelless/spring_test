<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        curUser     = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        curUsername = curUser.getUsername()
        isAdmin     = curUser.isAdmin()
    >
<#else>
    <#assign
        curUsername = ""
        isAdmin     = false
    >
</#if>