jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
		}
		var path = options.path ? '; path=' + options.path : '';
		var domain = options.domain ? '; domain=' + options.domain : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		if(name==="csrftoken"){
			return $('meta[name=csrftoken]').attr("content");
		}
		return cookieValue;
	}
};
// 调用方式： $.cookie('csrftoken')

jQuery(function ($) {
    $.extend({
        form: function (url, data, method) {
            if (method == null) method = 'POST';
            if (data == null) data = {};

            var form = $('<form>').attr({
                method: method,
                action: url
            }).css({
                display: 'none'
            });

            var addData = function (name, data) {
                if ($.isArray(data)) {
                    for (var i = 0; i < data.length; i++) {
                        var value = data[i];
                        addData(name + '[]', value);
                    }
                } else if (typeof data === 'object') {
                    for (var key in data) {
                        if (data.hasOwnProperty(key)) {
                            addData(name + '[' + key + ']', data[key]);
                        }
                    }
                } else if (data != null) {
                    form.append($('<input>').attr({
                        type: 'hidden',
                        name: String(name),
                        value: String(data)
                    }));
                }
            };

	        addData($('meta[name=csrftoken_parameter_name]').attr("content"), $.cookie('csrftoken'));
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    addData(key, data[key]);
                }
            }

            return form.appendTo('body');
        }
    });

	$.fn.extend({
		/**
		 * 初始化对象以支持光标处插入内容
		 */
		setCaret: function(){
			if(/msie/.test(navigator.userAgent.toLowerCase())==false) return;
			let initSetCaret = function(){
				let textObj = $(this).get(0);
				textObj.caretPos = document.selection.createRange().duplicate();
			};
			$(this)
				.click(initSetCaret)
				.select(initSetCaret)
				.keyup(initSetCaret);
		},
		/**
		 * 在当前对象光标处插入指定的内容
		 */
		insertAtCaret: function(textFeildValue){
			let textObj = $(this).get(0);
			if(document.all && textObj.createTextRange && textObj.caretPos){
				let caretPos=textObj.caretPos;
				caretPos.text = caretPos.text.charAt(caretPos.text.length-1) == '' ?
					textFeildValue+'' : textFeildValue;
				caretPos.select();
			}else if(textObj.setSelectionRange){
				let rangeStart=textObj.selectionStart;
				let rangeEnd=textObj.selectionEnd;
				let tempStr1=textObj.value.substring(0,rangeStart);
				let tempStr2=textObj.value.substring(rangeEnd);
				if ((tempStr1.split('[').last()+tempStr2.split(']').first()).match(/^附件\d+$/i)) { //jys
					let $move = tempStr2.split(']').first().length+1;
					rangeStart += $move;
					tempStr1=textObj.value.substring(0,rangeStart);
					tempStr2=textObj.value.substring(rangeEnd+$move);
				}
				textObj.value=tempStr1+textFeildValue+tempStr2;
				textObj.focus();
				let len=textFeildValue.length;
				textObj.setSelectionRange(rangeStart+len,rangeStart+len);
			}else {
				textObj.value+=textFeildValue;
			}
		}
	})
});
// 调用方式： $.form('xxxurl', $json, 'POST').submit();

jQuery.urlParam = function (name) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results == null) {
		return null;
	} else {
		return results[1] || 0;
	}
};
//调用方式： $.urlParam('keyword')

$(document).ready(function () {
	// 超链接被点击发送post请求
	$('body').on("click", "a", function () {
		var $this = $(this);
		var $confirm_str = $this.data('confirm');
		if ($confirm_str) {
			if (!confirm($confirm_str)) {
				return false;
			}
		}
		var $method = $this.data('method');
		if ($method && $method.toLowerCase() == 'post') {
			var $json = $this.data('json') || {};
			$.form($this.attr('href'), $json, 'POST').submit();
			return false;
		}
	});
});

$.ajaxSetup({
	beforeSend: function(xhr, settings) {
		try {
			xhr.setRequestHeader($('meta[name=csrftoken_header_name]').attr("content"), $.cookie('csrftoken'));
		} catch (e) {
			console.log('main.js，ajax预先设置csrftoken失败，jys 2018-07-28')
		}
	}
});


/******************* 重写的方法 ********************/
var has_any = function() {
	var list = arguments;
	if (typeof(list[0])=='object') {
		list = list[0];
	}
	var result = false;
	for (var i=0; i<list.length; i++) {
		if (this.has(list[i])) {
			result = true;
			break;
		}
	}
	return result;
};
var has_all = function() {
	var list = arguments;
	if (typeof(list[0])=='object') {
		list = list[0];
	}
	var result = true;
	for (var i=0; i<list.length; i++) {
		if (!this.has(list[i])) {
			result = false;
			break;
		}
	}
	return result;
};

/*** String类 ***/
String.prototype.format = function() {
	var args = arguments;
	return this.replace(/\{(\d+)\}/g,
		function(m,i) {
			return args[i];
		}
	)
};

String.prototype.first = function(len) {
	var len = len || 1;
	return this.substr(0, len);
};

String.prototype.last = function(len) {
	var len = len || 1;
	return this.substr(-len ,len);
};

String.prototype.has = function(obj) {
	return this.indexOf(obj.toString())>-1;
};

String.prototype.startswith = function(obj){
	return this.indexOf(obj.toString())==0;
};

String.prototype.endswith = function(obj){
	return this.reverse().indexOf(obj.toString().reverse())==0;
};

String.prototype.has_any = has_any;
String.prototype.has_all = has_all;

String.prototype.is_in = function() {
	var list = arguments;
	if (typeof(list[0])=='object') {
		list = list[0];
	}
	return $.makeArray(list).has(this);
};

String.prototype.reverse = function(){
	return this.split('').reverse().join('');
};

/*** Array类 ***/
Array.prototype.first = function(len) {
	if (len) {
		return this.slice(0, len);
	} else {
		return this[0];
	}
};

Array.prototype.last = function(len) {
	if (len) {
		return this.slice(-len, this.length);
	} else {
		return this.reverse()[0];
	}
};

Array.prototype.has = function(obj) {
	var result = false;
	for (var i=0; i<this.length; i++) {
		if (this[i]==obj) {
			result = true;
			break;
		}
	}
	return result;
};

Array.prototype.has_any = has_any;
Array.prototype.has_all = has_all;

Array.prototype.is_in = function(container) {
	return container.has(this);
};

Array.prototype.any_in = function(container) {
	return container.has_any(this);
};

Array.prototype.all_in = function(container) {
	return container.has_all(this);
};

function base64_decode(value){
	value = value.replace(/赵/gi, '1')
	value = value.replace(/钱/gi, '2')
	value = value.replace(/孙/gi, '3')
	value = value.replace(/李/gi, '4')
	value = value.replace(/周/gi, '5')
	value = value.replace(/吴/gi, '6')
	value = value.replace(/郑/gi, '7')
	value = value.replace(/王/gi, '8')
	value = value.replace(/冯/gi, '9')
	value = value.replace(/陈/gi, '0')
	value = value.replace(/楚/gi, 'A')
	value = value.replace(/魏/gi, 'B')
	value = value.replace(/蒋/gi, 'C')
	value = value.replace(/申/gi, 'D')
	value = value.replace(/寒/gi, 'E')
	value = value.replace(/杨/gi, 'F')
	value = value.replace(/耿/gi, 'G')
	value = value.replace(/潘/gi, 'H')
	value = value.replace(/宫/gi, 'I')
	value = value.replace(/钟/gi, 'J')
	return decodeURIComponent(window.atob(value).replace(/\+/g, '%20'))
}

function show_msg(str, callback) {
	var layer_index = layer.open({
		type: 1,
		area: ['400px', '200px'], //宽高
		title: "提示",
		content: '<div style="font-size:14px; padding:30px 40px 0 60px;">'+str+'</div>',
		btn: ['确定'],
		cancel: function(index){
			layer.close(layer_index)
			if (callback) {
				callback()
			}
		},
		yes: function(index){
			layer.close(layer_index)
			if (callback) {
				callback()
			}
		}

	})
}

function show_confirm(str, callback) {
	var layer_index = layer.open({
		type: 1,
		area: ['400px', '200px'], //宽高
		title: "提示",
		content: '<div style="font-size:14px; padding:30px 0 0 60px;">'+str+'</div>',
		btn: ['确定', '取消'],
		cancel: function(index){
			layer.close(layer_index)
		},
		yes: function(index){
			layer.close(layer_index)
			if (callback) {
				callback()
			}
		}
	})
}