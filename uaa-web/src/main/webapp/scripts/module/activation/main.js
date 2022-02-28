YAHOO.namespace('jms.activation');

// activation scope variables
YAHOO.jms.activation = {
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
        				case 2: break;
        			}
        		}
        	});
        	YAHOO.jms.onAvailableTab('tab0', 0,
                {dataSrc: 'activation.do?dispatch=new'});
        	YAHOO.jms.onAvailableTab('tab1', 1,
                {dataSrc: 'activation.do?dispatch=reviewed'});
        	YAHOO.jms.onAvailableTab('tab2', 2,
                {dataSrc: 'activation.do?dispatch=investigation'});
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();