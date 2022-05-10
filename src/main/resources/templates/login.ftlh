<#include "inc/head.ftlh">

<form class="form-horizontal" action="login.action" method="post">
    <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
	<fieldset>
		<legend>登陆</legend>
		<div class="control-group">
			<label for="username" class="control-label">用户名</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="username" id="username">
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码</label>
			<div class="controls">
				<input type="password" class="input-xlarge" name="password" id="password">
			</div>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary">登陆</button>
		</div>
	</fieldset>
</form>


<#include "inc/foot.ftlh">
