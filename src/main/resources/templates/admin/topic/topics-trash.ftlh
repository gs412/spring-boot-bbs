<@override name="content">

<div class="row">
    <div class="col-md-12">
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">回收站</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table class="table table-bordered">
                    <tr>
                        <th style="width: 10px">ID</th>
                        <th>标题</th>
                        <th>作者</th>
                        <th>发布时间</th>
                        <th>操作</th>
                    </tr>
	                <#list page.getContent() as topic>
                        <tr>
                            <td>${topic.getId()}.</td>
                            <td>${topic.getTitle()}</td>
                            <td>
                                ${topic.user.getUsername()}
                                <#if topic.user.getBanned()>
                                    <span class="banned-color" style="font-size: 12px;">[已封禁]</span>
                                </#if>
                            </td>
                            <td>${show_date(topic.getCreatedAt())}</td>
                            <td>
                                <a href="/admin/topic/topic/${topic.getId()}/resume" data-method="post">恢复</a>
                                <a href="/admin/topic/topic/${topic.getId()}/remove-hard" data-method="post" data-confirm="删除后不可恢复，确定删除吗？">彻底删除</a>
                            </td>
                        </tr>
	                </#list>
                </table>
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-left">
                    <#include "../../inc/pagestr.ftlh">
                </ul>
            </div>
        </div>
        <!-- /.box -->
    </div>
</div>

</@override>

<@extends name="../_base.ftlh"/>