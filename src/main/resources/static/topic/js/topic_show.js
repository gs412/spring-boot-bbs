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
});