<#import "navbar.ftlh" as n>

<#macro page title isLoginRegisterForm>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>${title}</title>
    <link rel="shortcut icon" type="image/x-icon" href="/static/favicon.ico" />

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

    <!-- BOOTSTRAP -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous" defer="defer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous" defer="defer"></script>
    <!-- FILE INPUT -->
    <script src="https://cdn.jsdelivr.net/npm/bs-custom-file-input/dist/bs-custom-file-input.js" defer="defer"></script>
    <script>
        $(document).ready(function () {
            bsCustomFileInput.init()
        })
    </script>
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
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>

</html>
</#macro>