<#-- @ftlvariable name="user" type="com.springbootbbs.entiry.User" -->
<#-- @ftlvariable name="post" type="com.springbootbbs.entiry.Post" -->
<#-- @ftlvariable name="topic" type="com.springbootbbs.entiry.Topic" -->
<#include "../inc/head.ftl">

<table>
	<tr>
		<td>标题：</td>
		<td><input type="text" value="${topic.getTitle()}"></td>
	</tr>
	<tr>
		<td>内容：</td>
		<td><textarea>${post.getContent()}</textarea></td>
	</tr>
</table>

<#include "../inc/foot.ftl">