<#import "parts/common.ftlh" as c>

<@c.page userPage.username false>

    <div class="text-center">
        <h4>${userPage.username}</h4>
    </div>

    <div>${pageType}</div>

    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item">
                <a href="/user/${user.id}">${user.username}</a>
            </li>
        </#list>
    </ul>

</@c.page>