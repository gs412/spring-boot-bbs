<#include "../inc/head.ftlh">

<#if msg??>
	<script>alert("${msg}")</script>
</#if>

<form class="form-horizontal" action="/user/register_save" method="post">
    <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
	<fieldset>
		<legend>${local("register")}</legend>
		<div class="control-group">
			<label for="username" class="control-label">${local("username")}</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="username" id="username" value="<#if username??>${username}</#if>">
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">${local("password")}</label>
			<div class="controls">
				<input type="password" class="input-xlarge" name="password" id="password">
			</div>
		</div>
		<div class="control-group">
			<label for="password_confirm" class="control-label">${local("password_confirm")}</label>
			<div class="controls">
				<input type="password" class="input-xlarge" name="password_confirm" id="password_confirm">
			</div>
		</div>
		<div class="control-group">
			<label for="captcha" class="control-label">${local("captcha")}</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="captcha" id="captcha" maxlength="${seccodeSize}" style="width: ${inputWidth}px;">
				<img src="${seccodeSrc}" id="seccodeImg" style="cursor: pointer;">
			</div>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary">${local("register")}</button>
		</div>
	</fieldset>
</form>

<script>
	$(function() {
		$('img#seccodeImg').click(function () {
			let $this = $(this);
			$this.attr('src', "${seccodeSrc}?v=" + Math.random());
		});
	});
</script>

<#include "../inc/foot.ftlh">