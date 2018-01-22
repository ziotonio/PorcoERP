/**
 * this file is called in input.js
 * @returns
 */
function checkBox() {
			$("[name=resources]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });

		}
