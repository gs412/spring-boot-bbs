<@override name="content">

<div class="row">
    <div class="col-md-8">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">所有分类</h3>
            </div>
            <div class="box-body no-padding">
                <table class="table">
                    <tr>
                        <th style="width: 10px">ID</th>
                        <th>分类名称</th>
                        <th>标签</th>
                        <th>排序</th>
                        <th>管理</th>
                    </tr>
	                <#list categories as category>
                        <tr>
                            <td>${category.getId()}.</td>
                            <td>${category.getName()}</td>
                            <td>${category.getTab()}</td>
                            <td>${category.getSort()}</td>
                            <td>
	                            <a href="/admin/category/edit/${category.getId()}">编辑</a>
	                            <a href="/admin/category/delete/${category.getId()}" data-method="post" data-confirm="确定删除？">删除</a>
                            </td>
                        </tr>
	                </#list>
                </table>
            </div>
        </div>
	    <a href="/admin/category/add" class="btn btn-primary btn-sm">添加分类</a>
    </div>
</div>

</@override>

<@extends name="../_base.ftl"/>