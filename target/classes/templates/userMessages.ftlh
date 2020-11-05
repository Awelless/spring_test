<#import "parts/common.ftlh" as c>
<#import "parts/messageEdit.ftlh" as m>
<#include "parts/security.ftlh">

<@c.page userPage.username false>

    <#if userPage.id == curUserId && message??>
        <@m.messageBox true />
    <#else>
        <div class="card-columns">
            <#include "parts/messageList.ftlh" />
        </div>
    </#if>

</@c.page>