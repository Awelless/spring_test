<#include "security.ftlh">
<#import "login.ftlh" as l>

<#macro navbar isLoginRegisterForm>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Sweater</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if curUser??>
            <li class="nav-item active">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/profile">Profile</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/user-messages/${curUserId}">My messages</a>
            </li>
            </#if>
            <#if isAdmin>
            <li class="nav-item active">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
        </ul>

        <#if !isLoginRegisterForm>
            <#if curUsername = "">
                <a href="/login"
                   role="button" class="btn btn-primary mr-2">Sign In</a>
                <div class="btn btn-light">
                    <a href="/registration">Sign Up</a>
                </div>
            <#else>
                <div class="nav-item active mr-3">${curUsername}</div>
                <@l.logout />
            </#if>
        </#if>

    </div>
</nav>

</#macro>