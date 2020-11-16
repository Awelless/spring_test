<#import "parts/common.ftlh" as c>

<@c.page "Welcome!" false>

    <div class="ml-3 mr-3 text-center">
        <h4>Hello, ${username}</h4>
        <h6>Testing spring boot</h6>
        <img src="/static/greeting.jpg" height="400" />
    </div>

</@c.page>