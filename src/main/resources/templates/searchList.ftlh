<#import "parts/common.ftlh" as c>
<#import "parts/messageList.ftlh" as ml>

<@c.page "Search ${pattern}" false>

<div class="text-center">
    <h4>Search</h4>
</div>

<div class="my-3">

    <form method="get" action="/search">
        <div class="form-group row">
            <input type="text" class="form-control"
                   value="${pattern}" name="pattern" placeholder="Search" />
        </div>
        <fieldset class="form-group">
            <div class="form-check">
                <input class="form-check-input" type="radio" id="radio1"
                       name="type" value="message" ${(isMessageList??)?string('checked', '')} />
                <label class="form-check-label" for="radio1">
                    Search for messages
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" id="radio2"
                       name="type" value="user" ${(isUserList??)?string('checked', '')} />
                <label class="form-check-label" for="radio2">
                    Search for users
                </label>
            </div>
        </fieldset>
        <div class="form-group row">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </div>
        </div>
    </form>

</div>

<#if isUserList??>
    <#include "parts/searchUserList.ftlh" />
<#else>
    <@ml.messageList />
</#if>

</@c.page>