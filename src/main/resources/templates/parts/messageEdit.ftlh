<#macro messageBox isEditBox>

<#if isEditBox>
    <#assign
        submitButtonValue = "Edit"
    >
<#else>
    <#assign
        submitButtonValue = "Post"
    >
</#if>


<#if !isEditBox>
    <div class="mt-3">
        <a class="btn btn-primary" data-toggle="collapse" href="#messageBox"
           role="button" aria-expanded="false" aria-controls="collapseExample">
            Add new message
        </a>
    </div>
</#if>

<div class="collapse <#if message??>show</#if>" id="messageBox">
    <div class="form-group mt-2">
        <form method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <#if isEditBox>
                <input type="hidden" name="messageId" value="<#if message??>${message.id}</#if>" />
            </#if>
            <div class="form-group mt-3">
                <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>" name="text" placeholder="message..." />
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="row">
                <div class="form-group col-md-8">
                    <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.tag}</#if>" name="tag" placeholder="tag" />
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
                <div class="form-group col-md-4">
                    <div class="custom-file">
                        <input type="file" name="file" accept="image/*" class="custom-file-input" id="customFile" />
                        <label class="custom-file-label" for="customFile">
                            <#if isEditBox && message.filename??>
                                ${message.getNormalFilename()}
                            <#else>
                                Choose file
                            </#if>
                        </label>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">${submitButtonValue}</button>
                <#if isEditBox>
                    <div class="btn btn-light ml-2">
                        <a href="/user/${message.author.id}">Back</a>
                    </div>
                </#if>
            </div>

        </form>
    </div>
</div>

</#macro>