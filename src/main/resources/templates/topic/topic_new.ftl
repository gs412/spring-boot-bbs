<#include "../inc/head.ftl">

<form class="form-horizontal" action="/topic_save" method="post">
	<fieldset>
		<legend>发布新帖</legend>
        <div class="control-group">
            <label for="title" class="control-label">节点</label>
            <div class="controls">
	            <select name="category_id">
		            <option>111</option>
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