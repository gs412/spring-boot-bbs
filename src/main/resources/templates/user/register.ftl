<#include "../inc/head.ftl">

<form action="/user/register_save" method="post">
	用户名：<input type="text" name="username" value="${username!}"><br>
	密码：<input type="password" name="password"><br>
	确认密码：<input type="password" name="password_confirm"><br>
	<input type="submit" value="注册">
</form>

<#include "../inc/foot.ftl">