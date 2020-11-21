<#import "parts/common.ftlh" as c>
<#import "parts/messageEdit.ftlh" as me>
<#import "parts/messageList.ftlh" as ml>

<@c.page "All Messages" false>

    <form method="get" action="/messages">
        <div id="filterFieldClass"></div>
    </form>

    <script defer="defer">
        let filterFieldClass = null

        if (window.innerWidth < 800) {
            filterFieldClass = 'col-sm-10 mb-2'
        } else {
            filterFieldClass = 'col-sm-3'
        }
        document.getElementById("filterFieldClass").innerHTML =
            '<div class="form-group row">' +
            '<div class="' + filterFieldClass + '">' +
            '<input type="text" name="filter" value="${filter?ifExists}" class="form-control" placeholder="search by tag" />' +
            '</div>' +
            '<div class="col-sm-1">' +
            '<button type="submit" class="btn btn-primary">Search</button>' +
            '</div>' +
            '</div>'
    </script>

    <@me.messageBox false />

    <@ml.messageList />

</@c.page>