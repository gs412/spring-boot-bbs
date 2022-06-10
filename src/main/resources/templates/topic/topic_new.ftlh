<#-- @ftlvariable name="tab" type="String" -->
<#include "../inc/head.ftlh">

<link rel="stylesheet" href="/static/topic/css/topic_edit.css">
<script src="/static/topic/js/topic_edit.js"></script>

<form class="form-horizontal" action="/topic_save" method="post" autocomplete="off">
    <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
	<fieldset>
		<legend>${local("new_topic")}</legend>
        <div class="control-group">
            <label for="title" class="control-label">${local("category")}</label>
            <div class="controls">
	            <select name="category_id">
		            <#list categories as category>
                        <option value="${category.getId()}" <#if category.getTab() == tab>selected</#if>>
							<#if local_lang == "cn">
								${category.getNameCn()}
							<#else>
								${category.getNameEn()}
							</#if>
						</option>
		            </#list>
	            </select>
            </div>
        </div>
		<div class="control-group">
			<label for="title" class="control-label">${local("title")}</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="title" id="title">
			</div>
		</div>
		<div class="control-group">
			<label for="content" class="control-label">${local("content")}</label>
			<div class="controls">
				<div class="toolbar">
					<i class="fa fa-image" title="上传图片，也可以上传其它类型文件"></i>
					<input type="file" id="upload_file" style="display: none;">
				</div>
				<textarea name="content" id="content" style="width:700px; height:255px; resize:none;"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary">${local("publish")}</button>
		</div>
	</fieldset>
</form>

<#include "../inc/foot.ftlh">