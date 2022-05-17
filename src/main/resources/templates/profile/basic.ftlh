<#include "../inc/head.ftlh">

<link rel="stylesheet" href="/static/profile/basic.css">

<form id="form1" autocomplete="off" class="form-horizontal">
  <fieldset>
    <legend>基本资料</legend>
    <div class="control-group">
      <label for="tel" class="control-label">设置头像</label>
      <div class="controls">
        <img src="${user_face_link}" id="shop_icon_img">
        <input type="file" name="user_face" class="input-file shop_icon">
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">修改</button>
      &nbsp;
      <a href="javascript:history.go(-1)" class="btn">取消</a>
    </div>
  </fieldset>
</form>

<script>
    $(document).ready(function () {
        $('#form1').submit(function () {
            const $file = document.querySelector("input[name='user_face']").files[0];
            const fd = new FormData();
            fd.append('user_face', $file);

            const xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    const json = JSON.parse(xhr.responseText);
                    if (json.success == 1) {
                        window.location.reload();
                    } else {
                        alert(json.message);
                    }
                }
            }

            xhr.open("post", "/profile/basic_post");
            xhr.setRequestHeader($('meta[name=csrftoken_header_name]').attr("content"), $.cookie('csrftoken'));
            xhr.send(fd);

            return false;
        })
    })
</script>

<#include "../inc/foot.ftlh">