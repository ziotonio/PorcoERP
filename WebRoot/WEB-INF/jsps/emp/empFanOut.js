/**
 * this file is called in input.js
 * @returns
 */
function reverseClick(){
$("#reverse").click(function() {
			$("[name=roles]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });

});
}
