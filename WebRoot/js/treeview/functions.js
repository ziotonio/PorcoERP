/**
 * this file is called in jquery.treeview.async.js
 * @returns
 */  

function newFunction() {
        if (this.hasChildren || this.children && this.children.length) {
            var branch = $("<ul/>").appendTo(current);
            if (this.hasChildren) {
                current.addClass("hasChildren");
                createNode.call({
                    text: "placeholder",
                    id: "placeholder",
                    children: []
                }, branch);
            }
            if (this.children && this.children.length) {
                $.each(this.children, createNode, [branch]);
            }
        }
    }

    function superNew() {
        var $this = $(this);
        if ($this.hasClass("hasChildren")) {
            var childList = $this.removeClass("hasChildren").find("ul");
            childList.empty();
            load(settings, this.id, childList, container);
        }
        if (userToggle) {
            userToggle.apply(this, arguments);
        }
    }