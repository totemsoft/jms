YAHOO.namespace('jms.admin');

// admin scope variables
YAHOO.jms.admin = {
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
        				case 3: break;
        				case 4: break;
        				case 5: break;
        				case 6: break;
        			}
        		}
        	});
        	YAHOO.jms.onAvailableTab('tab0', 0,
                {dataSrc: 'admin.do?dispatch=import'});
        	YAHOO.jms.onAvailableTab('tab1', 1,
                {dataSrc: 'admin.do?dispatch=undoExport'});
        	YAHOO.jms.onAvailableTab('tab2', 2,
                {dataSrc: 'admin.do?dispatch=chargeRates'});
        	YAHOO.jms.onAvailableTab('tab3', 3,
                {dataSrc: 'admin.do?dispatch=leniency'});
        	YAHOO.jms.onAvailableTab('tab4', 4,
                {dataSrc: 'admin.do?dispatch=commonwealth'});
        	YAHOO.jms.onAvailableTab('tab5', 5,
                {dataSrc: 'admin.do?dispatch=customers'});
        	YAHOO.jms.onAvailableTab('tab6', 6,
                {dataSrc: 'admin.do?dispatch=audit'});
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();