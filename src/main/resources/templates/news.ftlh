<#import "parts/common.ftlh" as c>
<#import "parts/messageList.ftlh" as ml>

<@c.page "News" false>

    <div class="card-columns">
        <@ml.messageList />
    </div>

</@c.page>