<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Invitation to Venture Mentor Network</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
        }

        .header {
            background-color: #e74c3c;
            color: white;
            padding: 10px;
            text-align: center;
        }

        .content {
            padding: 20px;
        }

        .footer {
            background-color: #e74c3c;
            color: white;
            padding: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class='container'>
    <div class='header'>
        <h1>Invitation to Venture Mentor Network</h1>
    </div>
    <div class='content'>
        <p>Hello <b>${name}</b>,</p>
        <p>
            I am <b>${senderName}</b>, Venture Development Cell, GITAM University.
            You have been invited to join the Venture Mentor Network as <b>${role}</b>.
            <#if role == "MENTEE">
                You have been invited to a Venture Mentor Network as <b>${role}</b> under the venture <b>${venture}</b>.
            </#if>
            Please click on the link below to register.
        </p>
        <p>
            <a href=''>Links under development but your token is: <b>${token}</b></a>
        </p>
    </div>
    <div class='footer'>
        <p>Team Venture Development Cell</p>
    </div>
</div>
</body>
</html>