<#import "parts/common.ftlh" as c>

<@c.page "My Settings" false>

    <div class="text-center">
        <h4>My Settings</h4>
    </div>

    <#if message??>
    <div class="alert alert-success mb-3">
        ${message}
    </div>
    </#if>

    <form method="post" action="/settings">
        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Email:</label>
            <div class="col-sm-3">
                <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                       name="email" placeholder="set new email" value="<#if user??>${user.email}</#if>" />
            </div>
            <#if emailError??>
            <div class="invalid-feedback">
                ${emailError}
            </div>
            </#if>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Password:</label>
            <div class="col-sm-3">
                <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       name="password" placeholder="set new password" />
            </div>
            <#if passwordError??>
            <div class="invalid-feedback">
                ${passwordError}
            </div>
            </#if>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Confirm password:</label>
            <div class="col-sm-3 mt-2">
                <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       name="password2" placeholder="re-enter the password" />
                <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
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
