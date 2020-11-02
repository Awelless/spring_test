<#import "/parts/common.ftlh" as c>
<#import "/parts/login.ftlh" as l>

<@c.page>
    <div class="mb-3">
        <h5>Add new user</h5>
    </div>
    ${message?ifExists}
    <@l.login "/registration" true/>
</@c.page>