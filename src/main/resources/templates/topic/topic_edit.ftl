<#include "../inc/head.ftl">

<form id="edit_form" class="form-horizontal" autocomplete="off">
	<fieldset>
		<legend>编辑帖子</legend>
        <div class="control-group">
            <label for="title" class="control-label">节点</label>
            <div class="controls">
                <select name="category_id" id="category_id">
					<#list categories as category>
	                    <option value="${category.getId()}" <#if category == topic.getCategory()>selected</#if>>${category.getName()}</option>
					</#list>
                </select>
            </div>
        </div>
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
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">发布</button> &nbsp;
            <a class="btn" href="javascript:history.go(-1)">取消</a>
        </div>
	</fieldset>
</form>

<script>
	$(document).ready(function () {
		$('#edit_form').submit(function () {
            let category_id = $('#category_id').val()
            let title = $('#title').val()
            let content = $('#content').val()

			$.post('/topic/${topic.getId()}/edit_post', {category_id: category_id, title: title, content: content}, function (json) {
				if (json.success) {
					window.location = "/topic/${topic.getId()}";
                }
			});

			return false;
        })
    })
</script>

<#include "../inc/foot.ftl">