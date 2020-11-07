<#import "parts/common.ftlh" as c>
<#import "parts/messageEdit.ftlh" as me>
<#import "parts/messageList.ftlh" as ml>
<#include "parts/security.ftlh">

<@c.page userPage.username false>

    <div class="text-center">
        <h4>${userPage.username}</h4>
    </div>

    <#if !isCurrentUser>
        <div class="mb-2">
        <#if isSubscriber>
            <a class="btn btn-light" href="/user/${userPage.id}/unsubscribe">Unsubscribe</a>
        <#else>
            <a class="btn btn-primary" href="/user/${userPage.id}/subscribe">Subscribe</a>
        </#if>
        </div>
    </#if>

    <div class="ml-2 mt-1">
        <a href="/user/${userPage.id}/subscriptions">
            <div class="row">
                <div class="col-sm-1">
                    <h6>Subscriptions</h6>
                </div>
                <div class="col ml-3">
                    <h6>${subscriptionsCount}</h6>
                </div>
            </div>
        </a>
        <a href="/user/${userPage.id}/subscribers">
            <div class="row">
                <div class="col-sm-1">
                    <h6>Subscribers</h6>
                </div>
                <div class="col ml-3">
                    <h6>${subscribersCount}</h6>
                </div>
            </div>
        </a>
    </div>

    <#if (userPage.id == currentUserId || isAdmin) && message??>
        <@me.messageBox true />
    <#else>
        <@ml.messageList />
    </#if>

</@c.page>