<#include "security.ftlh">
<#import "pager.ftlh" as p>

<#macro messageList>

<div class="mt-3">
    <@p.pager "/messages" page />
</div>

<div class="card-columns" id="message-list">

    <#list page.content as message>

        <div class="card mb-3" data-id="${message.id}">
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top" />
            </#if>
            <div class="m-2">
                <span>${message.text}</span><br />
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <div class="row">
                    <div class="col-sm-3 align-self-center">
                        <a href="/messages/${message.id}/like">
                            <#if message.meLiked>
                                <i class="fas fa-heart"></i>
                            <#else>
                                <i class="far fa-heart"></i>
                            </#if>
                        ${message.likes}
                        </a>
                    </div>
                    <div class="col align-self-center">
                        <a href="/user/${message.author.id}">${message.authorName}</a>
                    </div>
                    <#if message.author.id == currentUserId || isAdmin>
                    <div class="col">
                        <div class="row float-right">
                            <div class="btn btn-sm btn-light">
                                <a href="/user/${message.author.id}?message=${message.id}">
                                    <i>Edit</i>
                                </a>
                            </div>
                            <div class="ml-2 mr-1">
                                <form method="post" action="/user/${message.author.id}/${message.id}/delete">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                    <button class="btn btn-sm btn-danger" type="submit">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    </#if>
                </div>
            </div>
        </div>

    <#else>
        No messages
    </#list>
</div>

<@p.pager "/messages" page />

</#macro>