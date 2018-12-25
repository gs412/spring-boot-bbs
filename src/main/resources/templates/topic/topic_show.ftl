<#include "../inc/head.ftl">

<#list page.getContent() as post>
    <div>
	    <div>${post.user.getUsername()}</div>
	    ${post.getContent()}
    </div>
</#list>

<#include "../inc/foot.ftl">