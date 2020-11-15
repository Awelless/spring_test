<ul class="list-group">
    <#list users as user>
        <li class="list-group-item">
            <a href="/user/${user.id}">${user.username}</a>
        </li>
    <#else>
        No users
    </#list>
</ul>