<#assign page_num=[page.getNumber()+1, page.getTotalPages()]?min>

<#if page.isFirst()>
	<li class="disabled">
	    <a href="javascript:void(0);">«</a>
	</li>
<#else>
	<li>
	    <a href="?<#if query_str?has_content>${query_str}&</#if>p=${page_num-1}">«</a>
	</li>
</#if>

<#assign start1=0>
<#assign end1=0>
<#assign start2=0>
<#assign end2=0>
<#assign start3=0>
<#assign end3=0>
<#assign start4=0>
<#assign end4=0>

<#if page_num lt 9 || page.getTotalElements() lt 12>
	<#assign start2=1>
	<#assign end2=page_num>
<#else>
	<#assign start1=1>
	<#assign end1=2>
	<#assign dot1="..">
	<#assign start2=[page_num-4, page.getTotalPages()-8]?min>
	<#assign end2=page_num>
</#if>
<#if page_num gt page.getTotalPages()-8 || page.getTotalElements() lt 12>
	<#if page_num lt page.getTotalPages()>
		<#assign start3=page_num+1>
		<#assign end3=page.getTotalPages()>
	</#if>
<#else>
	<#assign start3=page_num+1>
	<#assign end3=[page_num+4, 9]?max>
	<#assign start4=page.getTotalPages()-1>
	<#assign end4=page.getTotalPages()>
</#if>

<#if end1 gt start1>
	<#list start1..end1 as i>
	    <li <#if i == page_num> class="active"</#if>>
	        <a href="?<#if query_str?has_content>${query_str}&</#if>p=${i}">${i}</a>
	    </li>
	</#list>
	<li class="disabled"><a href="javascript:void(0);">...</a></li>
</#if>
<#list start2..end2 as i>
	<li <#if i == page_num> class="active"</#if>>
	    <a href="?<#if query_str?has_content>${query_str}&</#if>p=${i}">${i}</a>
	</li>
</#list>
<#if start3 !=0 && end3 != 0>
	<#list start3..end3 as i>
		<li <#if i == page_num> class="active"</#if>>
		    <a href="?<#if query_str?has_content>${query_str}&</#if>p=${i}">${i}</a>
		</li>
	</#list>
</#if>
<#if end4 gt start4>
	<li class="disabled"><a href="javascript:void(0);">...</a></li>
	<#list start4..end4 as i>
	    <li <#if i == page_num> class="active"</#if>>
	        <a href="?<#if query_str?has_content>${query_str}&</#if>p=${i}">${i}</a>
	    </li>
	</#list>
</#if>

<#if page.isLast()>
	<li class="disabled">
	    <a href="javascript:void(0);">»</a>
	</li>
<#else>
	<li>
	    <a href="?<#if query_str?has_content>${query_str}&</#if>p=${page_num+1}">»</a>
	</li>
</#if>