<#include "../inc/head.ftlh">

<link rel="stylesheet" href="/static/topic/css/topic_show.css">
<script src="/static/topic/js/topic_show.js"></script>
<script src="/static/topic/js/topic_edit.js"></script>

<h2>${topic.getTitle(50, "...")}</h2>

<#list page.getContent() as post>
	<div class="post clearfix" id="post_${post.getId()}">
		<div class="user_info">
			<div class="user_name">${post.user.getUsername()}</div>
			<div><img src="${post.user.getUserFaceLink()}"></div>
		</div>
		<div class="main">
			<div class="post_info">发布于 ${topic.getCreatedAt()}</div>
			<div class="floor" style="">#${page.getNumber()*10 + post?index + 1}</div>
			<div class="post_content">
					${post.getContentHtml()?no_esc}
			</div>
			<div>
				<#if user?? && user.username == 'admin'>
					<#if post.isFirst >
						<a href="/topic/${topic.getId()}/edit"><i class="fa fa-edit"></i>编辑</a>
						<a href="/topic/${topic.getId()}/remove" data-method="post"><i class="fa fa-remove"></i>删除</a>
					<#else>
						<a href="/post/${post.getId()}/edit"><i class="fa fa-edit"></i>编辑</a>
						<a href="/post/${post.getId()}/remove" data-method="post"><i class="fa fa-remove"></i>删除</a>
					</#if>
				</#if>
			</div>
		</div>
	</div>
</#list>
<br>

<#if (page.getTotalPages() > 1)>
	<div class="pagination">
		<ul>
			<#include "../inc/pagestr.ftlh">
		</ul>
	</div>
</#if>

<br>
<#if user??>
	<form class="form-horizontal" action="/topic/${topic.getId()}/reply" method="post">
        <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
		<fieldset>
			<legend>快速回复</legend>
			<div class="control-group">
				<label for="content" class="control-label" style="width:180px;">内容</label>
				<div class="controls" style="margin-left:200px;">
					<div class="toolbar">
						<i class="fa fa-image" title="上传图片，也可以上传其它类型文件"></i>
						<input type="file" id="upload_file" style="display: none;">
					</div>
					<textarea name="content" id="content" style="width:700px; height:155px; resize:none;"></textarea>
				</div>
			</div>
			<div class="form-actions" style="padding-left:200px;">
				<button type="submit" class="btn btn-primary">回复</button>
			</div>
		</fieldset>
	</form>
</#if>

<#include "../inc/foot.ftlh">