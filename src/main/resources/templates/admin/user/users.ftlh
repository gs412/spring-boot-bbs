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
                            <td>${user.getId()}.</td>
                            <td>${user.getUsername()}</td>
                            <td>${user.getLang()}</td>
                            <td></td>
                            <td>
                                <a href="/admin/user/user/${user.getId()}/ban" data-method="post">封禁</a>
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