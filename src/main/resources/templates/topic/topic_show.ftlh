<#include "../inc/head.ftlh">

<link rel="stylesheet" href="/static/topic/css/topic_show.css">
<script src="/static/topic/js/topic_show.js"></script>
<script src="/static/topic/js/topic_edit.js"></script>

<h2 class="title" data-id="${topic.id}">${topic.getTitle()}</h2>

<#list page.getContent() as post>
	<div class="post clearfix" id="post_${post.getId()}">
		<div class="user_info">
			<div class="user_name <#if post.user.getBanned()>banned</#if>">${post.user.getUsername()}</div>
			<div><img src="${post.user.getUserFaceLink()}"></div>
		</div>
		<div class="main">
			<div class="post_info">${local("post_at")} ${topic.getCreatedAt()}</div>
			<div class="floor" style="">#${page.getNumber()*10 + post?index + 1}</div>
			<div class="post_content">
					${post.getContentHtml()?no_esc}
			</div>
			<div>
				<#if user?? && user.username == 'admin'>
					<#if post.isFirst >
						<a href="/topic/${topic.getId()}/edit"><i class="fa fa-edit"></i>${local("edit")}</a>
						<a href="/topic/${topic.getId()}/remove" data-method="post"><i class="fa fa-remove"></i>${local("delete")}</a>
						<#if topic.stick >
							<a href="javascript:void(0);" class="topic-stick-off"><i class="fa fa-thumb-tack"></i>${local("stick_off")}</a>
						<#else>
							<a href="javascript:void(0);" class="topic-stick-on"><i class="fa fa-thumb-tack"></i>${local("stick_on")}</a>
						</#if>
					<#else>
						<a href="/post/${post.getId()}/edit"><i class="fa fa-edit"></i>${local("edit")}</a>
						<a href="/post/${post.getId()}/remove" data-method="post"><i class="fa fa-remove"></i>${local("delete")}</a>
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
			<legend>${local("quick_reply")}</legend>
			<div class="control-group">
				<label for="content" class="control-label" style="width:180px;">
					${local("content")}<br>
					<small style="color:#999; cursor:help;" title="内容支持markdown格式">markdown</small>
				</label>
				<div class="controls" style="margin-left:200px;">
					<div class="toolbar">
						<i class="fa fa-image" title="上传图片，也可以上传其它类型文件"></i>
						<input type="file" id="upload_file" style="display: none;">
					</div>
					<textarea name="content" id="content" style="width:700px; height:155px; resize:none;"></textarea>
				</div>
			</div>
			<div class="form-actions" style="padding-left:200px;">
				<button type="submit" class="btn btn-primary">${local("reply")}</button>
			</div>
		</fieldset>
	</form>
</#if>

<#include "../inc/foot.ftlh">