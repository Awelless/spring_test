<#macro login>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

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

            <form action="/login" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />

                <h6>Username</h6>
                <div class="form-group row">
                    <div class="col">
                        <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                               name="username" value="<#if usernameLogin??>${usernameLogin}</#if>"/>
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
                <div class="form-group row">
                    <small class="col text-right">
                        <a href="/forgot_password">Forgot password?</a>
                    </small>
                </div>

                <div class="row justify-content-center">
                    <button type="submit" class="btn btn-primary">Sign In</button>
                    <div class="btn btn-light ml-2">
                        <a href="/registration">Sign Up</a>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-sm-4"></div>
    </div>

</#macro>

<#macro logout>

<form method="post" action="/logout">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit" class="btn btn-primary">Sign Out</button>
</form>

</#macro>