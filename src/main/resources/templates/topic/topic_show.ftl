<#include "../inc/head.ftl">

<h2>${topic.getTitle()}</h2>

<#list page.getContent() as post>
    <div style="border:1px solid #666;">
	    <div style="min-height:200px; border-right:1px solid #666; float:left; width:200px;">
	        ${post.user.getUsername()}
	    </div>
	    ${post.getContent()}
    </div>
</#list>

<#include "../inc/foot.ftl">