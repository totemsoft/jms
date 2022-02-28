YAHOO.namespace("jms.home");

(function() {
    var Dom = YAHOO.util.Dom,
        Event = YAHOO.util.Event,
        layout1 = null;
        
    /////////////////////
    //Pipes callback
    /////////////////////
    YAHOO.jms.home.handleSuccess = function(o) {
        var responseData = o.responseText;
        //YAHOO.log('Data returned to handleSuccess: ' + responseData, 'info', 'home.js');
        Dom.get('homeDiv').innerHTML = responseData;
    };
    YAHOO.jms.home.handleFailure = function(o) {
        var response = o.responseText;
        //Dom.get('errors').innerHTML = response;
        alert("Failure: " + o.status + ' ' + response);
    }
    YAHOO.jms.home.callback = {
        success: YAHOO.jms.home.handleSuccess,
        failure: YAHOO.jms.home.handleFailure,
        argument: { foo: "foo", bar: "bar" }
    };

    //YAHOO.log('Using loader to fetch datatable and editor (for the toolbar)', 'info', 'home.js');
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            //YAHOO.log('Inject some HTML for the content of home layout.', 'info', 'home.js');

            layout1 = new YAHOO.widget.Layout('homeHolder', {
                parent: YAHOO.jms.layout,
                units: [
                    { position: 'top', id: 'welcomeWrapper', gutter: '0 0 1 0' },
                    { position: 'center', id: 'center1', gutter: '7px 0 5px 0', scroll: true }
                ]
            });

            //before the resize, update the parent with the proper height
            layout1.on('beforeResize', function() {
                Dom.setStyle('homeHolder', 'height', Dom.getStyle(YAHOO.jms.tabView._contentParent, 'height'));
            });

            //On resize, resize the table and set the custom width on the id Column
            layout1.on('resize', function() {
                //Dom.get('center1').setStyle('top', Dom.get('welcomeWrapper').clientHeight);
                  this.getUnitByPosition('center').setStyle('top', Dom.get('welcomeWrapper').clientHeight);
            }, layout1, true);

            layout1.on('render', function() {
                window.setTimeout(function() {
                    //full reload - menu link clicked
                    var url = YAHOO.jms.currentURL;
                    //YAHOO.log('Getting render data from: ' + url, 'info', 'home.js');
                    YAHOO.util.Connect.asyncRequest('GET', url, YAHOO.jms.home.callback);                       
                }, 100);
            }, layout1, true);
            layout1.render();
            YAHOO.jms.home.layout = layout1;
        }
    });
    
    //Have loader insert only the JS files.
    loader.insert({}, 'js');
})();