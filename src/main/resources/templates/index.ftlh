<#include "inc/head.ftlh">

<style>
	.navbar .tabs {list-style: none; margin: 0; line-height: 40px;}
	.navbar .tabs li {display: inline-block;}
	.navbar .tabs li a {color: #555; padding: 3px 8px; border-radius: 2px;}
	.navbar .tabs li a:hover {text-decoration: none; background: #e9e9e9; color: #333;}
	.navbar .tabs li.active a {color: #fff; background: #334;}
</style>
<link rel="stylesheet" href="/static/index/css/index.css">

<div class="navbar">
    <div class="navbar-inner">
        <ul class="tabs">
	        <#list categories as category>
	            <li <#if category.getTab() == tab>class="active"</#if>>
					<a href="/?tab=${category.getTab()}">
						<#if local_lang == "cn">
							${category.getNameCn()}
						<#else>
							${category.getNameEn()}
						</#if>
					</a>
				</li>
	        </#list>
	        <li <#if tab == "all">class="active"</#if>><a href="/?tab=all">${local("tab_all")}</a></li>
        </ul>
    </div>
</div>

<a class="btn btn-primary" href="/topic_new?tab=${tab}">${local("new_topic")}</a>

<br><br>
<table class="table">
	<tr>
		<th style="width:50%;">${local("title")}</th>
		<th style="width:15%;">${local("author")}</th>
		<th style="width:15%;">${local("category")}</th>
		<th class="operate">
			<div>
				${local("pub_time")}
				<svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg">
					<path d="m11.998 17 7-8h-14z"></path>
				</svg>
			</div>
			<ul id="order_by">
				<li><a href="/change_language?lang=en">发布时间</a></li>
				<li><a href="/change_language?lang=cn">回复时间</a></li>
			</ul>
		</th>
		<script>
			$(document).ready(function() {

			})
		</script>
	</tr>
	<#list topics as topic>
		<tr>
			<td class="title clearfix">
				<a href="/topic/${topic.getId()}" title="${topic.getTitle()}">${topic.getShortTitle()?no_esc}</a>
				<#if topic.stick>
					<i class="fa fa-thumb-tack" title="${local("stick_topic")}"></i>
				</#if>
			</td>
			<td>${topic.user.getUsername()}</td>
			<td>
				<a href="/?tab=${topic.category.getTab()}">
					<#if local_lang == "cn">
						${topic.category.getNameCn()}
					<#else>
						${topic.category.getNameEn()}
					</#if>
				</a>
			</td>
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