<#import "parts/common.ftlh" as c>
<#import "parts/messageEdit.ftlh" as me>
<#import "parts/messageList.ftlh" as ml>

<@c.page "All Messages" false>

    <div class="form-row">
        <form method="get" action="/messages" class="form-inline">
            <input type="text" name="filter" value="${filter?ifExists}" class="form-control ml-1" placeholder="search by tag"/>
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>

    <@me.messageBox false />

    <div class="card-columns">
        <@ml.messageList />
    </div>

</@c.page>