<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Sweater</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <#if isAdmin>
            <li class="nav-item active">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
        </ul>

        <#if name = "">
            <form method="get" action="/login">
                <button type="submit" class="btn btn-primary">Sign In</button>
            </form>
        <#else>
            <div class="navbar-text mr-3">${name}</div>
            <@l.logout />
        </#if>


    </div>
</nav>