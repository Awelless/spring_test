<#include "security.ftlh">

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
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == curUserId>
                <div class="text-right">
                    <a href="/user-messages/${message.author.id}?message=${message.id}"><i>Edit</i></a>
                </div>
                </#if>
        </div>
    </div>
<#else>
    No messages
</#list>