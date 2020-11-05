<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        curUser     = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        curUsername = curUser.getUsername()
        isAdmin     = curUser.isAdmin()
        curUserId   = curUser.getId()
    >
<#else>
    <#assign
        curUsername = ""
        isAdmin     = false
        curUserId   = -1
    >
</#if>