<#-- @ftlvariable name="tab" type="String" -->
<#include "inc/head.ftl">

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
	            <li <#if category.getTab() == tab>class="active"</#if>><a href="/?tab=${category.getTab()}">${category.getName()}</a></li>
	        </#list>
	        <li <#if tab == "all">class="active"</#if>><a href="/?tab=all">全部</a></li>
        </ul>
    </div>
</div>

<a class="btn btn-primary" href="/topic_new">发布新帖</a>

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
			<td><a href="/topic/${topic.getId()}">${topic.getTitle(50, "..")}</a></td>
			<td>${topic.user.getUsername()}</td>
			<td>${topic.category.getName()}</td>
			<td>${show_date(topic.getCreatedAt())}</td>
		</tr>
	</#list>
</table>

<#if (page.getTotalPages() > 1)>
	<div class="pagination">
		<ul>
			<#include "inc/pagestr.ftl">
		</ul>
	</div>
</#if>

<#include "inc/foot.ftl">