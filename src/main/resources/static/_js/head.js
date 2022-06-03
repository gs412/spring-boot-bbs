$(document).ready(function () {
    // 右下角中英文切换
    let choose_language_a = $('.select-language > a:first-of-type')
    let choose_language_menu = $('.select-language ul#language_menu')

    choose_language_a.click(function () {
        if (choose_language_menu.is(':visible')) {
            choose_language_menu.hide()
        } else {
            choose_language_menu.show()
        }
    })

    $(document).click(function (e) {
        let target = e.target
        let a_dom = choose_language_a.get(0)
        let menu_dom = choose_language_menu.get(0)
        if (target === a_dom || target === menu_dom || a_dom.contains(target) || menu_dom.contains(target)) {
            return false;
        } else {
            choose_language_menu.hide()
        }
    })
})