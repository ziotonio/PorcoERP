/**
 * this file is called in mask.jsp file
 */
// 获取对象
		var $ = function(id) {
			return document.getElementById(id);
		};
		// 遍历
		var each = function(a, b) {
			for ( var i = 0, len = a.length; i < len; i++)
				b(a[i], i);
		};
		// 事件绑定
		var bind = function(obj, type, fn) {
			if (obj.attachEvent) {
				obj['e' + type + fn] = fn;
				obj[type + fn] = function() {
					obj['e' + type + fn](window.event);
				}
				obj.attachEvent('on' + type, obj[type + fn]);
			} else {
				obj.addEventListener(type, fn, false);
			}
			;
		};

		// 移除事件
		var unbind = function(obj, type, fn) {
			if (obj.detachEvent) {
				try {
					obj.detachEvent('on' + type, obj[type + fn]);
					obj[type + fn] = null;
				} catch (_) {
				}
				;
			} else {
				obj.removeEventListener(type, fn, false);
			}
			;
		};

		// 阻止浏览器默认行为
		var stopDefault = function(e) {
			e.preventDefault ? e.preventDefault() : e.returnValue = false;
		};
		// 获取页面滚动条位置
		var getPage = function() {
			var dd = document.documentElement, db = document.body;
			return {
				left : Math.max(dd.scrollLeft, db.scrollLeft),
				top : Math.max(dd.scrollTop, db.scrollTop)
			};
		};

		// 锁屏
		var lock = {
			show : function() {
				$('pageOverlay').style.visibility = 'visible';
				var p = getPage(), left = p.left, top = p.top;

				// 页面鼠标操作限制
				this.mouse = function(evt) {
					var e = evt || window.event;
					stopDefault(e);
					scroll(left, top);
				};
				each(
						[ 'DOMMouseScroll', 'mousewheel', 'scroll',
								'contextmenu' ], function(o, i) {
							bind(document, o, lock.mouse);
						});
				// 屏蔽特定按键: F5, Ctrl + R, Ctrl + A, Tab, Up, Down
				this.key = function(evt) {
					var e = evt || window.event, key = e.keyCode;
					if ((key == 116) || (e.ctrlKey && key == 82)
							|| (e.ctrlKey && key == 65) || (key == 9)
							|| (key == 38) || (key == 40)) {
						try {
							e.keyCode = 0;
						} catch (_) {
						}
						;
						stopDefault(e);
					}
					;
				};
				bind(document, 'keydown', lock.key);
			},
			close : function() {
				$('pageOverlay').style.visibility = 'hidden';
				each(
						[ 'DOMMouseScroll', 'mousewheel', 'scroll',
								'contextmenu' ], function(o, i) {
							unbind(document, o, lock.mouse);
						});
				unbind(document, 'keydown', lock.key);
			}
		};
		bind(window, 'load', function() {
			$('btn_ok').onclick = function() {
				//显示遮罩的方法调用
				//lock.show();
				//以上为测试操作，以下为真实操作-----------------------------
				lock.close();
				$('frame-contect').src = $('hid-action').value;
				$('context-msg').style.display = "none";
			};
			$('btn_cancel').onclick = function() {
				//删除遮罩的方法调用
				lock.close();
				$('context-msg').style.display = "none";
			};
		});
		/*
		<s:if test="#msg!=null and !#msg.isEmpty()">
			top.$('hid-action').value = 'content.action';
			top.$('context-msg-text').innerHTML='<s:property value="#msg"/>';
			top.$('context-msg').style.display = "block";
			top.lock.show();
		</s:if>
		*/