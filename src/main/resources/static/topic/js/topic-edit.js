$(document).ready(function () {

    // 上传附件的相关逻辑
    let input_upload_file = $('.toolbar input#upload_file');

    $('.toolbar .fa-image').click(function () {
        input_upload_file.click();
    });

    input_upload_file.change(function (e) {
        let file = this.files[0]
        let fd = new FormData()
        fd.append('file', file)

        $.ajax({
            url: '/topic_upload',
            type: 'POST',
            data: fd,
            processData: false,
            contentType: false,
            success: function (json) {
                if (json.success) {
                    let text;
                    if (json.data.isImage) {
                        text = `![](${json.data.url})`;
                    } else {
                        text = `[${json.data.fileName}](${json.data.url})`;
                    }
                    $('textarea#content').insertAtCaret(text)
                } else {
                    show_msg(json.message)
                }
            },
        })
    });
});