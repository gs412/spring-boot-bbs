<#include "../inc/head.ftl">

<form id="app" autocomplete="off" class="form-horizontal">
    <fieldset>
        <legend>基本资料</legend>
        <div class="control-group">
	        <label for="tel" class="control-label">设置头像</label>
            <div class="controls">
	            <img src="" id="shop_icon_img">
	            <input type="file" class="input-file shop_icon">
            </div>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-primary">修改</button>
            &nbsp;
            <a href="javascript:history.go(-1)" class="btn">取消</a>
        </div>
    </fieldset>
</form>

<#include "../inc/foot.ftl">