/**
 * this file is called in input.js
 * @returns
 */
function checkBox() {
			$("[name=roles]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });

		}
