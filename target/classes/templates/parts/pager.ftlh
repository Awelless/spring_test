<#macro pager url page>

    <#if page.getTotalPages() gt 7>
        <#assign
            totalPages = page.getTotalPages()
            pageNumber = page.getNumber() + 1

            head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
            tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])

            bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
            bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])

            body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
>
    <#else>
        <#assign
            body = 1..page.getTotalPages()
        >
    </#if>

    <div class="row">
        <div class="col">
            <ul class="pagination">
                <li class="page-item disabled">
                    <span class="page-link">Pages</span>
                </li>
                <#list body as p>
                    <#if p == -1>
                        <li class="page-item disabled">
                            <span class="page-link">...</span>
                        </li>
                    <#else>
                        <li class="page-item ${(p - 1 == page.getNumber())?string('active', '')}">
                            <a class="page-link" href="${url}?page=${p - 1}&size=${page.getSize()}">${p}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>

</#macro>