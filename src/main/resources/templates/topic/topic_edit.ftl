<#-- @ftlvariable name="user" type="com.springbootbbs.entiry.User" -->
<#-- @ftlvariable name="post" type="com.springbootbbs.entiry.Post" -->
<#-- @ftlvariable name="topic" type="com.springbootbbs.entiry.Topic" -->
<#include "../inc/head.ftl">

<form id="edit_form" class="form-horizontal" autocomplete="off">
	<fieldset>
		<legend>编辑帖子</legend>
		<div class="control-group">
            <label for="title" class="control-label">标题</label>
            <div class="controls">
                <input type="text" class="input-xlarge" id="title" value="${topic.getTitle()}">
            </div>
		</div>
		<div class="control-group">
            <label for="content" class="control-label">内容</label>
			<div class="controls">
				<textarea id="content" style="height:300px; width:700px; resize:vertical;">${post.getContent()}</textarea>
			</div>
		</div>
	</fieldset>
</form>

<#include "../inc/foot.ftl">