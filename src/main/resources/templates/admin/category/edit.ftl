<@override name="content">

<div class="row">
    <div class="col-md-8">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">添加分类</h3>
            </div>
            <form id="form1" class="form-horizontal">
                <div class="box-body">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">分类名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="name" id="name" value="${category.getName()}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="tab" class="col-sm-2 control-label">标签</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="tab" id="tab" value="${category.getTab()}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sort" class="col-sm-2 control-label">排序</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="sort" id="sort" value="${category.getSort()}" min="1" max="1000" step="1" required>
                            <p class="help-block" style="font-size: 12px; color: #888; margin-top: 0;">数字越大越靠后</p>
                        </div>
                    </div>
                </div>
                <div class="box-footer">
                    <button type="button" class="btn btn-default btn-sm pull-right" onclick="history.go(-1)">取消</button>
                    <button type="submit" class="btn btn-info btn-sm">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
	$(document).ready(function () {
        $('#form1').submit(function () {
	        let name = $('input#name').val()
	        let tab = $('input#tab').val()
	        let sort = $('input#sort').val()
	        $.post('/admin/category/edit_post/${category.getId()}', {name: name, tab: tab, sort: sort}, function (json) {
		        json = JSON.parse(json);
				if (json.success == 1) {
                    window.location.href = '/admin/category/categories';
				} else {
					alert(json.message);
				}
	        })
	        return false;
        })
    })
</script>

</@override>

<@extends name="../_base.ftl"/>