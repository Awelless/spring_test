<#import "parts/common.ftlh" as c>
<#import "parts/messageEdit.ftlh" as m>

<@c.page "Messages" false>

    <div class="form-row">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" value="${filter?ifExists}" class="form-control ml-1" placeholder="search by tag"/>
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>

    <@m.messageBox false />

    <div class="card-columns">
        <#include "parts/messageList.ftlh" />
    </div>

</@c.page>