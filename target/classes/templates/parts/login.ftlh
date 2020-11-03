<#macro login>

<form action="/login" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <h6>Username</h6>
    <div class="form-group row">
        <div class="col-sm-3">
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   name="username" value="<#if usernameLogin??>${usernameLogin}</#if>" placeholder="username"/>
            <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
            </#if>
        </div>
    </div>

    <h6>Password</h6>
    <div class="form-group row">
        <div class="col-sm-3">
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                   name="password" placeholder="password"/>
            <#if passwordError??>
            <div class="invalid-feedback">
                ${passwordError}
            </div>
            </#if>
        </div>
    </div>

    <div class="row">
        <button type="submit" class="btn btn-primary ml-3">Sign In</button>
        <div class="btn btn-light ml-2">
            <a href="/registration">Sign Up</a>
        </div>
    </div>
</form>
</#macro>

<#macro logout>
<form method="post" action="/logout">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit" class="btn btn-primary">Sign Out</button>
</form>
</#macro>