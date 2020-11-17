<#import "parts/common.ftlh" as c>

<@c.page "My Settings" false>

    <div class="text-center">
        <h4>My Settings</h4>
    </div>

    <#if emailAlert?? && emailAlertType??>
        <div class="alert alert-${emailAlertType} mb-3">
            ${emailAlert}
        </div>
    </#if>

    <#if passwordAlert?? && passwordAlertType??>
        <div class="alert alert-${passwordAlertType} mb-3">
            ${passwordAlert}
        </div>
    </#if>

    <form method="post" action="/settings">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <h6>Current password:</h6>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       name="password" placeholder="current password" />
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <h6>Email:</h6>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="text" class="form-control ${(emailError??)?string('is-invalid', '')}"
                       name="email" placeholder="set new email" value="<#if user??>${user.email}</#if>" />
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>

        <h6>New password:</h6>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="password" class="form-control ${(newPasswordError??)?string('is-invalid', '')}"
                       name="newPassword" placeholder="set new password" />
                <#if newPasswordError??>
                    <div class="invalid-feedback">
                        ${newPasswordError}
                    </div>
                </#if>
            </div>
        </div>

        <h6>Confirm password:</h6>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="password" class="form-control ${(passwordConfirmError??)?string('is-invalid', '')}"
                       name="passwordConfirm" placeholder="re-enter the password" />
                <#if passwordConfirmError??>
                    <div class="invalid-feedback">
                        ${passwordConfirmError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="row">
            <button type="submit" class="btn btn-primary ml-3">Save</button>
        </div>
    </form>

</@c.page>
