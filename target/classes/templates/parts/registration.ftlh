<#macro registration>

<div class="row">
    <div class="col-sm-4"></div>
    <div class="col-sm-4">

        <#if captchaError??>
            <div class="alert alert-danger">
                ${captchaError}
            </div>
        </#if>

        <form action="/registration" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />

            <h6>Email</h6>
            <div class="form-group row">
                <div class="col">
                    <input type="text" class="form-control ${(emailError??)?string('is-invalid', '')}"
                           name="email" value="<#if user??>${user.email}</#if>" />
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>

            <h6>Username</h6>
            <div class="form-group row">
                <div class="col">
                    <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                           name="username" value="<#if user??>${user.username}</#if>" />
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </div>
            </div>

            <h6>Password</h6>
            <div class="form-group row">
                <div class="col">
                    <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                           name="password" />
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
            </div>

            <h6>Re-enter password</h6>
            <div class="form-group row">
                <div class="col">
                    <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           name="password2" />
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row justify-content-center">
                    <div class="g-recaptcha" data-sitekey="6LculN4ZAAAAAP_04XPIlHPKfygJ1BdOXLXctQk9"></div>
            </div>

            <div class="row justify-content-center">
                <button type="submit" class="btn btn-primary">Create</button>
                <div class="btn btn-light ml-2">
                    <a href="/login">Sign In</a>
                </div>
            </div>
        </form>
    </div>

    <div class="col-sm-4"></div>
</div>

</#macro>