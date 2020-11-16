<#import "parts/common.ftlh" as c>
<#import "parts/registration.ftlh" as r>

<@c.page "Sign Up" true>

    <div class="mb-3">
        <h4>Sign Up</h4>
    </div>

    <#if captchaError??>
        <div class="alert alert-danger">
            ${captchaError}
        </div>
    </#if>

    <@r.registration/>

</@c.page>