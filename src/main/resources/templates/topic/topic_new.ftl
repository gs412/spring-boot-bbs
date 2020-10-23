<#-- @ftlvariable name="tab" type="String" -->
<#include "../inc/head.ftl">

<form class="form-horizontal" action="/topic_save" method="post" autocomplete="off">
    <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
	<fieldset>
		<legend>发布新帖</legend>
        <div class="control-group">
            <label for="title" class="control-label">节点</label>
            <div class="controls">
	            <select name="category_id">
		            <#list categories as category>
                        <option value="${category.getId()}" <#if category.getTab() == tab>selected</#if>>${category.getName()}</option>
		            </#list>
	            </select>
            </div>
        </div>
		<div class="control-group">
			<label for="title" class="control-label">标题</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="title" id="title">
			</div>
		</div>
		<div class="control-group">
			<label for="content" class="control-label">内容</label>
			<div class="controls">
				<textarea name="content" id="content" style="width:700px; height:255px; resize:none;"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary">发表</button>
		</div>
	</fieldset>
</form>

<#include "../inc/foot.ftl">