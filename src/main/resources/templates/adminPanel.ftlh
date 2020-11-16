<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page "Users" false>

    <div class="text-center">
        <h4>List of Users</h4>
    </div>

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

        <#if user.id == currentUserId>
            <#assign
                trType = "primary"
            >
        <#else>
            <#assign
                trType = "default"
            >
        </#if>

        <tr scope="row" class="table-${trType}">
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>
                <#list user.roles as role>
                ${role}<#sep>,
            </#list>
            </td>
            <td>
                <#if !user.roles?seq_contains('ADMIN') || user.id == currentUserId>
                <div class="btn btn-light btn-sm">
                    <a href="/adminpanel/${user.id}">Edit</a>
                </div>
                </#if>
            </td>
            <td>
                <#if !user.roles?seq_contains('ADMIN')>
                <form method="post" action="/adminpanel/delete">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <input type="hidden" name="userId" value="${user.id}" />
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
                </#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>

</@c.page>