<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page "Sing In" true>

    <div class="mb-3">
        <h4>Sign In</h4>
    </div>

    <#if alert??>
        <div class="alert alert-${alertType}">
            ${alert}
        </div>
    </#if>

    <#if loginError?? && Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <#assign
            defMessage = Session.SPRING_SECURITY_LAST_EXCEPTION.message
        >
        <#if defMessage == 'Bad credentials'>
            <#assign
                message = 'Incorrect username or password'
            >
        <#elseif defMessage == 'User is disabled'>
            <#assign
                message = 'Account is not verified. Check your email'
            >
        <#else>
            <#assign
                message = 'Something went wrong. Try again'
            >
        </#if>

        <div class="alert alert-danger" role="alert">
            ${message}
        </div>
    </#if>

    <@l.login />

</@c.page>
