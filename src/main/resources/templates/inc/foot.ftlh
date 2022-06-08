<div class="select-language">
    <a id="open_source_link" href="https://github.com/gs412/spring-boot-bbs" target="_blank"><i class="fa fa-github"></i> open source</a>

    <span>choose language:</span>
    <a id="choose_language_link" href="javascript:void(0);">
        <#if local_lang == "cn">
            中文
        <#else>
            English
        </#if>
        <i class="fa fa-angle-up"></i>
    </a>
    <ul id="language_menu">
        <li><a href="/change_language?lang=en">English</a></li>
        <li><a href="/change_language?lang=cn">中文</a></li>
    </ul>
</div>

</div>
</body>
</html>