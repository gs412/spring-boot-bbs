$(document).ready(function () {
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
                console.log(json)
            },
        })
    });
});