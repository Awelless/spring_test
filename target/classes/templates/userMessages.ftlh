<#import "parts/common.ftlh" as c>

<@c.page false>

    <#if isCurrentUser>
        <#include "parts/messageEdit.ftlh" />
    </#if>

    <div class="card-columns">
        <#include "parts/messageList.ftlh" />
    </div>

</@c.page>