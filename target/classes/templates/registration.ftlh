<#import "parts/common.ftlh" as c>
<#import "parts/registration.ftlh" as r>

<@c.page true>

    <div class="mb-3">
        <h5>Sign Up</h5>
    </div>

    <#if captchaError??>
    <div class="alert alert-danger">
        ${captchaError}
    </div>
    </#if>

    <@r.registration/>

</@c.page>