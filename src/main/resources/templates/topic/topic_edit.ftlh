<#include "../inc/head.ftlh">

<link rel="stylesheet" href="/static/topic/css/topic_edit.css">
<script src="/static/topic/js/topic_edit.js"></script>

<form id="edit_form" class="form-horizontal" autocomplete="off">
  <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
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
        <div class="toolbar">
          <i class="fa fa-image" title="上传图片，也可以上传其它类型文件"></i>
          <input type="file" id="upload_file" style="display: none;">
        </div>
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

            $.post('/topic/${topic.getId()}/edit_post', {
                category_id: category_id,
                title: title,
                content: content
            }, function (json) {
                if (json.success) {
                    window.location = "/topic/${topic.getId()}";
                }
            });

            return false;
        })
    })
</script>

<#include "../inc/foot.ftlh">