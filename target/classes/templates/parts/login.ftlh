<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <#if isRegisterForm>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="email" name="email" class="form-control" placeholder="email"/>
            </div>
        </div>
    </#if>
    <div class="form-group row">
        <div class="col-sm-3">
            <input type="text" name="username" class="form-control" placeholder="username"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-3">
            <input type="password" name="password" class="form-control" placeholder="password"/>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div class="row">
        <button type="submit" class="btn btn-primary ml-3">
            <#if isRegisterForm>
                Create
            <#else>
                Sign In
            </#if>
        </button>
        <#if !isRegisterForm>
            <div class="btn btn-light ml-3">
                <a href="/registration">Add new user</a>
            </div>
        <#else>
            <div class="btn btn-light ml-3">
                <a href="/login">Sign In</a>
            </div>
        </#if>
    </div>
</form>
</#macro>

<#macro logout>
<form method="post" action="/logout">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit" class="btn btn-primary">Sign Out</button>
</form>
</#macro>