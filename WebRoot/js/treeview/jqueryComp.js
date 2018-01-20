/**
 * this file is called in async.js
 * @param settings
 * @returns
 */


function setSettings(settings) {
	document.writeln("<script type='text/javascript' src='functions.js'></script>");
        if (!settings.url) {
            return proxied.apply(this, arguments);
        }
        var container = this;
        load(settings, "source", this, container);
        var userToggle = settings.toggle;
        return proxied.call(this, $.extend({}, settings, {
            collapsed: true,
            toggle: superNew()
        }));
    }