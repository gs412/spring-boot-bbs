<#include "../inc/head.ftlh">

<form class="form-horizontal" action="/user/register_save" method="post">
    <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
	<fieldset>
		<legend>注册</legend>
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
		<div class="control-group">
			<label for="password_confirm" class="control-label">确认密码</label>
			<div class="controls">
				<input type="password" class="input-xlarge" name="password_confirm" id="password_confirm">
			</div>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary">注册</button>
		</div>
	</fieldset>
</form>

<#include "../inc/foot.ftlh">