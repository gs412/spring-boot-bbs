<#include "../inc/head.ftlh">

<link rel="stylesheet" href="/static/profile/basic.css">

<form id="form1" method="post" action="/profile/password_post" autocomplete="off" class="form-horizontal">
  <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
  <fieldset>
    <legend>${local("profile_password")}</legend>
    <div class="control-group">
      <label for="password_origin" class="control-label">${local("profile_password_origin")}</label>
      <div class="controls">
        <input type="password" name="password_origin" id="password_origin" class="input-file shop_icon">
      </div>
    </div>
    <div class="control-group">
      <label for="password_new" class="control-label">${local("profile_password_new")}</label>
      <div class="controls">
        <input type="password" name="password_new" id="password_new" class="input-file shop_icon">
      </div>
    </div>
    <div class="control-group">
      <label for="password_confirm" class="control-label">${local("profile_password_confirm")}</label>
      <div class="controls">
        <input type="password" name="password_confirm" id="password_confirm" class="input-file shop_icon">
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">${local("save_change")}</button>
      &nbsp;
      <a href="javascript:history.go(-1)" class="btn">${local("cancel")}</a>
    </div>
  </fieldset>
</form>

<script>
    $(document).ready(function () {
        $('#form1').submit(function () {
            //
        })
    })
</script>

<#include "../inc/foot.ftlh">