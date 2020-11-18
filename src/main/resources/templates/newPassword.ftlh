<#import "parts/common.ftlh" as c>

<@c.page "Password reset" true>

    <div class="mb-3 text-center">
        <h4>Enter new password</h4>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <form action="/reset" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="hidden" name="userId" value="${user.id}" />

                <h6>New password</h6>
                <div class="form-group row">
                    <div class="col">
                        <input type="password" class="form-control ${(newPasswordError??)?string('is-invalid', '')}"
                               name="newPassword" />
                        <#if newPasswordError??>
                            <div class="invalid-feedback">
                                ${newPasswordError}
                            </div>
                        </#if>
                    </div>
                </div>

                <h6>Confirm password</h6>
                <div class="form-group row">
                    <div class="col">
                        <input type="password" class="form-control ${(passwordConfirmError??)?string('is-invalid', '')}"
                               name="passwordConfirm" />
                        <#if passwordConfirmError??>
                            <div class="invalid-feedback">
                                ${passwordConfirmError}
                            </div>
                        </#if>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <button type="submit" class="btn btn-primary">Reset</button>
                    <div class="btn btn-light ml-2">
                        <a href="/login">Sign In</a>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-sm-4"></div>
    </div>


</@c.page>