<#include "../inc/head.ftl">

<h2>${topic.getTitle(50, "...")}</h2>

<#list page.getContent() as post>
    <div style="border:1px solid #666;" class="clearfix">
	    <div style="min-height:200px; float:left; width:190px; box-sizing:border-box; padding:20px;">
	        <div style="font-weight:bold;">${post.user.getUsername()}</div>
		    <div><img src="${post.user.getUserFaceLink()}"></div>
	    </div>
	    <div style="min-height:200px; margin-left:200px; border-left:1px solid #666; word-break:break-all; box-sizing:border-box; padding:1px 20px 20px 20px; position:relative;">
		    <div style="padding:6px 0 3px; margin:0 0 15px 0; border-bottom:1px solid #ddd;">发布于 ${topic.getCreatedAt()}</div>
		    <div style="position:absolute; right:20px; top:8px;">#${page.getNumber()*10 + post?index + 1}</div>
		    <div style="min-height: 140px;">
		        ${post.getContentHtml()}
		    </div>
		    <div>
			    <#if user?? && user.username == 'admin'>
                    <a href="/topic/${topic.getId()}/edit"><i class="fa fa-edit"></i>编辑</a>
				    <#if post.isFirst >
				        <a href="/topic/${topic.getId()}/remove" data-method="post"><i class="fa fa-remove"></i>删除</a>
				    <#else>
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
			<li <#if page.isFirst()>class="disabled"</#if>>
				<a href="/topic/${topic.getId()}/?p=1">«</a>
			</li>
			<#list 1..page.getTotalPages() as i>
				<li <#if i == page.getNumber()+1> class="active"</#if>>
					<a href="/topic/${topic.getId()}/?p=${i}">${i}</a>
				</li>
			</#list>
			<li <#if page.isLast()>class="disabled"</#if>>
				<a href="/topic/${topic.getId()}/?p=${page.getTotalPages()}">»</a>
			</li>
		</ul>
	</div>
</#if>

<br>
<#if user??>
	<form class="form-horizontal" action="/topic/${topic.getId()}/reply" method="post">
		<fieldset>
			<legend>快速回复</legend>
			<div class="control-group">
				<label for="content" class="control-label" style="width:180px;">内容</label>
				<div class="controls" style="margin-left:200px;">
					<textarea name="content" id="content" style="width:700px; height:155px; resize:none;"></textarea>
				</div>
			</div>
			<div class="form-actions" style="padding-left:200px;">
				<button type="submit" class="btn btn-primary">回复</button>
			</div>
		</fieldset>
	</form>
</#if>

<#include "../inc/foot.ftl">