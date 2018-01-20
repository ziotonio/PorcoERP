/**
 * this file is called in jqueryComp file
 * @param parent
 * @returns
 */
function createNode(parent) {
	document.writeln("<script type='text/javascript' src='functions.js'></script>");
	var spanText = "<span>" + this.text + "</span>";
	var test = ("<li/>").attr("id", this.id || "");
	test.html(spanText);
	test.appendTo(parent);
    var current = $.test;
    if (this.classes) {
        current.children("span").addClass(this.classes);
    }
    if (this.expanded) {
        current.addClass("open");
    }
    newFunction();
}