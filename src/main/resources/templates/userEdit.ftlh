<#import "parts/common.ftlh" as c>

<@c.page "Editing ${user.username}" false>

    <h4 class="mb-3">Editing ${user.username}</h4>

    <form method="post" action="/userlist">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="hidden" name="userId" value="${user.id}" />

        <h6>Username</h6>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="text" class="form-control"
                       name="username" value="${user.username}" />
            </div>
        </div>

        <h6>Email</h6>
        <div class="form-group row">
            <div class="col-sm-3 ml-1">
                ${user.email}
            </div>
        </div>

        <h6>Roles</h6>
        <div class="mb-2 ml-4">
            <#list roles as role>
            <div>
                <input type="checkbox" class="form-check-input" id="checkbox${role}"
                       name="${role}" ${user.roles?seq_contains(role)?string("checked", "")} />
                <label class="form-check-label" for="checkbox${role}">${role}</label>
            </div>
            </#list>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
        <div class="btn btn-light">
            <a href="/userlist">Back</a>
        </div>
    </form>

</@c.page>