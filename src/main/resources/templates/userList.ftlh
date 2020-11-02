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
            <td><a href="/user/${user.id}">Edit</a></td>
        </tr>
        </#list>
        </tbody>
    </table>

</@c.page>