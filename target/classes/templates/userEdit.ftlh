<#import "parts/common.ftlh" as c>

<@c.page "Editing ${user.username}" false>

    <h4 class="mb-3 text-center">Editing ${user.username}</h4>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <form method="post" action="/admin_panel">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="hidden" name="userId" value="${user.id}" />

                <h6>Username</h6>
                <div class="form-group row">
                    <div class="col">
                        <input type="text" class="form-control"
                               name="username" value="${user.username}" />
                    </div>
                </div>

                <h6>Email</h6>
                <div class="form-group row">
                    <div class="col">
                        ${user.email}
                    </div>
                </div>

                <div class="row justify-content-center">
                    <button type="submit" class="btn btn-primary">Save</button>
                    <div class="btn btn-light ml-2">
                        <a href="/admin_panel">Back</a>
                    </div>
                </div>
            </form>
        </div>

        <div class="col-sm-4"></div>
    </div>

</@c.page>