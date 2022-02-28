(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            var ifFormat = '%d/%m/%Y';
            function onUpdate(cal) {
                var inputFieldId = cal.inputField;
            	//alert(inputFieldId);
                var time = cal.date.getTime();
                var date = new Date(time);
                var dateField = YAHOO.util.Dom.get(inputFieldId);
                if (dateField) {
                    dateField.value = date.print(ifFormat);
                }
                //var timeField = YAHOO.util.Dom.get('f_time');
                //if (timeField) {
                //    timeField.value = date.print('%H:%M');
                //}
            }
            //all input elements whose names begin with "f_date". This is a job for the "starts with" ("^=") operator:
            var nodes = YAHOO.util.Selector.query('input[id^=f_date]');
            for (var i = 0; i < nodes.length; i++) {
                var inputFieldId = nodes[i].id;
            	//alert(inputFieldId);
                Calendar.setup({
                    inputField  : inputFieldId,
                    //eventName   : 'dblclick',
                    ifFormat    : ifFormat,
                    firstDay    : '0', //Sunday
                    //showsTime   : true,
                    //timeFormat  : '24',
                    //align       : 'Br',
                    //singleClick : false,
                    cache       : true,
                    onUpdate    : onUpdate
                });
                YAHOO.util.Event.addListener(inputFieldId, 'click', function(e) {
                	var el = window.calendar.element;
                	YAHOO.util.Dom.setStyle(el, 'left', e.clientX - 40); //e.layerX
                	YAHOO.util.Dom.setStyle(el, 'top', e.clientY + 15); //e.layerY
                	YAHOO.util.Dom.setStyle(el, 'z-index', 400);
                });
            }
        }
    });
    loader.insert();
})();