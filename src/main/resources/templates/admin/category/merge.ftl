<@override name="content">

<div class="row">
    <div class="col-md-8">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">合并分类</h3>
            </div>
            <form action="/admin/category/merge_post/${category.getId()}" method="post" class="form-horizontal">
                <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                <div class="box-body">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label" style="white-space: nowrap;">将分类 “${category.getName()}” 合并到</label>
                        <div class="col-sm-4">
                            <select name="target_id" class="form-control">
	                            <#list categories as category>
									<option value="${category.getId()}">${category.getName()}</option>
	                            </#list>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="box-footer">
                    <button type="button" class="btn btn-default btn-sm pull-right" onclick="history.go(-1)">取消</button>
                    <button type="submit" class="btn btn-info btn-sm">开始合并</button>
                </div>
            </form>
        </div>
    </div>
</div>

</@override>

<@extends name="../_base.ftl"/>