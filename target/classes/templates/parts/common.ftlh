<#import "navbar.ftlh" as n>

<#macro page title isLoginRegisterForm>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>${title}</title>
    <link rel="shortcut icon" type="image/x-icon" href="/static/favicon.ico" />

    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- BOOTSTRAP -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous" />
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous" defer="defer"></script>
    <!-- CAPTCHA -->
    <script src='https://www.google.com/recaptcha/api.js' async="async" defer="defer"></script>
    <!-- LIKES -->
    <script src="https://kit.fontawesome.com/abc64bf97d.js" crossorigin="anonymous" defer="defer"></script>
    <!-- TURBOLINKS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js" defer="defer"></script>
    <style>
        .turbolinks-progress-bar {
            height: 3px;
            background-color: #007bff;
        }
    </style>

</head>

<body>
    <@n.navbar isLoginRegisterForm />

    <div class="container mt-4">
    <#if notWorking??>
        <#include "notWorking.ftlh">
    <#else>
        <#nested>
    </#if>
    </div>
    <!-- FILE INPUT -->
    <script src="https://cdn.jsdelivr.net/npm/bs-custom-file-input/dist/bs-custom-file-input.js" defer="defer"></script>
    <script defer="defer">
        $(document).ready(function () {
            bsCustomFileInput.init()
        })
    </script>
    <!-- BOOTSTRAP -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous" defer="defer"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous" defer="defer"></script>
</body>

</html>
</#macro>