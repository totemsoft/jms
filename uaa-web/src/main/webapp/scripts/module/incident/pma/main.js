YAHOO.namespace('jms.incidentPma');

// incidentPma scope variables
YAHOO.jms.incidentPma = {
    //dataTable: null
};

(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
        	YAHOO.jms.createTabView('tabViewDiv', {
        		onActiveIndexChange: function(e) {
        			switch (e.newValue) {
        				case 0: break;
        				case 1: break;
        			}
        		}
        	});
        	YAHOO.jms.onAvailableTab('tab0', 0,
                {dataSrc: 'incidentPma.do?dispatch=new'});
        	YAHOO.jms.onAvailableTab('tab1', 1,
                {dataSrc: 'incidentPma.do?dispatch=review'});
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();