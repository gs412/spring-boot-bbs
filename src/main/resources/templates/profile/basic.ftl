<#include "../inc/head.ftl">

<form action="/profile/basic_post" method="post" autocomplete="off" class="form-horizontal" enctype="multipart/form-data">
    <fieldset>
        <legend>基本资料</legend>
        <div class="control-group">
	        <label for="tel" class="control-label">设置头像</label>
            <div class="controls">
	            <img src="${user_face_link}" id="shop_icon_img">
	            <input type="file" name="user_face" class="input-file shop_icon">
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">修改</button>
            &nbsp;
            <a href="javascript:history.go(-1)" class="btn">取消</a>
        </div>
    </fieldset>
</form>

<script>
	$(document).ready(function () {
		//
	})
</script>

<#include "../inc/foot.ftl">