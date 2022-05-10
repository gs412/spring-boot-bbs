<@override name="content">

<div class="row">
    <div class="col-md-12">
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">所有贴子</h3>
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
                            <td>${topic.user.getUsername()}</td>
                            <td>${show_date(topic.getCreatedAt())}</td>
                            <td>
                                <a href="/admin/topic/topic/${topic.getId()}/remove" data-method="post">删除</a>
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

<@extends name="../_base.ftl"/>