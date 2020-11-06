<#include "security.ftlh">

<#macro messageList>

<#list messages as message>

    <div class="card my-3">
        <#if message.filename??>
            <img src="/img/${message.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <span>${message.text}</span><br />
            <i>#${message.tag}</i>
        </div>
        <div class="card-footer text-muted">
            <div class="row">
                <div class="col">
                    <a href="/user/${message.author.id}">${message.authorName}</a>
                </div>
                <#if message.author.id == currentUserId || isAdmin>
                <div class="col text-right">
                    <a href="/user/${message.author.id}?message=${message.id}"><i>Edit</i></a>
                    <a class="btn btn-danger btn-sm" href="/user/${message.author.id}/${message.id}/delete">Delete</a>
                </div>
                </#if>
            </div>
        </div>
    </div>

<#else>
    No messages
</#list>

</#macro>