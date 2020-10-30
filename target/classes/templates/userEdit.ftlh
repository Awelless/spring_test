<#import "/parts/common.ftlh" as c>

<@c.page>

    User edit

    <form method="post" action="/user">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="hidden" name="userId" value="${user.id}" />
        <input type="text" name="username" value="${user.username}"/ >
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")} />${role}</label>
            </div>
        </#list>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>

</@c.page>