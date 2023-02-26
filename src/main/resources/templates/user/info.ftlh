<#include "../inc/head.ftlh">

<link rel="stylesheet" href="/static/user/info.css">

<div class="user-info">
    <div>${user.getUsername()}</div>
    <div class="face">
        <img src="${user.getUserFaceLink()}">
    </div>
</div>

<#include "../inc/foot.ftlh">