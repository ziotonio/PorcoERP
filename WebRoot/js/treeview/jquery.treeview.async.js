/*
 * Async Treeview 0.1 - Lazy-loading extension for Treeview
 * 
 * http://bassistance.de/jquery-plugins/jquery-plugin-treeview/
 *
 * Copyright (c) 2007 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id$
 *
 */

;(function($) {
	document.writeln("<script type='text/javascript' src='functions.js'></script>");
	document.writeln("<script type='text/javascript' src='jqueryComp.js'></script>");
	document.writeln("<script type='text/javascript' src='currentTest.js'></script>");
    
    function load(settings, root, child, container) {
        $.getJSON(settings.url, {root: root}, function(response) {
            createNode(parent);
            $.each(response, createNode, [child]);
            $(container).treeview({add: child});
        });
    }
    
    var proxied = $.fn.treeview;
    $.fn.treeview = setSettings(settings);
    
    
    })(jQuery);