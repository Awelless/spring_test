<#import "parts/common.ftlh" as c>

<@c.page "My Settings" false>

    <div class="text-center">
        <h4>My Settings</h4>
    </div>

    <#if emailMessage?? && emailMessageType??>
        <div class="alert ${emailMessageType} mb-3">
            ${emailMessage}
        </div>
    </#if>

    <#if passwordMessage?? && passwordMessageType??>
        <div class="alert ${passwordMessageType} mb-3">
            ${passwordMessage}
        </div>
    </#if>

    <form method="post" action="/settings">
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
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="row">
            <button type="submit" class="btn btn-primary ml-3">Save</button>
        </div>
    </form>

</@c.page>
