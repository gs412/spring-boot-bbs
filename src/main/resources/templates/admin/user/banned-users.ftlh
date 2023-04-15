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
                        <th>用户名</th>
                        <th>语言</th>
                        <th>注册时间</th>
                        <th>操作</th>
                    </tr>
	                <#list page.getContent() as user>
                        <tr>
                            <td>${user.getIdStr()}.</td>
                            <td>${user.getUsername()}</td>
                            <td>${user.getLang()}</td>
                            <td>${show_date(user.getCreatedAt())}</td>
                            <td>
                                <a href="/admin/user/user/${user.getIdStr()}/unban" data-method="post">解禁</a>
                                <a href="/admin/user/user/${user.getIdStr()}/remove" data-confirm="删除后不可恢复，确定删除吗？" data-method="post">删除</a>
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