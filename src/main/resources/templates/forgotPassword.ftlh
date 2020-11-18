<#import "parts/common.ftlh" as c>

<@c.page "Password reset" true>

    <div class="mb-3 text-center">
        <h4>Enter your email</h4>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <#if alert??>
                <div class="alert alert-${alertType}">
                    ${alert}
                </div>
            </#if>

            <form action="/forgot_password" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />

                <h6>Enter account's email address:</h6>
                <div class="form-group row">
                    <div class="col">
                        <input type="text" class="form-control ${(emailError??)?string('is-invalid', '')}"
                               name="email" />
                        <#if emailError??>
                            <div class="invalid-feedback">
                                ${emailError}
                            </div>
                        </#if>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <button type="submit" class="btn btn-primary">Reset</button>
                    <div class="btn btn-light ml-2">
                        <a href="/login">Back</a>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-sm-4"></div>
    </div>

</@c.page>