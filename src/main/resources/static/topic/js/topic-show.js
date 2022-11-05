$(document).ready(function () {
    $('.post_content img').load(function () {
        let $this = $(this);
        if ($this.prop("naturalHeight") > $this.height()) {
            $this.wrap(`<a href="${$this.attr("src")}" target="_blank" style="cursor: zoom-in">`)
        }

        moveToHash();
    });

    function moveToHash() {
        let urlHash = window.location.hash;

        if(urlHash) {
            window.location.hash = '';
            window.location.hash = urlHash;
        }
    }

    // 置顶，取消置顶
    $('.topic-stick-on, .topic-stick-off').click(function () {
        let $this = $(this);
        let id = $('h2.title').data('id');
        let action = $this.hasClass('topic-stick-on') ? 'on' : 'off';
        $.post("/topic_stick", {id: id, action: action}, function (json) {
            if (json.success) {
                window.location.reload();
            }
        })
    });
});