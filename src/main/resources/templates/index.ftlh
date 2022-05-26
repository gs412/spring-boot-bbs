<#include "inc/head.ftlh">

<style>
	.navbar .tabs {list-style: none; margin: 0; line-height: 40px;}
	.navbar .tabs li {display: inline-block;}
	.navbar .tabs li a {color: #555; padding: 3px 8px; border-radius: 2px;}
	.navbar .tabs li a:hover {text-decoration: none; background: #e9e9e9; color: #333;}
	.navbar .tabs li.active a {color: #fff; background: #334;}
</style>
<div class="navbar">
    <div class="navbar-inner">
        <ul class="tabs">
	        <#list categories as category>
	            <li <#if category.getTab() == tab>class="active"</#if>><a href="/?tab=${category.getTab()}">${category.getNameCn()}</a></li>
	        </#list>
	        <li <#if tab == "all">class="active"</#if>><a href="/?tab=all">全部</a></li>
        </ul>
    </div>
</div>

<a class="btn btn-primary" href="/topic_new?tab=${tab}">${local("zh-CN", "new_topic")}</a>

<br><br>
<table class="table">
	<tr>
		<th style="width:50%;">标题</th>
		<th style="width:15%;">作者</th>
		<th style="width:15%;">节点</th>
		<th>发布时间</th>
	</tr>
	<#list topics as topic>
		<tr>
			<td><a href="/topic/${topic.getId()}">${topic.getTitle()}</a></td>
			<td>${topic.user.getUsername()}</td>
			<td><a href="/?tab=${topic.category.getTab()}">${topic.category.getNameCn()}</a></td>
			<td>${show_date(topic.getCreatedAt())}</td>
		</tr>
	</#list>
	<#if searchWord?has_content && !topics?has_content>
		<tr>
			<td colspan="4" style="font-weight: bold; text-align: center; padding: 30px;">没有 “${searchWord}” 相关的主题</td>
		</tr>
	</#if>
</table>

<#if (page.getTotalPages() > 1)>
	<div class="pagination">
		<ul>
			<#include "inc/pagestr.ftlh">
		</ul>
	</div>
</#if>

<#include "inc/foot.ftlh">