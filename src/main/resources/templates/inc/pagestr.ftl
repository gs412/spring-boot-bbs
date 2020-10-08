<li <#if page.isFirst()>class="disabled"</#if>>
	<a href="?<#if query_str?has_content>${query_str}&</#if>p=1">«</a>
</li>
<#list 1..page.getTotalPages() as i>
	<li <#if i == page.getNumber()+1> class="active"</#if>>
		<a href="?<#if query_str?has_content>${query_str}&</#if>p=${i}">${i}</a>
	</li>
</#list>
<li <#if page.isLast()>class="disabled"</#if>>
	<a href="?<#if query_str?has_content>${query_str}&</#if>p=${page.getTotalPages()}">»</a>
</li>