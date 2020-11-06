<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page "Sing In" true>

    <div class="mb-3">
        <h4>Sign In</h4>
    </div>

    <#if activationSuccess??>
        <div class="alert alert-success">
            ${activationSuccess}
        </div>
    </#if>
    <#if activationError??>
        <div class="alert alert-danger">
            ${activationError}
        </div>
    </#if>

    <#if loginError?? && Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>

    <@l.login/>

</@c.page>
