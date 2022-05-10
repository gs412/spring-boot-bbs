<#include "inc/head.ftl">

<h5>hello ${user.getUsername()}</h5>
<h1>list HTML</h1>
<a href="showUserHtml.action">user html</a>
<a href="showAdminHtml.action">admin html</a>
<a href="error.action">error 1/0  html</a>
<a href="logout">退出</a>


<#include "inc/foot.ftl">