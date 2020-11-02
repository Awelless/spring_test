<#import "/parts/common.ftlh" as c>

<@c.page>

    <h5>
        List of Users
    </h5>

    <table class="table table-sm table-hover table-fixed">
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Roles</th>
                <th></th>
                <th></th>
            </tr>
        </thead>

        <tbody>
        <#list users as user>
        <tr scope="row">
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>
                <#list user.roles as role>
                ${role}<#sep>,
            </#list>
            </td>
            <td>
                <div class="btn btn-light">
                    <a href="/user/${user.id}">Edit</a>
                </div>
            </td>
            <td>
                <form method="post" action="/user/delete">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <input type="hidden" name="userId" value="${user.id}" />
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>

</@c.page>