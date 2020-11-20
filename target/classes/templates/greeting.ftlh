<#import "parts/common.ftlh" as c>

<@c.page "Welcome!" false>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4 text-center">
            <h4>Hello, ${username}</h4>
            <h6>Testing spring boot</h6>
            <img class="img-fluid" src="/static/greeting.jpg" />
        </div>
        <div class="col-sm-4"></div>
    </div>

</@c.page>