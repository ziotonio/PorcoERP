/**
 * this file is called in input.js
 * @returns
 */
function checkedFun() {
                $(this).attr("checked", !$(this).attr("checked"));
}

function clickAction() {
	$("[name=resources]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
}
