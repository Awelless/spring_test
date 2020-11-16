<#macro registration>

<form action="/registration" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <h6>Email</h6>
    <div class="form-group row">
        <div class="col-sm-3">
            <input type="text" class="form-control ${(emailError??)?string('is-invalid', '')}"
                   name="email" value="<#if user??>${user.email}</#if>" placeholder="email" />
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
    </div>

    <h6>Username</h6>
    <div class="form-group row">
        <div class="col-sm-3">
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   name="username" value="<#if user??>${user.username}</#if>" placeholder="username" />
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
                   name="password" placeholder="password" />
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
    </div>

    <h6>Re-enter password</h6>
    <div class="form-group row">
        <div class="col-sm-3">
            <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}"
                   name="password2" placeholder="re-enter the password" />
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-3">
            <div class="g-recaptcha" data-sitekey="6LculN4ZAAAAAP_04XPIlHPKfygJ1BdOXLXctQk9"></div>
        </div>
    </div>

    <div class="row">
        <button type="submit" class="btn btn-primary ml-3">Create</button>
        <div class="btn btn-light ml-2">
            <a href="/login">Sign In</a>
        </div>
    </div>
</form>

</#macro>