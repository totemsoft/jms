(function() {
    //Call loader the first time
    var loader = new YAHOO.util.YUILoader({
        base: 'yui/',
        rollup: true,
        onSuccess: function() {
            /**
             * Obtain an array of the links in container
             */
            function getTitledLinks() {
                var ids = [];
                var container = YAHOO.util.Dom.get("dataTable");
                var candidates = container.getElementsByTagName('a');
                var nodes = YAHOO.util.Selector.filter(candidates, '[title]');
                for (var i = 0; i < nodes.length; i++) {
                    var a = nodes[i];
                    ids.push(a.id);
                }
                return ids;
            }
            if (YAHOO.jms.calendarTooltip) {
            	YAHOO.jms.calendarTooltip.destroy();
            	YAHOO.jms.calendarTooltip = null;
            }
            /**
             * set the title attribute on each of the links, to drive the tooltip's text
             */
            var groupIds = getTitledLinks();
            //YAHOO.log('groupIds: ' + groupIds, 'info', 'calendar.js');
            // We'll also setup Tooltip to use the FADE ContainerEffect
            YAHOO.jms.calendarTooltip = new YAHOO.widget.Tooltip('calendarTooltip', {
                context: groupIds,
                //effect: {effect: YAHOO.widget.ContainerEffect.FADE, duration: 0.20},
                autodismissdelay: 30000 //The number of milliseconds to wait before automatically dismissing the Tooltip. Defaults to 5000.
            });
        }
    });
    loader.insert();
})();