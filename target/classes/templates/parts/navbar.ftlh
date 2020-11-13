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
            <#if currentUser??>
            <li class="nav-item active">
                <a class="nav-link" href="/news">News</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/messages">All Messages</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/search">Search</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/user/${currentUserId}">My profile</a>
            </li>
            </#if>
            <#if isAdmin>
            <li class="nav-item active">
                <a class="nav-link" href="/adminpanel">Admin Panel</a>
            </li>
            </#if>
        </ul>

        <#if !isLoginRegisterForm>
            <#if currentUsername = "">
                <a href="/login"
                   role="button" class="btn btn-primary mr-2">Sign In</a>
                <div class="btn btn-light">
                    <a href="/registration">Sign Up</a>
                </div>
            <#else>
                <div class="nav-item active">${currentUsername}</div>
                <div class="nav-item active">
                    <a class="nav-link" href="/settings">Settings</a>
                </div>
                <@l.logout />
            </#if>
        </#if>

    </div>
</nav>

</#macro>