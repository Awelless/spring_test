<#import "parts/common.ftlh" as c>

<@c.page "Search results for ${pattern}" false>

    <div class="text-center">
        <h4>Search</h4>
    </div>

    <div class="my-3">
        <form class="form-inline" method="get" action="/search">
            <div class="form-group">
                <input type="text" class="form-control"
                       value="${pattern}" name="pattern" placeholder="Search" />
            </div>
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>

    <ul class="list-group">
        <#list users as user>
        <li class="list-group-item">
            <a href="/user/${user.id}">${user.username}</a>
        </li>
        <#else>
        No users
        </#list>
    </ul>

</@c.page>