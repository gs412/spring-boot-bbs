<#include "inc/head.ftl">

<a class="btn btn-primary" href="/topic_new">发布新帖</a>

<br><br>
<table class="table">
	<tr>
		<th style="width:50%;">标题</th>
		<th style="width:15%;">作者</th>
		<th style="width:15%;">节点</th>
		<th>发布时间</th>
	</tr>
	<#list page.getContent() as topic>
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
			<li <#if page.isFirst()>class="disabled"</#if>>
				<a href="/?p=1">«</a>
			</li>
			<#list 1..page.getTotalPages() as i>
				<li <#if i == page.getNumber()+1> class="active"</#if>>
					<a href="/?p=${i}">${i}</a>
				</li>
			</#list>
			<li <#if page.isLast()>class="disabled"</#if>>
				<a href="/?p=${page.getTotalPages()}">»</a>
			</li>
		</ul>
	</div>
</#if>

<#include "inc/foot.ftl">