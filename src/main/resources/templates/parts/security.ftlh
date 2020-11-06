<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        currentUser     = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        currentUsername = currentUser.getUsername()
        isAdmin         = currentUser.isAdmin()
        currentUserId   = currentUser.getId()
    >
<#else>
    <#assign
        currentUsername = ""
        isAdmin         = false
        currentUserId   = -1
    >
</#if>