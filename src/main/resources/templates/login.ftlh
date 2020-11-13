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

    <#if loginError??>
        <div class="alert alert-danger" role="alert">
            ${loginError}
        </div>
    </#if>

    <@l.login/>

</@c.page>
