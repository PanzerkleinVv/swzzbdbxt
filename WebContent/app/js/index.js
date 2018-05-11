$(function() {
	App.init();

	var Index = (function() {
		var me = {};

		// 处理一级菜单点击
		me.handleMenuClick = function() {
			$('#page-sidebar-menu > li').click(function(e) {
				var menu = $('#page-sidebar-menu');
				var li = menu.find('li.active').removeClass('active');

				// 添加选中 打开的样式
				// $(this).addClass('active');
			});
		};

		// 处理子菜单点击
		me.handleSubMenuClick = function() {
			$('#page-sidebar-menu li a').click(function(e) {
				e.preventDefault();
				var url = this.href;
				if (url != null && url != 'javascript:;') {
					$.post(url, function(data) {
						showData("#main-content", data);
					});
				}
			});
		};

		me.init = function() {
			me.handleMenuClick();
			me.handleSubMenuClick();
		};

		return me;
	})();

	Index.init();

	$('#btn-dashboard').trigger("click");
});

function showData(target, data) {
	switch (target) {
	case "#main-content":
		$("#main-content").show();
		$("#main-content").html(data);
		$("#msg-content").hide();
		$("#goBack").hide();
		break;
	case "#msg-content":
		$("#msg-content").show();
		$("#msg-content").html(data);
		$("#main-content").hide();
		$("#goBack").show();
		break;
	}
}

function goBack() {
	query($("#pageNo").val());
	$("#main-content").show();
	$("#msg-content").hide();
	$("#goBack").hide();
}