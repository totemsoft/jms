YAHOO.namespace('jms.about');

// about scope variables
YAHOO.jms.about = {
    //dataTable: null
};

(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
        	YAHOO.jms.createTabView('tabViewDiv', {
        		onActiveIndexChange: function(e) {
        			switch (e.newValue) {
        				case 0: break;
        			}
        		}
        	});
        	YAHOO.jms.onAvailableTab('tab0', 0,
                {dataSrc: 'about.do?dispatch=about'});
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();