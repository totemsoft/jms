YAHOO.namespace("jms");

var YL = YAHOO.lang,
    YUC = YAHOO.util.Connect,
    YUD = YAHOO.util.Dom,
    YUE = YAHOO.util.Event,
    YUG = YAHOO.util.Get,
    YUH = YAHOO.util.History,
    YUS = YAHOO.util.Selector,
    YJ = YAHOO.jms;

var debug = false;

var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
    calendarYear = null,
    calendarMonth = null;

var dataToolbar = null,
    dataTable = null,
    currentMenuItems = [],
    currentScript = null,
    currentFilters = [];

var autoCompleteConfig = {
        prehighlightClassName: 'yui-ac-prehighlight',
        forceSelection: false,
        autoHighlight: false,
        //maxResultsDisplayed: 20,
        useShadow: true,
        useIFrame: true,
        queryDelay: 0.5,
        minQueryLength: 1
        //,animVert: .01
    },
    autoCompletes = [],
    dataSources = [];

(function() {
    YAHOO.jms = {
        module: 'jmsModule',
        form: 'dataForm',
        //
        tabView: null,
        tabViewActiveIndex: 'YAHOO.jms.tabView.activeIndex',
        getTab: function(oTabId) {
            var tabs = YAHOO.jms.tabView.get('tabs');
            for (var i = 0; i < tabs.length; i++) {
                var tab = tabs[i];
                if (tab.get('id') == oTabId) {
                    return tab;
                }
            }
            return null;
        },
        //
        alertPanel: null,
        editPanel: null,
        htmlInput: null,
        helpTitle: null,
        //
        calendarTooltip: null, // scripts/calendar.js
        supplierTooltip: null, // scripts/module/file/edit.js
        //
        menuBar: null,
        menuBarHeight: 0,
        //
        data: {
            fields: [],
            columnDefs: [],
            rows: [],
            menuItems: [],
            filters: [],
            actions: []
        },
        //
        dataLoaded: false,
        dataLoading: false,
        currentURL: null, //'home/findHome.do?dispatch=find&title=Home',
        currentRow: null,
        currentAction: null,
        currentScript: null,
        /**
         * Executes the supplied function in the context of the supplied object.
         * @method execute
         * @param o the context object
         * @param fn {Function|String} the function to execute or the name of the method in the 'o' object to execute
         * @param data [Array] data that is provided to the function. This accepts either a single item or an array.
         * If an array is provided, the function is executed with one parameter for each array item.
         * If you need to pass a single array parameter, it needs to be wrapped in an array [myarray]
         * @return a result object.
         */
        getFunction: function(o, fn) {
            var namespace;
            if (YL.isString(o)) {
                if (o == 'YAHOO.jms.mailOut') namespace = YAHOO.jms.mailOut;
                else if (o == 'YAHOO.jms.mailIn') namespace = YAHOO.jms.mailIn;
                else namespace = YAHOO.jms;
            } else {
                namespace = namespace || {};
            }
            var m = fn;
            if (YL.isString(fn)) {
                m = namespace[fn];
            }
            if (!m) {
                throw new TypeError('method undefined: ' + fn);
            }
            return m;
        },
        execute: function(o, fn, data) {
            var namespace;
            if (YL.isString(o)) {
                if (o == 'YAHOO.jms.mailOut') namespace = YAHOO.jms.mailOut;
                else if (o == 'YAHOO.jms.mailIn') namespace = YAHOO.jms.mailIn;
                else namespace = YAHOO.jms;
            } else {
                namespace = namespace || {};
            }
            var m = fn;
            if (YL.isString(fn)) {
                m = o[fn];
            }
            if (!m) {
                throw new TypeError('method undefined: ' + fn);
            }
            var d = data;
            if (d && !L.isArray(d)) {
                d = [data];
            }
            return m.apply(o, d || []);
        },
        /**
         * progress
         */
        progressStart: function(args) {
            var argument = args[1];
            if (!argument || argument.silentProgress !== true) {
                YAHOO.jms.dataLoading = true;
                YUD.addClass(YAHOO.jms.tabView._tabParent, 'loading');
            }
        },
        progressStop: function(args) {
            if (YAHOO.jms.pageUnload === true) {
                return;
            }
            var response = args[0];
            var argument = args[1] || (response && response.argument); // on success [argument = response.argument]
            if (response && response.argument) {
                response.argument = null;
            }
            if (!argument || argument.silentProgress !== true) {
                YAHOO.jms.dataLoading = false;
                YUD.removeClass(YAHOO.jms.tabView._tabParent, 'loading');
            }
        },
        /**
         *
         */
        toLocaleString: function(oNumber, oScale) {
            //return new Number(oNumber).toLocaleString();
            oNumber += '';
            var x = oNumber.split('.');
            var x1 = x[0];
            var x2 = x.length > 1 && oScale > 0 ? '.' + this.roundNumber(parseInt(x[1]), oScale) : '';
            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(x1)) {
                x1 = x1.replace(rgx, '$1' + ',' + '$2');
            }
            return x1 + x2;
        },
        /**
         * Round to certain decimal points
         */
        roundNumber: function(value, scale) {
            return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
        },
        /**
         * DataTable cell validation
         */
        validateCurrency: function(oData) {
            var match = oData.match(/\$*(\d{1,3},)*(\d{1,3})+(\.\d{0,2})?/);
            if (match != null && match.length != 0 && match[0] == oData) {
                return oData;
            }
            return undefined;
        },
        /**
         *
         */
        parseFloat: function(value) {
            return value ? parseFloat(value) : 0;
        },
        /**
         * Validate integer input
         */
        inputInteger: function(event, target) {
            return this.inputNumber(event, target, false);
        },
        /**
         * Validate float input
         */
        inputFloat: function(event, target) {
            return this.inputNumber(event, target, true);
        },
        /**
         * Validate number input
         */
        inputNumber: function(event, target, decimals, length) {
            if (!target) target = YUE.getTarget(event);
            if (!length) {
                length = 64;
            }
            var which = event.which;
            var key = event.keyCode;
            if (typeof(which) == 'undefined') {
                which = key;
                key = 0;
            }
            // delete
            if (which == 0 && key == 46) {
                return true;
            }
            // 45='-' minus
            if (which == 45) {
                return true;
            }
            // 46='.' dot
            if (which == 46 && target.value.indexOf('.') == -1 && decimals && target.value.length < length) {
                return true;
            }
            // 8=backspace, 9=tab, 13=CR, 27=
            if (!((which >= 48 && which <= 57 && target.value.length < length) || (key >= 37 && key <= 40) || (key >= 112 && key <= 123) || event.ctrlKey || (key == 8 || key == 9 || key == 13))) {
                event.returnValue = false;
                return false;
            }
            return true;
        },
        /**
         * Replace Microsoft Smart Quotes with regular quotes.
         */
        inputString: function(str) {
            var stringReplacements = new Array();
            stringReplacements[8216] = 39; // '
            stringReplacements[8217] = 39; // '
            stringReplacements[8220] = 34; // "
            stringReplacements[8221] = 34; // "
            stringReplacements[8212] = 45; // -
            for (var c = 0; c < str.length; c++) {
                var code = str.charCodeAt(c);
                var value = stringReplacements[code];
                if (value != undefined) {
                    str = str.substr(0, c) + String.fromCharCode(value) + str.substr(c + 1);
                }
            }
            return str;
        },
        /**
         * phone input validation
         */
        inputPhone: function(event, target) {
            if (!target) target = YUE.getTarget(event);
            var key = event.keyCode;
            // 8=backspace, 46=delete forward, 9=tab, 13=CR, 37=left, 39=right
            if (key == 37 || key == 39 || key == 8 || key == 46 || key == 9 || key == 13) {
                return true;
            }
            var charCode = event.which || event.keyCode;
            var phone = target.value + String.fromCharCode(charCode);
            var match = phone.match(/(\d+\s?)+/);
            return match != null && match.length != 0 && match[0] == phone;
        },
        /**
         *
         */
        formatDate: function(d) {
            if (typeof d === 'string') {
                return d;
            }
            var day = (d.getDate() < 10 ? '0' : '') + d.getDate();
            var month = (d.getMonth() < 9 ? '0' : '') + (d.getMonth() + 1);
            var year = d.getFullYear();
            return day + '/' + month + '/' + year;
        },
        /**
         *
         * @param oTarget
         * @param prefix
         * @returns
         */
        getId: function(oTarget, prefix) {
            // handle children
            while (!(oTarget && oTarget.id && oTarget.id.indexOf(prefix) == 0)) {
                oTarget = oTarget.parentNode;
                if (!oTarget) {
                    return null; // get to the top - nothing found
                }
            }
            var s = oTarget.id.substring(prefix.length);
            // cut the crap (unique suffix, eg .container)
            if (s.indexOf('.') > 0) {
                s = s.substring(0, s.indexOf('.'));
            }
            return s;
        },
        /**
         *
         */
        autocompleteOff: function() {
            var node = YUD.get('fileId');
            if (node) node.setAttribute('autocomplete', 'off');
            node = YUD.get('fileReference');
            if (node) node.setAttribute('autocomplete', 'off');
        },
        /**
         *
         */
        toggle: function(el, active) {
            var elem = YUD.get(el);
            elem.style.display = active ? 'inline' : 'none';
        },
        toggleClass: function(el, selectedClass) {
            if (YUD.hasClass(el, selectedClass)) {
                YUD.removeClass(el, selectedClass);
            } else {
                YUD.addClass(el, selectedClass);
            }
        },
        /**
         * @param root - root element (optional)
         */
        toggleContainers: function(root) {
            root = YUD.get(root);
            var rootPath = root ? root.tagName.toLowerCase() + '[id='+ root.id +'] ' : '';
            var nodes = YUS.query(rootPath + 'span[id^=toggle.]');
            YUE.addListener(nodes, 'click', function(e) {
                var toggleEl = YUE.getTarget(e);
                YAHOO.jms.toggleContainer(toggleEl);
            });
            for (var i = 0; i < nodes.length; i++) {
                var toggleEl = nodes[i];
                var toggleElId = toggleEl.id;
                // only string id's stored in cookie
                var id = parseInt(YAHOO.jms.getId(toggleEl, 'toggle.'));
                if (isNaN(id)) {
                    var toggleElState = YAHOO.util.Cookie.get(toggleElId);
                    if (toggleElState == 'true' || toggleElState == 'false') {
                        YAHOO.jms.toggleContainer(toggleEl, toggleElState == 'true');
                    }
                }
            }
        },
        /**
         * @param toggleEl
         * @param toggleElState - boolean or null
         */
        toggleContainer: function(toggleEl, toggleElState) {
            toggleEl = YUD.get(toggleEl);
            var toggleElId = toggleEl.id;
            var containerEl = YUD.get(toggleElId + '.container');
            if (!toggleElState) {
                toggleElState = YUD.hasClass(toggleEl, 'collapsed');
            }
            if (toggleElState) {
                YUD.removeClass(toggleEl, 'collapsed');
                YUD.setStyle(containerEl, 'display', 'block');
            } else {
                YUD.addClass(toggleEl, 'collapsed');
                YUD.setStyle(containerEl, 'display', 'none');
            }
            // only string id's stored in cookie
            var id = parseInt(YAHOO.jms.getId(toggleEl, 'toggle.'));
            if (isNaN(id)) {
                YAHOO.util.Cookie.set(toggleElId, toggleElState);
            }
        },
        /**
         *
         * @param targetId
         * @param sourceId
         * @param selectedClass
         */
        moveSelected: function(targetId, sourceId, selectedClass) {
            var targetEl = YUD.get(targetId);
            var sourceEl = YUD.get(sourceId);
            var nodes = YAHOO.util.Selector.query('div[id^=' + sourceId + ']', sourceEl);
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                try {if (!node.textContent) node.textContent = node.innerText;} catch(e) {/* IE6 hack */}
                if (!selectedClass || YUD.hasClass(node, selectedClass)) {
                    node.parentNode.removeChild(node);
                    if (selectedClass) {
                        YUD.removeClass(node, selectedClass);
                    }
                    var id = node.id;
                    id = id.replace(sourceId, targetId);
                    YUD.setAttribute(node, 'id', id);
                    var input = node.children[0];
                    input.setAttribute('name', targetId);
                    var referenceNode = targetEl.firstChild;
                    if (referenceNode == null) {
                        targetEl.appendChild(node);
                    } else {
                        // insert in alphabetical order (compare text nodes)
                        var n = referenceNode;
                        while (n != null) {
                            try {if (!n.textContent) n.textContent = n.innerText;} catch(e) {/* IE6 hack */}
                            referenceNode = n;
                            if (node.textContent < n.textContent) { // IE does not like .trim()
                                YUD.insertBefore(node, referenceNode);
                                break;
                            }
                            n = n.nextSibling;
                            if (n == null) {
                                YUD.insertAfter(node, referenceNode);
                            }
                        }
                    }
                }
            }
        },
        /**
         *
         */
        emptyCallback: {
            cache: false,
            success: function(oResponse) {

            },
            failure: function(oResponse) {
                YAHOO.jms.failure(oResponse);
            }
        },
        /**
         *
         */
        viewCallback: {
            cache: false,
            success: function(oResponse) {
                // clear cookie
                YAHOO.util.Cookie.set(YAHOO.jms.tabViewActiveIndex, null);
                // Set the body to the string passed
                var response = oResponse.responseText;
//                var el = YAHOO.jms.layout.getUnitByPosition('center').get('wrap');
//                el.innerHTML = response;
//                YAHOO.jms.loadScripts(el);
                // Parsing JSON strings can throw a SyntaxError exception, so we wrap the call in a try catch block
                var data = {};
                data.tabs = [];
                data.fields = [];
                data.columnDefs = [];
                data.rows = [];
                data.menuItems = [];
                data.filters = [];
                data.actions = [];
                try {
                    // parse
                    var jsonData = YAHOO.jms.parseJsonData(response);
                    data.tabs = jsonData.tabs;
                    data.fields = jsonData.fields;
                    data.columnDefs = jsonData.columnDefs;
                    data.rows = jsonData.rows;
                    data.pagination = jsonData.pagination;
                    data.menuItems = jsonData.menuItems;
                    data.filters = jsonData.filters;
                    data.actions = jsonData.actions;
                    data.subscribe = jsonData.subscribe;
                    YAHOO.jms.data = data;
                    YAHOO.jms.dataReady(data);
                } catch (e) {
                    YAHOO.jms.alert(e.message, oResponse);
                }
            },
            failure: function(oResponse) {
                YAHOO.jms.failure(oResponse);
            }
        },
        /**
         *
         */
        asyncRequest: function(method, action, oCallback, postData, modal) {
            if (!oCallback) {
                //oCallback = YAHOO.jms.emptyCallback;
            }
            if (!postData) {
                postData = null;
            }
            if (!modal) {
                modal = false;
            }
            return YUC.asyncRequest(method, action, oCallback, postData);
        },
        /**
         * send download request
         * used to download document.
         */
        sendDownloadRequest: function(downloadRequestAction) {
            window.open(downloadRequestAction);
        },
        /**
         * send GET request for update
         * used to represent view/edit form.
         */
        sendGetRequest: function(oAction, oScript, oTitle, oCallback, w, h) {
            YAHOO.jms.currentAction = oAction;
            // to avoid multiple script loading
            YAHOO.jms.currentScript = oScript;
            if (oTitle) {
                YAHOO.jms.editPanel.setHeader(oTitle);
            }
            if (!oCallback) {
                oCallback = YAHOO.jms.editActionCallback;
            }
            // set size
            YAHOO.jms._setSize(YAHOO.jms.editPanel, w, h);
            //
            YUC.asyncRequest('GET', oAction, oCallback);
        },
        /**
         * send POST request for update
         * used to represent edit form.
         */
        sendPostRequest: function(oData, oAction, oScript, oTitle, oCallback, w, h) {
            YUC.setDefaultPostHeader(true);
            //YUC.resetDefaultHeaders();
            //
            //YAHOO.jms.currentAction = oAction;
            YAHOO.jms.currentScript = oScript;
            if (oTitle) {
                YAHOO.jms.editPanel.setHeader(oTitle);
            }
            if (!oCallback) {
                oCallback = YAHOO.jms.editActionCallback;
            }
            //
            if (typeof oData.tagName === 'string') {
                oData = YUD.get(oData);
            }
            if (oData && oData.tagName && oData.tagName.toLowerCase() == 'form') {
                var form = YUD.get(oData);
                // handle multi-part
                if (YAHOO.jms.isMultipart(form)) {
                    YUC.setForm(form, true, true);
                } else {
                    YUC.setForm(form);
                }
                YAHOO.jms.fixIEFormHeader();
            } else if (YL.isObject(oData)) {
                var postdata = YAHOO.lang.JSON.stringify(oData);
                YUC.setDefaultPostHeader(false);
                //YUC.initHeader('Accept', 'application/json');
                YUC.initHeader('Content-Type', 'application/json;charset=UTF-8');
            }
            // set size
            if (w !== null || h !== null) {
                YAHOO.jms._setSize(YAHOO.jms.editPanel, w, h);
            }
            // POST to keep user entered data
            YUC.asyncRequest('POST', oAction, oCallback, postdata);
        },
        /**
         *
         */
        isMultipart: function(oForm) {
            var multipart = YUS.test(oForm, 'form[enctype=multipart/form-data]');
            if (!multipart) {
                return false;
            }
            var files = YUS.query('input[type=file][id^=uploadFile]', oForm);
            for (var i = 0; files && i < files.length; i++) {
                if (files[i].value) {
                    return true;
                }
            }
            return false;
        },
        fixIEFormHeader: function() {
            if (YAHOO.env.ua.ie >= 6) {
                //YUC.initHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
                YUC._default_form_header = 'application/x-www-form-urlencoded; charset=UTF-8';
                //YUC.setDefaultXhrHeader('application/x-www-form-urlencoded; charset=UTF-8');
            }
        },
        /**
         *
         */
        failure: function(oResponse) {
            var response = oResponse.responseText;
            var data = YAHOO.jms.parseJsonData(response, true);
            if (data && data.name && data.message) {
                YAHOO.jms.alert(data.name, {status: oResponse.status, responseText: data.message});
            } else if (data && YAHOO.lang.isArray(data.errors)) {
                var error = data.errors[0];
                YAHOO.jms.alert(error.name, {status: oResponse.status, responseText: error.message});
            } else if (YUD.get('errors')) {
                YUD.get('errors').innerHTML = response;
            } else {
                YAHOO.jms.alert('Failure', oResponse);
                //alert('filterActionCallback failed: ' + o.status + ' ' + response);
            }
        },
        /**
         *
         * @param oMessage
         * @param oFunction
         */
        confirm: function(oMessage, oFunction) {
            if (confirm(oMessage)) {
                oFunction();
            }
        },
        /**
         *
         * @param str
         * @param oResponse - optional
         */
        alert: function(str, oResponse) {
            if (YAHOO.jms.alertPanel == null) {
                // Create a SimpleDialog used to mimic an OS dialog
                YAHOO.jms.alertPanel = new YAHOO.widget.SimpleDialog('alert', {
                    fixedcenter: true,
                    visible: false,
                    modal: true,
                    width: '500px',
                    constraintoviewport: true,
                    icon: YAHOO.widget.SimpleDialog.ICON_WARN,
                    buttons: [
                        {
                            text: 'OK',
                            handler: function() {this.hide();},
                            isDefault: true
                        }
                    ]
                });
                YAHOO.jms.alertPanel.setHeader('Alert');
                YAHOO.jms.alertPanel.setBody('Notta');
                YAHOO.jms.alertPanel.render(document.body);
            }
            if (oResponse) {
                YAHOO.jms.alertPanel.status = oResponse.status;
                // Set the body to the string passed
                // SC_PROXY_AUTHENTICATION_REQUIRED = 407
                if (YAHOO.jms.alertPanel.status == 407) {
                    YAHOO.jms.alertPanel.setBody('<b>' + oResponse.responseText + '</b>'); // login page
                } else {
                    YAHOO.jms.alertPanel.setBody(str + '<br/>Status: ' + oResponse.status + '<br/>' + oResponse.responseText);
                }
            } else {
                YAHOO.jms.alertPanel.setBody(str);
            }
            //YAHOO.jms.alertPanel.cfg.setProperty('icon', YAHOO.widget.SimpleDialog.ICON_WARN);
            YAHOO.jms.alertPanel.bringToTop();
            YAHOO.jms.alertPanel.show();
        },
        /**
         * parse JSON string - replace unsafe characters
         */
        parseJsonData: function(jsonData, silent) {
            if (!jsonData) {
                return null;
            }
            try {
                //encode Back Slashes
                jsonData = jsonData.replace(/\\/g, '\\\\');
                //TODO: encode "
                //jsonData = jsonData.replace(/\"/g, '\\\\');
                //parse
                return YAHOO.lang.JSON.parse(jsonData);
            } catch (e) {
                if (!silent) throw e;
            }
        },
        /**
         * execute script(s) - if any
         */
        loadScripts: function(oRootEl) {
            if (oRootEl) {
                var rootPath = oRootEl.tagName.toLowerCase() + '[id='+ oRootEl.id +'] ';
                var scripts = YUS.query(rootPath + 'script[type=text/javascript]');
                for (var i = 0; scripts && i < scripts.length; i++) {
                    var script = scripts[i];
                    var scriptSrc = script.src;
                    if (scriptSrc) {
                        YUG.script(scriptSrc, {
                            onSuccess: function(scriptData) {
                                scriptData.purge(); //removes the script node immediately after executing
                            }
                        });
                    } else {
                        eval(YL.trim(script.innerHTML)); // single javascript function
                    }
                }
                // load css(s) - if any
                var scripts = YUS.query(rootPath + 'link[type=text/css]');
                for (var i = 0; scripts && i < scripts.length; i++) {
                    var script = scripts[i].href;
                    YUG.css(script);
                }
            }
        },
        loadScript: function(oScripts) {
            if (!oScripts) {
                return;
            }
            if (typeof oScripts === 'string') {
                var scripts = oScripts.split(',');
                for (var i = 0; i < scripts.length; i++) {
                    var script = scripts[i];
                    YUG.script(script, {
                        onSuccess: function(scriptData) {
                            scriptData.purge(); //removes the script node immediately after executing
                        }
                    });
                }
            } else if (typeof oScripts === 'object') {
                eval(oScripts.f)(oScripts.p);
            }
        },
        /**
         *
         */
        navigate: function(oAction, oTitle) {
            var state = YUH.getQueryStringParameter('state', oAction) || oAction;
            state += (state.indexOf('?') < 0 ? '?' : '&') + 'title=' + oTitle;
            YUH.navigate(YAHOO.jms.module, state);
        },
        // Update the UI of jms module according to the 'state' parameter
        loadState: function(oState) {
            // update title (last parameter)
            var idx = oState.indexOf('title=');
            var title = idx < 0 ? '' : oState.substring(idx + 6);
            var titleEl = YUD.get('title');
            document.title = (titleEl ? titleEl.value + ' :: ' : '') + title;
            var tab = YAHOO.jms.tabView.get('activeTab');
            tab.set('label', title);
            // check for callback (not default)
            var callback = oState.callback ? oState.callback : YAHOO.jms.viewCallback;
            // first request to load page is always GET
            YAHOO.jms.sendGetRequest(oState, null, null, callback);
            //
            var menus = YAHOO.jms.getMenus();
            for (var i = 0; i < menus.length; i++) {
                var menu = menus[i];
                if (oState.indexOf(menu.getAttribute('href')) >= 0) {
                    YUD.removeClass(YUS.query('#mainMenu li'), 'selected');
                    YUD.addClass(menu.parentNode, 'selected');
                    var parentMenu = menu.parentNode.parentNode.parentNode.parentNode.parentNode;
                    if (parentMenu.tagName.toLowerCase() == 'li') {
                        YUD.addClass(parentMenu, 'selected');
                    }
                    break;
                }
            }
        },
        getData: function(url, script) {
            if (url && !YAHOO.jms.dataLoading) {
                YAHOO.jms.currentScript = script;
                // full reload - menu link clicked
                YAHOO.jms.reloadData(url);
            }
        },
        reload: function() {
            var currentState = YUH.getCurrentState(YAHOO.jms.module);
            YAHOO.jms.loadState(currentState); // the latest history entry
        },
        reloadData: function(url) {
            YUC.asyncRequest('GET', url, YAHOO.jms.viewCallback);
        },
        dataReady: function(oData) {
            var tabs = oData.tabs || ['tab1', 'tab2'];
            YAHOO.jms.initTabs(tabs);
            YAHOO.jms.initMenu();
            YAHOO.jms.initFilter();
            YAHOO.jms.initDataTable(oData);
            YAHOO.jms.dataLoaded = true;
            YAHOO.jms.loadScript(YAHOO.jms.currentScript);
        },
        dataRefresh: function(oDataTable, oData) {
            if (!oDataTable) {
                oDataTable = dataTable;
            }
            if (!oData) {
                oData = YAHOO.jms.data;
            }
            // Clears out all cell selections.
            oDataTable.unselectAllCells();
            // Clears out all row selections.
            oDataTable.unselectAllRows();
            // hide/show columns
            var columnDefs = oData.columnDefs;
            //var columnDefs = oResponse.meta.columns;
            for (var c = 0; columnDefs && c < columnDefs.length; c++) {
                var columnDef = columnDefs[c];
                if (columnDef.hidden) {
                    oDataTable.hideColumn(columnDef.key);
                } else {
                    oDataTable.showColumn(columnDef.key);
                }
            }
            var oRows = oData.rows || oData;
            oDataTable.disable();
            // delete all rows
            while (oDataTable.getRecordSet().getLength() > 0) {
                oDataTable.deleteRow(0);
            }
            // add new rows
            if (oRows && oRows.length > 0) {
                oDataTable.addRows(oRows);
            }
            oDataTable.undisable();
        },
// =======================================================================
// tabView.js
// =======================================================================
        initTabs: function(oTabs) {
            var activeIndex = null;
            var tabs = YAHOO.jms.tabView.get('tabs'); // for now - tab1, tab2 labelEl
            for (var i = tabs.length - 1; i >= 0; i--) {
                var tab = tabs[i];
                var tabId = tab.get('element').id;
                // legacy tabs - list and details
                if (tabId === 'tab1' || tabId === 'tab2') {
                    var labelEl = tab.get('labelEl').parentNode.parentNode;
                    var idx = null; for (var t = 0; t < oTabs.length; t++) {if (oTabs[t] === tabId) {idx = t; break;}} // oTabs.indexOf(tabId);
                    if (idx !== null) {
                        YUD.removeClass(labelEl, 'hidden');
                        activeIndex = i;
                        oTabs.splice(idx, 1);
                    } else {
                        YUD.addClass(labelEl, 'hidden');
                    }
                } else {
                    // menu specific tabs - new more flexible way of presenting data
                    YAHOO.jms.tabView.removeTab(tab);
                    tab.destroy();
                }
            }
            for (var t = 0; t < oTabs.length; t++) {
                var tab = oTabs[t];
                if (typeof tab === 'object') {
                    YAHOO.jms.initTab(tab);
                }
            }
            if (activeIndex !== null) {
                setTimeout(function() {YAHOO.jms.tabView.set('activeIndex', activeIndex, false);}, 100); // silent
            }
        },
        // initialise tab
        initTab: function(oTabData) {
            //YAHOO.jms.resizeTabView();
            var tabId = oTabData.id;
            var config = oTabData.config;
            // get tab (existing or new)
            var tab = null;
            var tabs = YAHOO.jms.tabView.get('tabs'); // for now - tab1, tab2 labelEl
            for (var i = 0; i < tabs.length; i++) {
                if (tabs[i].get('element').id === tabId) {
                    tab = tabs[i];
                }
            }
            if (!tab) {
                tab = new YAHOO.widget.Tab({
                    id: tabId,
                    label: config && config.label ? config.label : tabId,
                    //active: config && config.active === true,
                    content: '<div><div class="processing center"></div></div>'
                });
                YAHOO.jms.tabView.addTab(tab);
            }
            //var el = tab.get('contentEl');
            // any aditional configuration
            if (config) {
                tab.set('cacheData', config.cacheData === false ? false : true);
                if (config.dataSrc) {
                    tab.set('dataSrc', config.dataSrc);
                    if (config.postData) {
                        tab.set('loadMethod', 'POST');
                        tab.set('postData', config.postData);
                    }
                }
                if (YL.isFunction(config.initHandler)) {
                    config.initHandler();
                }
                if (config.active === true) {
                    YAHOO.jms.tabView.set('activeTab', tab);
                }
            }
            // callbacks for loading data
            tab.loadHandler = {
                success: function(oResponse) {
                    this.set('content', oResponse.responseText);
                    YAHOO.jms.loadScripts(this.get('contentEl'));
                },
                failure: function(oResponse) {
                    YAHOO.jms.emptyCallback.failure(oResponse);
                }
            };
            // When tab is available, update the height..
            YUE.onContentReady(tabId, function() {
                //YAHOO.jms.resizeWindow();
                var el = tab.get('contentEl');
                el.id = tabId + '.dataHolder';
                //el = YUD.get('dataTable');
                //var contentRegion = YUD.getRegion(YAHOO.jms.tabView._contentParent);
                //YUD.setStyle(el, 'height', contentRegion.height + 'px');
                YUD.setStyle(el, 'height', YUD.getStyle(YAHOO.jms.tabView._contentParent, 'height'));
                YUD.setStyle(el, 'overflow-x', 'hidden');
                YUD.setStyle(el, 'overflow-y', 'auto');
            });
            //YUE.onContentReady(tabId + '.content', function() {
            //    YAHOO.jms.loadScripts(this);
            //});
        },
        // resize the tabview
        resizeTabView: function() {
            var ul = YAHOO.jms.tabView._tabParent.offsetHeight;
            var h = (YAHOO.jms.layout.getSizes().center.h - ul) - 2;
            YUD.setStyle(YAHOO.jms.tabView._contentParent, 'height', h + 'px');
        },
// =======================================================================
// menu.js
// =======================================================================
        initMenu: function() {
            // do not refresh menu (e.g. setup main menu item clicked - action codes)
            // TODO: improve check - there is no menu change
            if (YAHOO.jms.data.menuItems == undefined || currentMenuItems.length == YAHOO.jms.data.menuItems.length) {
                //YAHOO.log('No menuItems changed', 'info', 'menu.js');
                return {};
            }
            // destroy/remove menu
            if (YAHOO.jms.menuBar) {
                //YAHOO.log('Destroying the menuBar', 'info', 'menu.js');
                YAHOO.jms.menuBar.destroy();
                YAHOO.jms.menuBar = null;
                YAHOO.jms.menuBarHeight = 0;
            }

            currentMenuItems = YAHOO.jms.data.menuItems;

            //menu (not required for this screen)
            if (currentMenuItems == undefined || currentMenuItems.length == 0) {
                //YAHOO.log('No menuItems found', 'info', 'menu.js');
                YAHOO.jms.menuBarHeight = 3;
                return {};
            }

            //YAHOO.log('Inject menu HTML.', 'info', 'menu.js');
            var d = YUD.get('menuBarDiv');
            var innerHTML = '<div id="menuBar" class="yuimenubar yuimenubarnav"><div class="bd"><ul class="first-of-type">';
            for (var i = 0; i != currentMenuItems.length; i++) {
                var menuItem = currentMenuItems[i];
                innerHTML +=
                    '<li class="yuimenubaritem' + (i == 0 ? ' first-of-type' : '') + '">' +
                        '<a class="yuimenubaritemlabel" href="' + (menuItem.url ? menuItem.url : 'javascript:;') + '">' + menuItem.text + '</a>' +
                    '</li>';
            }
            innerHTML += '</ul></div></div>';
            d.innerHTML = innerHTML;

            //Grab it's height for later use
            YAHOO.jms.menuBarHeight = YUD.get('menuBar').clientHeight + 3;
            //YAHOO.log('menuBarHeight=' + YAHOO.jms.menuBarHeight, 'info', 'menu.js');

            YAHOO.jms.menuBar = new YAHOO.widget.MenuBar('menuBar', {
                autosubmenudisplay: false,
                keepopen: false
                //, zIndex: 101
            });
            YAHOO.jms.menuBar.subscribe("beforeRender", function () {
                if (this.getRoot() == this) {
                    for (i = 0; i != currentMenuItems.length; i++) {
                        var menuItem = currentMenuItems[i];
                        if (menuItem.itemdata) {
                            //menuItem.zIndex = 100;
                            this.getItem(i).cfg.setProperty('submenu', menuItem);
                        }
                    }
                }
            });
            YAHOO.jms.menuBar.render();

            //Setup the click listeners on the menuBar
            YUE.on('menuBar', 'click', function(ev) {
                //YAHOO.log('menuBar clicked: ' + ev, 'info', 'menu.js');
                var tar = YUE.getTarget(ev);
                //YAHOO.log('tar: ' + tar, 'info', 'menu.js');
                if (tar.tagName.toLowerCase() != 'a') {
                    tar = null;
                }
                if (tar && tar.href == 'javascript:;') {
                    tar = null;
                }
                //Make sure we are a link in the list's
                if (tar && YAHOO.util.Selector.test(tar, '#menuBar ul li a')) {
                    var url = tar.href;
                    //YAHOO.log('URL=' + url, 'info', 'menu.js');
                    YUD.removeClass(YAHOO.util.Selector.query('#menuBar li'), 'selected');
                    YUD.addClass(tar.parentNode, 'selected');
                    YUE.preventDefault(ev);
                    //update tab label
                    var title = tar.innerHTML;
                    var tab = YAHOO.jms.tabView.get('activeTab');
                    tab.set('label', title);
                    //get data
                    YAHOO.jms.getData(url);
                }
            });
        },
//=======================================================================
// filter.js
//=======================================================================
        // check if filter is checkbox
        isCheckboxFilter: function(filter) {
            return filter.values && filter.values.length == 2 && filter.values[0] == "true" && filter.values[1] == "false";
        },
        // check if filter is date
        isDateFilter: function(filter) {
            return filter.id && filter.id.indexOf('f_date') == 0;
        },
        /**
         * get all filters query (name=value)
         */
        getSelectedRecordQuery: function(oDataTable) {
            var query = '';
            if (oDataTable) {
                var cd = oDataTable.getColumnSet().getDefinitions();
                for (var c = 0; c < cd.length; c++) {
                    var name = cd[c].name;
                    if (name && name != 'id') {
                        var value = YAHOO.jms.getSelectedRecord(oDataTable).getData(name);
                        if (value) {
                            query += '&' + name + '=' + value;
                        }
                    }
                }
            }
            return query;
        },
        getFilterQuery: function(oIgnoreEmpty) {
            var query = '';
            // check if action already has query (eg ?dispatch=find)
            for (var i = 0; i != currentFilters.length; i++) {
                var f = currentFilters[i];
                var id = f.id ? f.id : ('filter' + i);
                var input = YUD.get(id + 'Input');
                if (input/* && input.value && input.value.length > 0*/) {
                    var checkbox = YAHOO.jms.isCheckboxFilter(f);
                    // add input
                    query += query ? '&' : '';
                    query += f.name + '=';
                    query += checkbox ? input.checked : encodeURIComponent(input.value);
                }
                var hidden = YUD.get(id + 'Hidden');
                if (hidden && (oIgnoreEmpty !== true || hidden.value)) {
                    // add hidden
                    query += query ? '&' : '';
                    query += hidden.name + '=' + hidden.value;
                }
            }
            return query;
        },
        linkToggleButton: function(oToggler, oPushButton, oAutoComplete) {
            oPushButton.on('click', function(e) {
                if (!YUD.hasClass(oToggler, 'open')) {
                    YUD.addClass(oToggler, 'open');
                }
                if (oAutoComplete.isContainerOpen()) { // Is open
                    oAutoComplete.collapseContainer();
                } else { // Is closed
                    oAutoComplete.getInputEl().focus(); // Needed to keep widget active
                    setTimeout(function() { // For IE
                        oAutoComplete.sendQuery('');
                    },0);
                }
            });
            oAutoComplete.containerCollapseEvent.subscribe(function() {YUD.removeClass(oToggler, 'open');});
        },
        /**
         * Generic create filters.
         * Elements with id filterNInput, filterNContainer should exist.
         */
        initFilter: function() {
            //handle empty data
            var filtersDivEl = YUD.get('filtersDiv');
            var filtersLeftDivEl = YUD.get('filtersLeftDiv');
            var filtersCenterDivEl = YUD.get('filtersCenterDiv');
            var filtersRightDivEl = YUD.get('filtersRightDiv');
            if (YAHOO.jms.data.filters == undefined)
            {
                //YAHOO.log('No filters found', 'info', 'filter.js');
                currentFilters = [];
                YAHOO.jms.filtersHeight = 5;
                filtersLeftDivEl.innerHTML = '';
                filtersCenterDivEl.innerHTML = '';
                filtersRightDivEl.innerHTML = '';
                return {};
            }

            //destroy/remove autoCompletes/dataSources
            //YAHOO.log('Destroying the autoCompletes', 'info', 'filter.js');
            for (var i = 0; i != autoCompletes.length; i++) {
                if (autoCompletes[i]) {
                    autoCompletes[i].destroy();
                }
            }
            autoCompletes = [];
            dataSources = [];

            //filters (not required for this screen)
            currentFilters = YAHOO.jms.data.filters;
            if (currentFilters.length == 0) {
                //YAHOO.log('filters are not required', 'info', 'filter.js');
                YAHOO.jms.filtersHeight = 5;
                filtersLeftDivEl.innerHTML = '';
                filtersCenterDivEl.innerHTML = '';
                filtersRightDivEl.innerHTML = '';
                YUD.addClass(filtersDivEl, 'hidden');
                return {};
            } else {
                YUD.removeClass(filtersDivEl, 'hidden');
            }

            //YAHOO.log('Inject filters HTML.', 'info', 'filter.js');
            var innerHTMLLeft = '';
            var innerHTMLCenter = '';
            var innerHTMLRight = '';
            for (i = 0; i != currentFilters.length; i++) {
                var f = currentFilters[i];
                var innerHTML;
                if (f.name) {
                    innerHTML = '<table class="filter nonDataTable"><tr>';
                    var id = f.id ? f.id : ('filter' + i);
                    var hiddenId = id + 'Hidden';
                    var inputId = id + 'Input';
                    var title = f.title ? f.title : f.label;
                    if (YAHOO.jms.isCheckboxFilter(f)) {
                        innerHTML +=
                            '<td>' +
                                '<div id="filter' + i + '">' +
                                    '<label for="' + inputId + '' + '">' + f.label + '</label>' +
                                    '<input id="' + hiddenId + '" type="hidden" name="' + f.name + 'Id" value="' + (f.value ? f.value : '') + '" />' +
                                    '<input id="' + inputId + '" class="filterInput checkbox" type="checkbox" name="' + f.name + '"' + (f.value === true ? ' checked' : '') + ' title="' + title + '" />' +
                                '</div>' +
                            '</td>' +
                            '<td class="width3em yui-ac" />';
                    } else {
                        if (YAHOO.jms.isDateFilter(f)) {
                            innerHTML +=
                                '<td>' +
                                    '<div id="filter' + i + '">' +
                                        '<label for="' + inputId + '' + '">' + f.label + '</label>' +
                                        '<input id="' + inputId + '" class="filterInput yui-ac-input" type="text" name="' + f.name + '" value="' + (f.value ? f.value : '') + '" title="' + title + '" />' +
                                    '</div>' +
                                '</td>' +
                                '<td class="width3em yui-ac" />';
                        } else {
                            innerHTML +=
                                '<td>' +
                                    '<div id="filter' + i + '">' +
                                        '<label for="' + inputId + '' + '">' + f.label + '</label>' +
                                        '<input id="' + hiddenId + '" type="hidden" name="' + f.name + 'Id" value="' + (f.hiddenValue ? f.hiddenValue : '') + '" />' +
                                        '<input id="' + inputId + '" class="filterInput yui-ac-input" type="text" name="' + f.name + '" value="' + (f.value ? f.value : '') + '" title="' + title + '" onkeypress="return YAHOO.jms.search(event);" />' +
                                        '<div id="' + id + 'Container" class="filterContainer"></div>' +
                                    '</div>' +
                                '</td>' +
                                '<td class="width3em yui-ac">' +
                                    '<span id="filter' + i + 'Toggle" class="filterToggle"></span>' +
                                '</td>';
                        }
                    }
                    innerHTML += '</tr></table>';
                } else {
                    // placeholder
                    innerHTML = '<div class="filter"></div>';
                }
                if (i % 3 == 0) {
                    innerHTMLLeft += innerHTML;
                } else if (i % 3 == 1) {
                    innerHTMLCenter += innerHTML;
                } else {
                    innerHTMLRight += innerHTML;
                }
            }
            filtersLeftDivEl.innerHTML = innerHTMLLeft;
            filtersCenterDivEl.innerHTML = innerHTMLCenter;
            filtersRightDivEl.innerHTML = innerHTMLRight;

            //Grab it's height for later use
            var h = YUD.get('filtersDiv').clientHeight;
            YAHOO.jms.filtersHeight = h + 5;
            //YAHOO.log('filtersHeight=' + h, 'info', 'filter.js');

            // Instantiate AutoCompletes
            YAHOO.jms.initAutoCompleteFilters(currentFilters);
            YAHOO.jms.initCalendarInputs();
        },
        initAutoCompleteFilters: function(filters) {
            dataSources = new Array(filters.length);
            autoCompletes = new Array(filters.length);
            // Instantiate DataSource and AutoComplete
            for (var i = 0; i != filters.length; i++) {
                var f = filters[i];
                var id = f.id ? f.id : ('filter' + i);
                var ds = null;
                var ac = null;
                var input = YUD.get(id + 'Input');
                var container = YUD.get(id + 'Container');
                var hidden = YUD.get(id + 'Hidden');
                if (f.values) {
                    var values = f.values;
                    ds = new YAHOO.util.LocalDataSource(values);
                    if (YAHOO.jms.isCheckboxFilter(f)) {
                        ac = null;
                    } else {
                        var isString = values.length != 0 && YL.isString(values[0]);
                        if (!isString) {
                            ds.responseSchema = {fields: ['label','value']};
                        }
                        ac = new YAHOO.widget.AutoComplete(input, container, ds, autoCompleteConfig);
                        ac.minQueryLength = 0;
                        ac.resultTypeList = false;
                        ac.useShadow = true;
                        if (!isString) {
                            // Fired when the input field value has changed when it loses focus.
                            ac.textboxChangeEvent.subscribe(function(oSelf) {
                                var input = YUD.get(this.filterIndex + 'Input');
                                // reset hidden value (if input blank)
                                var hidden = YUD.get(this.filterIndex + 'Hidden');
                                if (input && !input.value && hidden) {
                                    hidden.value = '';
                                }
                            });
                        }
                        var toggle = YUD.get(id + 'Toggle');
                        var toggleButton = new YAHOO.widget.Button({container:toggle});
                        YAHOO.jms.linkToggleButton(toggle, toggleButton, ac);
                    }
                } else if (f.valuesUrl) {
                    ds = new YAHOO.util.XHRDataSource(f.valuesUrl);
                    ds.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;
                    ds.responseSchema = {resultsList:'records',fields:['label','value']};
                    ac = new YAHOO.widget.AutoComplete(input, container, ds, autoCompleteConfig);
                    ac.resultTypeList = false;
                    ac.useShadow = true;
                    //we could need all filter name
                    ac.generateRequest = function(sQuery) {
                        var liveData = this.dataSource.liveData;
                        var query = YAHOO.jms.getFilterQuery();
                        return (liveData.indexOf('?') > 0 ? '&' : '?') + 'query=' + sQuery + '&' + query;
                    };
                }
                if (ac) {
                    if (hidden) {
                        ac.itemSelectEvent.subscribe(function(sType, aArgs) {
                            var ac = aArgs[0]; // reference back to the AC instance
                            //var elLI = aArgs[1]; // reference to the selected LI element
                            var oData = aArgs[2]; // object literal of selected item's result data
                            var hidden = YUD.get(ac.filterIndex + 'Hidden');
                            hidden.value = oData.value;
                        });
                        ac.textboxChangeEvent.subscribe(function(sType, aArgs) {
                            var ac = aArgs[0]; // reference back to the AC instance
                            var input = YUD.get(ac.filterIndex + 'Input');
                            if (!input.value) {
                                var hidden = YUD.get(ac.filterIndex + 'Hidden');
                                hidden.value = '';
                            }
                        });
                        ac.unmatchedItemSelectEvent.subscribe(function(sType, aArgs) {
                            var ac = aArgs[0]; // reference back to the AC instance
                            var hidden = YUD.get(ac.filterIndex + 'Hidden');
                            hidden.value = '';
                        });
                    }
                    if (f.forceSelection == true) {
                        ac.forceSelection = true;
                    }
                    if (f.maxResultsDisplayed) {
                        ac.maxResultsDisplayed = f.maxResultsDisplayed;
                    }
                    ac.filterIndex = id;
                }
                dataSources[i] = ds;
                autoCompletes[i] = ac;
            }
        },
        /**
         * Customised autoComplete filter - will use baseUrl 'findFilter.do'
         * @param inputs
         * @param generateRequestQuery - format '?paramName=paramValue'
         * @param itemSelectEvent - optional handler (eg reload UI)
         */
        initAutoCompletes: function(inputs, generateRequestQuery, itemSelectEvent) {
            // Use an XHRDataSource
            var ds = new YAHOO.util.XHRDataSource('findFilter.do');
            ds.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;
            ds.responseSchema = {resultsList:'records',fields:['label','value']};
            //ds.maxCacheEntries = 5;
            for (var i = 0; i < inputs.length; i++) {
                var input = inputs[i];
                var inputId = input.id;
                var containerId = inputId + '.container';
                var container = YUD.get(containerId);
                if (!container) {
                    container = document.createElement('div');
                    container.setAttribute('id', containerId);
                    input.parentNode.appendChild(container);
                }
                var ac = new YAHOO.widget.AutoComplete(input, container, ds, autoCompleteConfig);
                ac.inputId = inputId;
                ac.resultTypeList = false;
                ac.useShadow = true;
                ac.generateRequest = function(sQuery) {
                    var valueEl = YUD.get(this.inputId + '.value');
                    if (valueEl) {
                        valueEl.value = '';
                    }
                    return generateRequestQuery + sQuery;
                };
                ac.itemSelectEvent.subscribe(function(sType, aArgs) {
                    var ac = aArgs[0]; // reference back to the AC instance
                    //var elLI = aArgs[1]; // reference to the selected LI element
                    var oData = aArgs[2]; // object literal of selected item's result data
                    var valueEl = YUD.get(ac.inputId + '.value');
                    if (valueEl) {
                        valueEl.value = oData.value;
                    }
                });
                if (typeof itemSelectEvent === 'function') {
                    ac.itemSelectEvent.subscribe(itemSelectEvent);
                    ac.textboxChangeEvent.subscribe(itemSelectEvent);
                }
//                ac.textboxChangeEvent.subscribe(function(sType, aArgs) {
//                    var ac = aArgs[0]; // reference back to the AC instance
//                    //var inputEl = YUD.get(ac.inputId);
//                    var valueEl = YUD.get(ac.inputId + '.value');
//                    if (valueEl) {
//                        valueEl.value = '';
//                    }
//                });
//                ac.unmatchedItemSelectEvent.subscribe(function(sType, aArgs) {
//                    var ac = aArgs[0]; // reference back to the AC instance
//                    var hidden = YUD.get(ac.filterIndex + 'Hidden');
//                    hidden.value = '';
//                });
            }
        },
        filterActionCallback: {
            cache: false,
            success: function(o) {
                if (YUD.get('errors')) {
                    YUD.get('errors').innerHTML = '';
                }
                // Update the JS Array that we will feed to the DT
                // Parsing JSON strings can throw a SyntaxError exception, so we wrap the call in a try catch block
                YAHOO.jms.data.rows = [];
                try {
                    var jsonData = YAHOO.jms.parseJsonData(o.responseText);
                    YAHOO.jms.data.rows = jsonData.rows;
                    if (jsonData.columnDefs && jsonData.columnDefs.length != 0) {
                        YAHOO.jms.data.columnDefs = jsonData.columnDefs;
                    }
                } catch (e) {
                    // Invalid JSON data: SyntaxError: parseJSON
                    alert('Invalid JSON data: ' + e);
                    return;
                }
                // refresh data
                YAHOO.jms.dataRefresh(dataTable, YAHOO.jms.data);
                // execute javascript (if any)
                YAHOO.jms.loadScript(currentScript);
            },
            failure: function(o) {
                YAHOO.jms.emptyCallback.failure(o);
            }
        },
        // execute filter
        processFilter: function(filterAction, filterScript) {
            var query = YAHOO.jms.getFilterQuery();
            if (filterAction.indexOf('?') < 0) {
                if (query) {
                    query = '?' + query;
                }
            } else if (query) {
                query = '&' + query;
            }
            // to avoid multiple script loading
            YAHOO.jms.currentScript = filterScript;
            YUC.asyncRequest('GET', filterAction + query, YAHOO.jms.filterActionCallback);
        },
        search: function(oEvent) {
            if (oEvent && oEvent.which != 13) {
                return true;
            }
            YAHOO.jms.processFilter(YAHOO.jms.data.actions.findAction, YAHOO.jms.data.actions.findScript);
            return false;
        },
//=======================================================================
// calendar.js
//=======================================================================
        initCalendarInputs: function(onUpdate) {
            //all input elements whose names begin with "f_date". This is a job for the "starts with" ("^=") operator:
            var nodes = YAHOO.util.Selector.query('input[id^=f_date]');
            for (var i = 0; i < nodes.length; i++) {
                this.initCalendarInput(nodes[i], onUpdate);
            }
        },
        initCalendarInput: function(inputField, onUpdate) {
            var ifFormat = '%d/%m/%Y';
            var inputFieldId = inputField.id;
            if (!inputFieldId) {
                inputFieldId = 'f_date_' + new Date().getTime();
                inputField.id = inputFieldId;
            }
            var showsTime = inputFieldId.indexOf('f_date_time') == 0;
            Calendar.setup({
                inputField  : inputFieldId,
                //eventName   : 'dblclick',
                ifFormat    : ifFormat + (showsTime ? ' %H:%M:%S' : ''),
                firstDay    : '0', //Sunday
                showsTime   : showsTime,
                timeFormat  : '24',
                //align       : 'Br',
                //singleClick : false,
                cache       : true,
                onUpdate    : onUpdate ? onUpdate : function(cal) {
                    var date = cal.date;
                    var inputFieldId = cal.inputField || cal.params.inputField;
                    var inputField = YUD.get(inputFieldId);
                    if (inputField) {
                        inputField.value = date.print(ifFormat);
                        //inputField.fireChangeEvent('value', e);
                        //inputField.setValue(date.print(ifFormat), false); // if true the change events will not be fired.
                        //inputField.fireQueue();
                    }
                    //var timeField = YUD.get('f_time');
                    //if (timeField) {
                    //    timeField.value = date.print('%H:%M');
                    //}
                }
            });
            YUE.addListener(inputField, 'click', function(e) {
                var el = window.calendar ? window.calendar.element : null;
                if (el) {
                    YUD.setStyle(el, 'left', e.clientX - 40); //e.layerX
                    YUD.setStyle(el, 'top', e.clientY + 15); //e.layerY
                    YUD.setStyle(el, 'z-index', 400);
                }
            });
            inputField.setAttribute('autocomplete', 'off');
        },
//=======================================================================
// datatable.js
//=======================================================================
        highlightEditableCell: function(oArgs) {
            var elCell = oArgs.target;
            if (YUD.hasClass(elCell, 'yui-dt-editable')) {
                this.highlightCell(elCell);
            }
        },
        highlightCell: function(oArgs) {
            var elCell = oArgs.target;
            if (YUD.hasClass(elCell, 'yui-dt-highlighted')) {
                this.unhighlightCell(elCell);
            } else {
                this.highlightCell(elCell);
            }
        },
        subscribeDatatableEvents: function(dataTable, oConfig, hasEditable) {
            // Set up editing flow
            if (hasEditable) {
                dataTable.subscribe('cellMouseoverEvent', YAHOO.jms.highlightEditableCell);
                dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
                dataTable.subscribe('cellClickEvent', dataTable.onEventShowCellEditor);
            } else if (oConfig && oConfig.cellSelect == true) {
                dataTable.subscribe('cellMouseoverEvent', dataTable.onEventHighlightCell);
                dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
                dataTable.subscribe('cellClickEvent', YAHOO.jms.highlightCell);
                //dataTable.subscribe('cellClickEvent', dataTable.onEventSelectCell);
                //dataTable.subscribe('cellSelectEvent', dataTable.clearTextSelection);
            } else {
                dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
                dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
                dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
            }
            dataTable.subscribe('checkboxClickEvent', function(oArgs) {
                var elCheckbox = oArgs.target;
                var oRecord = this.getRecord(elCheckbox);
                oRecord.setData('selected', elCheckbox.checked); // hardcoded to 'selected' column name
            });
            // Update payload data on the fly for tight integration with latest values from server
            dataTable.doBeforeLoadData = function(oRequest, oResponse, oPayload) {
                var oData = YAHOO.jms.parseJsonData(oResponse.responseText, true);
           	    if (oData && oData.errors && oData.errors.length != 0) {
                    // show errors
           	    	var error = oData.errors[0];
           	    	this.set('MSG_ERROR', error.message);
           	    } else if (oResponse.meta) {
                    // hide/show columns
                    var columns = oResponse.meta.columns;
                    for (var c = 0; columns && c < columns.length; c++) {
                        var column = columns[c];
                        if (column.hidden) {
                            this.hideColumn(column.key);
                        } else {
                            this.showColumn(column.key);
                        }
                    }
           	    }
                return true;
            };
            //
            dataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
                dataTable.validateColumnWidths(null);
                return oPayload;
            }
        },
        /**
         * oContainer  <HTMLElement> Container element for the TABLE
         * oData                      data
         */
        createStaticDataTable: function(oContainer, oData, oConfig, oHeight, oWidth) {
            //var elRegion = YUD.getRegion(oContainer);
            oConfig = oConfig || {};
            var liveData;
            var columnDefs;
            if (typeof oData === 'string') {
                liveData = YUD.get(oData);
                columnDefs = oConfig.columns;
            } else {
                liveData = oData.records || [];
                columnDefs = oData.columns;
            }
            var fields = YAHOO.jms.getFields(columnDefs);
            // dataSource
            var dataSource = new YAHOO.util.DataSource(liveData, {
                responseType: oConfig.responseType || YAHOO.util.XHRDataSource.TYPE_JSARRAY,
                responseSchema: {fields: fields}
            });
            // custom formatter(s)
            var hasLink = YAHOO.jms.updateColumnFormatters(columnDefs);
            var hasEditable = YAHOO.jms.updateColumnEditors(columnDefs);
            // dataTable
            var scrollable = false;
            if (oHeight && oWidth) {
                oConfig.height = oHeight;
                oConfig.width = oWidth;
                scrollable = true;
            } else if (oHeight) {
                oConfig.height = oHeight;
                //oConfig.width = (elRegion.right - elRegion.left - 2) + 'px';
                scrollable = true;
            } else if (oWidth) {
                oConfig.width = oWidth;
                scrollable = true;
            } else {
                oConfig.scrollable = !!oConfig.scrollable;
            }
            var dataTable;
            if (scrollable) {
            	if (YUD.get(oContainer + '.paginator')) {
            		var rowsPerPageOptions = oData.pagination.rowsPerPageOptions;
                	oConfig.paginator = new YAHOO.widget.Paginator({
                        containers: [oContainer + '.paginator'],
                        rowsPerPage: rowsPerPageOptions[0],
                        rowsPerPageOptions: rowsPerPageOptions,
                        template : '{FirstPageLink} {PreviousPageLink} <strong>{CurrentPageReport}</strong> {NextPageLink} {LastPageLink} {RowsPerPageDropdown}',
                        alwaysVisible: true
                    });
            	}
                dataTable = new YAHOO.widget.ScrollingDataTable(oContainer, columnDefs, dataSource, oConfig);
            } else {
                dataTable = new YAHOO.widget.DataTable(oContainer, columnDefs, dataSource, oConfig);
            }
            YAHOO.jms.subscribeDatatableEvents(dataTable, oConfig, hasEditable);
            //
            return dataTable;
        },
        /**
         * oContainer  <HTMLElement> Container element for the TABLE
         * oLiveData                  DataSource Pointer to live data
         * oColumnDefs  <Object[]>    Array of object literal Column definitions
         * oForm                      optional - can be null
         * oConfig                    optional - can be null,
         *                            eg var oConfig = {{initialLoad:true}, {initialRequest:'query=orders&results=10'}}
         * oHeight                    scroll height (optional)
         * oWidth                     scroll width (optional)
         */
        createDynamicDataTable: function(oContainer, oLiveData, oColumnDefs, oForm, oConfig, oHeight, oWidth) {
            var oFields = YAHOO.jms.getFields(oColumnDefs);
            var hasLink = YAHOO.jms.updateColumnFormatters(oColumnDefs);
            var hasEditable = YAHOO.jms.updateColumnEditors(oColumnDefs);
            // Create DataSource
            var dataSource = new YAHOO.util.XHRDataSource(oLiveData, {
                connXhrMode: 'queueRequests', // If a request is already in progress, wait until response is returned before sending the next request.
                connMethodPost: !!oForm, // POST
                maxCacheEntries: 0, // Set to 0 to turn off caching
                responseType: YAHOO.util.XHRDataSource.TYPE_JSON,
                responseSchema: {
                    resultsList: 'records',
                    fields: oFields,
                    metaFields: oConfig ? oConfig.metaFields : {columns:'columns'}
                }
            });
            var requestBuilder = function(oState, oDataTable) {
                if (oForm) {
                    YUC.setForm(oForm);
                    return '';
                }
                return '';
            };
            var elRegion = YUD.getRegion(oContainer);
            var dataTableConfig = {
                dynamicData: true
                , width: (elRegion.right - elRegion.left - 2) + 'px'
                , generateRequest: requestBuilder
                //, selectionMode: 'standard'
                //, selectionMode: 'single'
            };
            if (oConfig) {
                dataTableConfig.initialLoad = !!oConfig.initialLoad;
                if (oConfig.initialRequest) {
                    dataTableConfig.initialRequest = oConfig.initialRequest;
                }
                if (oConfig.formatRow) {
                    dataTableConfig.formatRow = oConfig.formatRow;
                }
                if (oConfig.cellSelect == true) {
                    dataTableConfig.selectionMode = 'cellrange'; // cellblock, singlecell
                }
            }
            var scrollable = false;
            if (oHeight && oWidth) {
                dataTableConfig.height = oHeight;
                dataTableConfig.width = oWidth;
                scrollable = true;
            } else if (oHeight) {
                dataTableConfig.height = oHeight;
                scrollable = true;
            } else if (oWidth) {
                dataTableConfig.width = oWidth;
                scrollable = true;
            } else {
                dataTableConfig.scrollable = false;
            }
            var dataTable;
            if (scrollable) {
                dataTable = new YAHOO.widget.ScrollingDataTable(oContainer, oColumnDefs, dataSource, dataTableConfig);
            } else {
                dataTable = new YAHOO.widget.DataTable(oContainer, oColumnDefs, dataSource, dataTableConfig);
            }
            YAHOO.jms.subscribeDatatableEvents(dataTable, oConfig, hasEditable);
            //
            return dataTable;
        },
        /**
         * @param oDataTable
         * @param insertRows (boolean) - This method will insert new rows at the beginning of the DataTable,
         *     preserving the existing data and regardless of any sort order.
         *     The insertion index for the added records can be assigned to when sending the request to the DataSource,
         *     or by accessing oPayload.insertIndex with the doBeforeLoadData() method at runtime.
         *     If applicable, creates or updates corresponding TR elements.
         * @param oRequest - Request object
         */
        filterDataTable: function(oDataTable, insertRows, oRequest) {
            if (!oDataTable) {
                YAHOO.jms.alert('YAHOO.jms.filterDataTable - no oDataTable defined.');
                return;
            }
            var oState = oDataTable.getState();
            //use onDataReturnSetRows because that method will clear out the old data in the DataTable, making way for the new data.
            var oCallback = {
                success: insertRows ? oDataTable.onDataReturnInsertRows : oDataTable.onDataReturnSetRows,
                failure: insertRows ? oDataTable.onDataReturnInsertRows : oDataTable.onDataReturnSetRows,
                argument: oState,
                scope: oDataTable
            };
            // Generate a query string
            if (!oRequest) {
                oRequest = oDataTable.get('generateRequest')(oState, oDataTable);
            }
            oDataTable.getDataSource().sendRequest(oRequest, oCallback);
        },
        /**
         * Client-side sort for dynamic table
         */
        clientSideSort: function(oDataTable) {
            oDataTable.unsubscribe('theadCellClickEvent');
            oDataTable.onEventSortColumn = function(oArgs) {
                var evt = oArgs.event;
                var target = oArgs.target;
                var el = this.getThEl(target) || this.getTdEl(target);
                if (el) {
                    var oColumn = this.getColumn(el);
                    if (oColumn.sortable) {
                        YUE.stopEvent(evt);
                        var dynamic = this.get('dynamicData');
                        if (dynamic) {
                            this.set('dynamicData', false);
                        }
                        this.sortColumn(oColumn);
                        if (dynamic) {
                            this.set('dynamicData', true);
                        }
                    }
                }
            };
            oDataTable._initColumnSort();
        },
        updateColumnFormatters: function(oColumnDefs) {
            if (!oColumnDefs) {
                return false;
            }
            var hasLink = false;
            // Update the Column Definitions (pass as string in find.jsp)
            for (var c = 0; c < oColumnDefs.length; c++) {
                var column = oColumnDefs[c];
                var link;
                if (column.formatter) {
                    link = YAHOO.jms.updateColumnFormatter(column);
                } else if (column.children) { // nested columns TODO: while
                    for (var c2 = 0; c2 < column.children.length; c2++) {
                        var columnNested = column.children[c2];
                        if (columnNested.formatter) {
                            link = YAHOO.jms.updateColumnFormatter(columnNested);
                        }
                    }
                }
                hasLink = hasLink || link;
            }
            return hasLink;
        },
        updateColumnFormatter: function(oColumn) {
            var hasLink = false;
            if (oColumn.formatter == 'YAHOO.widget.DataTable.formatLink' || oColumn.formatter == 'link') {
                oColumn.formatter = YAHOO.widget.DataTable.formatLink;
                hasLink = true;
            } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatHidden' || oColumn.formatter == 'hidden') {
                oColumn.formatter = YAHOO.widget.DataTable.formatHidden;
            } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatTextarea' || oColumn.formatter == 'textarea') {
                oColumn.formatter = YAHOO.widget.DataTable.formatTextarea;
            } else if (oColumn.formatter == 'YAHOO.widget.DataTable.formatCheckbox' || oColumn.formatter == 'checkbox') {
                oColumn.formatter = YAHOO.jms.formatCheckbox;
            } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatRadio' || oColumn.formatter == 'radio') {
                oColumn.formatter = YAHOO.widget.DataTable.formatRadio;
            } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatNumber' || oColumn.formatter == 'number') {
                oColumn.formatter = YAHOO.widget.DataTable.formatNumber;
            } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatDate' || oColumn.formatter == 'date') {
                oColumn.formatter = YAHOO.widget.DataTable.formatDate;
            } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatEmail' || oColumn.formatter == 'email') {
                oColumn.formatter = YAHOO.widget.DataTable.formatEmail;
            } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatClass' || oColumn.formatter == 'class') {
                oColumn.formatter = YAHOO.widget.DataTable.formatClass;
            }
            return hasLink;
        },
        formatCheckbox : function(el, oRecord, oColumn, oData) {
            if (oData !== true && oData !== false && oData !== 'true' && oData !== 'false') {
                return;
            }
            if (oData === 'true') {
                oData = true;
                oRecord.setData(oColumn.key, oData);
            } else if (oData === 'false') {
                oData = false;
                oRecord.setData(oColumn.key, oData);
            }
            var sChecked = (oData) ? " checked='checked'" : "";
            var disabled = oColumn.disabled;
            var sDisabled = disabled === false ? "" : " disabled='disabled'";
            var onchange = disabled === false ? " onchange='" + oColumn.onchange + "(this, " + this.getRecordIndex(oRecord) + (oColumn.dataTable ? ', ' + oColumn.dataTable : '') + ")'" : "";
            el.innerHTML = "<input type='checkbox' " + sDisabled + sChecked + onchange +
                " class='" + YAHOO.widget.DataTable.CLASS_CHECKBOX + "' />";
        },
        updateColumnEditors: function(oColumnDefs) {
            if (!oColumnDefs) {
                return false;
            }
            var hasEditable = false;
            // Update the Column Definitions (pass as string in find.jsp)
            for (var c = 0; c < oColumnDefs.length; c++) {
                var column = oColumnDefs[c];
                if (column.editor) {
                    YAHOO.jms.updateColumnEditor(column);
                    hasEditable = true;
                } else if (column.children) { // nested columns TODO: while
                    for (var c2 = 0; c2 < column.children.length; c2++) {
                        var columnNested = column.children[c2];
                        if (columnNested.editor) {
                            YAHOO.jms.updateColumnEditor(columnNested);
                            hasEditable = true;
                        }
                    }
                }
            }
            return hasEditable;
        },
        updateColumnEditor: function(oColumn) {
            var disableBtns = !!oColumn.disableBtns;
            if (oColumn.editor == 'checkbox' || oColumn.editor == 'YAHOO.widget.CheckboxCellEditor') {
                oColumn.editor = new YAHOO.widget.CheckboxCellEditor({checkboxOptions:[''],disableBtns:true});
            } else if (oColumn.editor == 'dropdown' || oColumn.editor == 'YAHOO.widget.DropdownCellEditor') {
                oColumn.editor = new YAHOO.widget.DropdownCellEditor({dropdownOptions:oColumn.dropdownOptions,disableBtns:disableBtns});
            } else if (oColumn.editor == 'YAHOO.widget.TextboxCellEditor') {
                oColumn.editor = new YAHOO.widget.TextboxCellEditor({disableBtns:disableBtns});
            } else if (oColumn.editor == 'YAHOO.widget.TextareaCellEditor') {
                oColumn.editor = new YAHOO.widget.TextareaCellEditor({disableBtns:disableBtns});
            } else if (oColumn.editor == 'YAHOO.widget.DateCellEditor') {
                oColumn.editor = new YAHOO.widget.DateCellEditor({disableBtns:disableBtns});
            }
            oColumn.editor.key = oColumn.key;
        },
        /**
         * oColumnDefs  <Object[]>    Array of object literal Column definitions
         */
        getFields: function(oColumnDefs) {
            var fields = [];
            for (var i = 0; oColumnDefs && i < oColumnDefs.length; i++) {
                var columnDef = oColumnDefs[i];
                if (typeof columnDef.parser === 'string') {
                    fields.push({key:columnDef.key, parser:columnDef.parser});
                } else {
                    fields.push(columnDef.key);
                }
            }
            return fields;
        },
        getSelectedRecord: function(oDataTable) {
            var selectedRows = oDataTable.getSelectedRows();
            var r = selectedRows.length == 0 ? null : oDataTable.getRecordSet().getRecord(selectedRows[0]);
            if (r) {
            	r._dataTable = oDataTable; // for internal use
            }
            return r;
        },
        getSelectedRow: function(oDataTable) {
            var r = YAHOO.jms.getSelectedRecord(oDataTable);
            return !r || !r.getData() ? null : r.getData();
        },
        getSelectedRowId: function(oDataTable) {
            var row = YAHOO.jms.getSelectedRow(oDataTable);
            return row ? row.id : null;
        },
        /**
         * Delete selected row
         */
        deleteSelectedRows: function(oDataTable) {
            if (!oDataTable) {
                oDataTable = dataTable;
            }
            var selectedRows = oDataTable.getSelectedRows();
            for (var i = selectedRows.length - 1; i >= 0; i--) {
                oDataTable.deleteRow(selectedRows[i]);
            }
        },
        initDataTable: function(data, h, w) {
        	var containerId = 'dataTable';
            if (dataToolbar) {
                //YAHOO.log('Destroying the dataToolbar', 'info', 'data.js');
                dataToolbar.destroy();
                dataToolbar = null;
                YAHOO.jms.dataToolbarHeight = 0;
                var d = YUD.get('dataToolbarDiv');
                if (d) d.innerHTML = '';
            }
            if (dataTable) {
                //YAHOO.log('Destroying the dataTable', 'info', 'data.js');
                dataTable.destroy(); dataTable = null;
                var d = YUD.get(containerId); if (d) d.innerHTML = '';
            }
            if (data && data.columnDefs && data.columnDefs.length > 0) {
                //YAHOO.log('Creating the DataTable', 'info', 'data.js');
                // Update the Column Definitions (pass as string in find.jsp)
                var columnDefs = data.columnDefs;
                var hasLink = YAHOO.jms.updateColumnFormatters(columnDefs);
                var hasEditable = YAHOO.jms.updateColumnEditors(columnDefs);

                // Create the datasource
                var fields = YAHOO.jms.getFields(columnDefs);
                var dataSource = new YAHOO.util.DataSource(data);
                dataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
                dataSource.responseSchema = {
                    resultsList: 'rows',
                    fields: fields
                };

                //Create the DT, setting scrollable to true and setting the height
                YAHOO.widget.DataTable.MSG_EMPTY = 'This folder contains no data';

                // DataTable configuration
        		var pagination = data.pagination;
                if (!h) {
                    var t = YUD.getRegion('top2').height;
                    var b = YAHOO.jms.data.actions.viewAction ? 27 : 0; // minimum height of 'bottom2'
                    var p = pagination ? 33 : 0;
                    h = YUD.getRegion(YAHOO.jms.tabView._contentParent).height - t - b - p - 70; // FIXME: where is this offset comming from?
                    if (data.actions.viewAction || data.actions.viewFunction) {
                        h -= 150;
                    }
                }
                if (!w) {
                    w = YUD.getRegion(YAHOO.jms.tabView._contentParent).width;
                }
                var columnCount = columnDefs.length;
                for (var c = 0; c < columnCount; c++) {
                    if (!columnDefs[c].width) {
                        columnDefs[c].width = w / columnCount - 3;
                    }
                }

                var configs = {
                    //When dynamicData is enabled, sorting or paginating will trigger a DataSource request for new data to reflect the state. By default, the request is formatted with the following syntax:
                    //sort={SortColumnKey}&dir={SortColumnDir}&startIndex={PaginationStartIndex}&results={PaginationRowsPerPage}
                    //dynamicData: true,
                    //sortedBy : {key:data.sortedBy.key, dir:data.sortedBy.dir}, // Sets UI initial sort arrow
                    //initialLoad: false,
                    scrollable: true,
                    //draggableColumns: true,
                    width: w + 'px',
                    height: h + 'px'
                    //paginator: paginator,
                    // This configuration item is what builds the query string passed to the DataSource.
                    //generateRequest: YAHOO.jms.table.requestBuilder,
                    // Enable the row formatter
                    //formatRow: rowFormatter
                };
            	if (pagination) {
            		configs.paginator = new YAHOO.widget.Paginator({
                        containers: [containerId + '.paginator'],
                        rowsPerPage: pagination.rowsPerPage,
                        rowsPerPageOptions: pagination.rowsPerPageOptions,
                        template : '{FirstPageLink} {PreviousPageLink} <strong>{CurrentPageReport}</strong> {NextPageLink} {LastPageLink} {RowsPerPageDropdown}'
                    });
            	}

                // Create table, TODO: use YAHOO.jms.createStaticDataTable(containerId, oData);
                //dataTable = new YAHOO.widget.DataTable(containerId, columnDefs, dataSource, configs);
                dataTable = new YAHOO.widget.ScrollingDataTable(containerId, columnDefs, dataSource, configs);

                // Subscribe to events for row selection
                if (data.actions.cellSelection) {
                    // Subscribe to events for cell selection
                    dataTable.subscribe('cellMouseoverEvent', dataTable.onEventHighlightCell);
                    dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
                    dataTable.subscribe('cellClickEvent', dataTable.onEventSelectCell);
                    dataTable.subscribe('cellSelectEvent', dataTable.clearTextSelection);
                } else {
                    // Subscribe to events for row selection
                    dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
                    dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
                    dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
                    if (data.actions.viewAction) {
                        dataTable.subscribe('rowSelectEvent', function() {
                            var row = YAHOO.jms.getSelectedRow(this);
                            YAHOO.jms.view(row, data.actions.viewAction);
                        }, dataTable, true);
                    }
                    if (data.actions.editAction) {
                        dataTable.subscribe('rowDblclickEvent', function() {
                            var record = YAHOO.jms.getSelectedRecord(this);
                            YAHOO.jms.edit(record, data.actions.editAction, data.actions.editScript, data.actions.editTitle, data.actions.editWidth, data.actions.editHeight);
                        }, dataTable, true);
                    }
                }
                if (hasEditable) {
                    dataTable.subscribe('cellClickEvent', dataTable.onEventShowCellEditor);
                    if (data.subscribe && data.subscribe.length != 0) {
                        for (var i = 0; i < data.subscribe.length; i++) {
                            var s = data.subscribe[i];
                            var handler = YAHOO.jms.getFunction(s.namespace, s.handler);
                            dataTable.getColumn(s.column).editor.subscribe(s.event, handler);
                        }
                    }
                }
                dataTable.subscribe('checkboxClickEvent', function(oArgs) {
                    var elCheckbox = oArgs.target;
                    var oRecord = this.getRecord(elCheckbox);
                    oRecord.setData('selected', elCheckbox.checked); // hardcoded to 'selected' column name
                    if (data.actions.viewFunction) {
                        eval(data.actions.viewFunction)(oRecord);
                    }
                });

                var viewEl = YUD.get('bottom2');
                if (viewEl) {
                    YUD.setStyle(viewEl, 'display', data.actions.viewAction || data.actions.viewFunction ? 'block' : 'none');
                }
                var previewEl = YUD.get('preview');
                if (previewEl) {
                    previewEl.innerHTML = '';
                }
                if (data.actions.viewFunction) {
                    eval(data.actions.viewFunction)(null);
                }

//=======================================================================
// toolbar.js
//=======================================================================
                var hideDataToolbar = true;
                dataToolbar = new YAHOO.widget.Toolbar('dataToolbarDiv', {buttons: []});
                if (data.actions.findAction) {
                    var label = data.actions.findTitle ? data.actions.findTitle : 'Search';
                    dataToolbar.addButton({id: 'tb_filter', type: 'push', label: label, value: 'filter'});
                    dataToolbar.addSeparator();
                    hideDataToolbar = false;
                }
                if (data.actions.newAction) {
                    if (typeof data.actions.newAction === 'string') {
                        var label = data.actions.newTitle ? data.actions.newTitle : 'New';
                        dataToolbar.addButton({id: 'tb_new', type: 'push', label: label, value: 'new'});
                        dataToolbar.addSeparator();
                        hideDataToolbar = false;
                    } else {
                        for (var i = 0; i < data.actions.newAction.length; i++) {
                            var label = data.actions.newTitle[i];
                            dataToolbar.addButton({id: 'tb_new' + i, type: 'push', label: label, value: 'new' + i});
                            dataToolbar.addSeparator();
                            hideDataToolbar = false;
                        }
                    }
                }
                if (data.actions.editAction) {
                    var label = data.actions.editTitle ? data.actions.editTitle : 'Edit';
                    dataToolbar.addButton({id: 'tb_edit',   type: 'push', label: label,    value: 'edit'});
                    dataToolbar.addSeparator();
                    hideDataToolbar = false;
                }
                if (data.actions.deleteAction) {
                    var label = data.actions.deleteTitle ? data.actions.deleteTitle : 'Delete';
                    dataToolbar.addButton({id: 'tb_delete', type: 'push', label: label, value: 'delete'});
                    dataToolbar.addSeparator();
                    hideDataToolbar = false;
                }
                if (data.actions.printAction) {
                    var label = data.actions.printTitle ? data.actions.printTitle : 'Print';
                    dataToolbar.addButton({id: 'tb_print',  type: 'push', label: label,  value: 'print'});
                    dataToolbar.addSeparator();
                    hideDataToolbar = false;
                }
                if (data.actions.exportAction) {
                    var label = data.actions.exportTitle ? data.actions.exportTitle : 'Export';
                    dataToolbar.addButton({id: 'tb_export', type: 'push', label: label, value: 'export'});
                    dataToolbar.addSeparator();
                    hideDataToolbar = false;
                }
                if (data.actions.importAction) {
                    if (typeof data.actions.importAction === 'string') {
                        var label = data.actions.importTitle ? data.actions.importTitle : 'Import';
                        dataToolbar.addButton({id: 'tb_import', type: 'push', label: label, value: 'import'});
                        dataToolbar.addSeparator();
                        hideDataToolbar = false;
                    } else {
                        // TODO: wip
                        var menu = [];
                        for (var i = 0; i < data.actions.importAction.length; i++) {
                            menu.push({text: data.actions.importTitle[i]});
                        }
                        var btn = new YAHOO.widget.Button({type: 'menu', label: 'Isolation History',
                            menu: menu, container: dataToolbar
                        });
                        dataToolbar.addButton(btn);
                        dataToolbar.addSeparator();
                        hideDataToolbar = false;
                    }
                }
                if (data.actions.customAction || data.actions.customFunction) {
                    var label = data.actions.customTitle ? data.actions.customTitle : '???';
                    dataToolbar.addButton({id: 'tb_custom', type: 'push', label: label, value: 'custom'});
                    dataToolbar.addSeparator();
                    hideDataToolbar = false;
                }
                if (data.actions.helpAction) {
                    var label = data.actions.helpTitle ? data.actions.helpTitle : 'Help';
                    dataToolbar.addButton({id: 'tb_help', type: 'push', label: label, value: 'help'});
                    dataToolbar.addSeparator();
                    hideDataToolbar = false;
                }
                // toggle button
                if (!true) {
                    dataToolbar.addButton({id: 'tb_toggle', type: 'push', className: 'float-right', label: 'Toggle', value: 'toggle'});
                    //dataToolbar.addSeparator();
                }
                //
                if (hideDataToolbar) {
                    YUD.addClass(YUD.get('dataToolbarDiv'), 'hidden');
                }
                //Show an alert message with the button they clicked
                dataToolbar.on('buttonClick', function(ev) {
                    //YAHOO.log('ev.button.id=' + ev.button.id, 'info', 'data.js');
                    if (ev.button.id == 'tb_filter') {
                        YAHOO.jms.processFilter(data.actions.findAction, data.actions.findScript);
                    } else if (ev.button.id == 'tb_new') {
                        YAHOO.jms.edit(null, data.actions.newAction, data.actions.newScript, data.actions.newTitle, data.actions.newWidth, data.actions.newHeight);
                    } else if (ev.button.id.indexOf('tb_new') == 0) {
                        var i = parseInt(ev.button.id.substring(6));
                        YAHOO.jms.edit(null, data.actions.newAction[i], data.actions.newScript, data.actions.newTitle[i], data.actions.newWidth, data.actions.newHeight);
                    } else if (ev.button.id == 'tb_edit') {
                        var query = YAHOO.jms.getFilterQuery(true);
                        if (data.actions.cellSelection) {
                            var id = ''; // all cells
                            var cell = dataTable.getSelectedCells()[0];
                            if (cell) {
                                id = dataTable.getRecordSet().getRecord(cell.recordId).getData(cell.columnKey + 'day');
                            }
                            YAHOO.jms.edit(null, data.actions.editAction + id + '&' + query, data.actions.editScript, data.actions.editTitle, data.actions.editWidth, data.actions.editHeight);
                        } else {
                            var record = YAHOO.jms.getSelectedRecord(dataTable);
                            if (record) {
                                YAHOO.jms.edit(record, data.actions.editAction + '&' + query, data.actions.editScript, data.actions.editTitle, data.actions.editWidth, data.actions.editHeight);
                            }
                        }
                    } else if (ev.button.id == 'tb_help') {
                        YAHOO.jms.help(data.actions.helpAction, data.actions.helpTitle);
                    } else if (ev.button.id == 'tb_print') {
                        var id = ''; // all cells
                        if (data.actions.cellSelection) {
                            var cell = dataTable.getSelectedCells()[0];
                            if (cell) {
                                id = dataTable.getRecordSet().getRecord(cell.recordId).getData(cell.columnKey + 'day');
                            }
                        } else {
                            var row = YAHOO.jms.getSelectedRow(dataTable);
                            if (row) {
                                id = row.id;
                            }
                        }
                        //YAHOO.jms.alert(ev.button.label + ': ' + id);
                        var printAction = data.actions.printAction;
                        var printQuery = (printAction.indexOf('?') < 0 ? '?' : '&') + 'id=' + id;
                        YAHOO.jms.sendDownloadRequest(printAction + printQuery);
                    } else if (ev.button.id == 'tb_export') {
                        var exportAction = data.actions.exportAction;
                        var exportQuery = YAHOO.jms.getFilterQuery();
                        exportQuery = (exportAction.indexOf('?') < 0 ? '?' : '&') + exportQuery;
                        YAHOO.jms.sendDownloadRequest(exportAction + exportQuery);
                    } else if (ev.button.id == 'tb_import') {
                        YAHOO.jms.edit(null, data.actions.importAction, data.actions.importScript, data.actions.importTitle, data.actions.importWidth, data.actions.importHeight);
                    } else if (ev.button.id == 'tb_custom') {
                        if (data.actions.customAction) {
                            //YAHOO.jms.edit(null, data.actions.customAction, data.actions.customScript, data.actions.customTitle, data.actions.customWidth, data.actions.customHeight);
                            //YUC.asyncRequest('GET', data.actions.customAction, YAHOO.jms.filterActionCallback);
                            YAHOO.jms.processFilter(data.actions.customAction, data.actions.findScript);
                        } else if (data.actions.customFunction) {
                            if (data.actions.customFunction.indexOf('mailOut.') == 0) {
                                YAHOO.jms.execute(YAHOO.jms.mailOut, data.actions.customFunction.substring('mailOut.'.length));
                            } else {
                                YAHOO.jms.execute(YAHOO.jms, data.actions.customFunction);
                            }
                        }
                    } else if (ev.button.id == 'tb_delete') {
                        var id = YAHOO.jms.getSelectedRowId(dataTable);
                        if (id > 0) {
                            YAHOO.jms.deleteRow(data.actions.deleteAction + '&id=' + id, data.actions.deleteTitle);
                        }
                    } else if (ev.button.id == 'tb_toggle') {
                        YAHOO.jms.toggleClass('filtersDiv', 'hidden');
                    }
                });
                //Grab it's height for later use
                YAHOO.jms.dataToolbarHeight = YUD.get('dataToolbarDiv').clientHeight + 3;
                //YAHOO.log('dataToolbarHeight=' + YAHOO.jms.dataToolbarHeight, 'info', 'data.js');
            }
        },
        /**
         * Method to Resize the tabview
         */
        resizeWindow: function() {
            YAHOO.jms.resizeTabView();
            //YUD.setStyle('doc1', 'height', YUD.getClientHeight() + 'px');
        },
        /**
         * process CKeditor 4.1 input
         * http://docs.fckeditor.net/CKEditor_3.x/Developers_Guide/Integration
         */
        updateEditorInput: function() {
            var editorInputEl = YUD.get('htmlInput');
            if (editorInputEl && YAHOO.jms.htmlInput) {
                editorInputEl.value = YAHOO.jms.htmlInput.getData();
            }
        },
        ckeditor: function(root) {
            // If we already have an editor, let's destroy it first.
            if (YAHOO.jms.htmlInput) {
                //YAHOO.jms.htmlInput.destroy(true);
                //YAHOO.jms.htmlInput = null;
            }
            YUE.onContentReady('htmlInput', function() {
                YAHOO.util.Get.script('ckeditor/ckeditor.js', {
                    onSuccess: function() {
                        YAHOO.jms.htmlInput = CKEDITOR.replace('htmlInput', {
                            toolbar : [
                                  ['Source','-'/*,'Save','NewPage'*/,'Preview','Print','-','Templates']
                                 ,['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo']
                                 ,['Find','Replace','-','SelectAll','RemoveFormat','SpellChecker','Scayt']
                                  ,'/'
                                 //,['Form','Checkbox','Radio','TextField','Textarea','Select','Button','ImageButton','HiddenField']
                                 ,['Bold','Italic','Underline','Strike','-','Subscript','Superscript']
                                 ,['NumberedList','BulletedList','-','Outdent','Indent','Blockquote']
                                 ,['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock']
                                 //,['Link','Unlink','Anchor']
                                 ,['Image',/*'Flash',*/'Table','HorizontalRule','Smiley','SpecialChar','PageBreak']
                                 ,'/'
                                 ,['Styles','Format','Font','FontSize']
                                 ,['TextColor','BGColor']
                                 ,[/*'Maximize', */'ShowBlocks','-','About']
                            ]
                        });
                    }
                });
            });
        },
        /**
         * Will allow to see full file path (usually disabled due to browser security settings)
         * http://rattomago.wordpress.com/2009/02/18/accessing-filepath-in-html-input-element-via-javascript/
         */
        onchangeUploadFile: function(oUploadFileEl) {
            if (YAHOO.env.ua.ie > 0) {
                // http://msdn.microsoft.com/en-us/library/ms535128%28VS.85%29.aspx
                //if (oUploadFileEl.value.indexOf('\\') == -1) {
                //    YUD.get('errors').innerHTML = 'The fully qualified filename of the selected file is returned only when the value of the<br/>'Include local directory path when uploading files to a server'<br/>security setting for the security zone is enabled.';
                //}
                return oUploadFileEl.value;
            }
            if (YAHOO.env.ua.gecko > 0) {
                // https://bug143220.bugzilla.mozilla.org/attachment.cgi?id=328849
                try {
                    netscape.security.PrivilegeManager.enablePrivilege('UniversalFileRead');
                    return oUploadFileEl.value;
                } catch(e) {
                    // Set the body to the string passed - to check for validation error(s)
                    YUD.get('errors').innerHTML = "<b>Unable to access local files due to browser security settings.<br/>To overcome this, follow these steps:</b><br/>(1) Enter 'about:config' in the URL field;<br/>(2) Right click and select New->Boolean;<br/>(3) Enter 'signed.applets.codebase_principal_support' (without the quotes) as a new preference name;<br/>(4) Click OK and try loading the file again.";
                    return null;
                };
            } else {
                //YUD.get('errors').innerHTML = 'Not IE or Firefox (userAgent=' + navigator.userAgent + ')';
                return oUploadFileEl.value;
            }
        },
        /**
         *
         */
        editFile: function(fileId) {
            YAHOO.jms.data.actions.editDetailsAction = 'file/editFile.do?dispatch=edit&view=detailView&id=' + fileId;
            YAHOO.jms.data.actions.editDetailsScript = 'scripts/module/file/edit.js';
            this.sendGetRequest(YAHOO.jms.data.actions.editDetailsAction, YAHOO.jms.data.actions.editDetailsScript);
        },
        /**
         * Instantiate the Dialog
         */
        edit: function(oRow, oAction, oScript, oTitle, w, h, args) {
            if (!oAction) {
                return;
            }
            var record = null;
            if (oRow instanceof YAHOO.widget.Record) {
                record = oRow;
                oRow = record._oData;
            }
            YAHOO.jms.currentRow = oRow ? oRow : null;
            if (oAction) {
                YAHOO.jms.currentAction = oAction;
            }
            //to avoid multiple script loading
            //if (oScript) {
                YAHOO.jms.currentScript = oScript;
            //}
            //oAction always in format 'action?dispatch=method'
            var query = '';
            if (YAHOO.jms.currentAction.indexOf('&id=') < 0) {
                if (YAHOO.jms.currentRow && YAHOO.jms.currentRow.id) {
                    query += '&id=' + YAHOO.jms.currentRow.id;
                } else if (YUD.get('fileId')) {
                    query += '&id=' + YUD.get('fileId').value;
                }
            }
            if (record) {
                query += YAHOO.jms.getSelectedRecordQuery(record._dataTable);
            }
            // set the header to the string passed
            var title = oTitle ? oTitle : (YAHOO.jms.currentRow ? 'Edit' : 'Add New');
            YAHOO.jms.editPanel.setHeader(title);
            // set size
            YAHOO.jms._setSize(YAHOO.jms.editPanel, w, h);
            // set callback argument (if any)
            YAHOO.jms.editActionCallback.argument = args;
            YUC.asyncRequest('GET', YAHOO.jms.currentAction + query, YAHOO.jms.editActionCallback);
        },
        _setSize: function(panel, w, h) {
            if (panel) {
                //var elRegion = YUD.getRegion(YAHOO.jms.editPanel.element);
                //w = elRegion.width - 2;
                //h = elRegion.height - 2;
                if (isNaN(w) || w == 0) {
                    w = YUD.getViewportWidth() * 0.50;
                } else if (w > 0 && w <= 1) {
                    w = YUD.getViewportWidth() * w; // percentage
                }
                panel.cfg.setProperty('width', w + 'px');
                if (isNaN(h) || h == 0) {
                    h = YUD.getViewportHeight() * 0.80;
                } else if (h > 0 && h <= 1) {
                    h = YUD.getViewportHeight() * h; // percentage
                }
                panel.cfg.setProperty('height', h + 'px');
            }
        },
        _initAfterCallback: function(root) {
            // set the header to the string passed
            var titleEl = YUD.get('title');
            if (titleEl && titleEl.value) {
                YAHOO.jms.editPanel.setHeader(titleEl.value);
            }
            var itemSelectEvent = null;
            var itemSelectEvent4supplier = null;
            var reloadOnChangeUrl = YUD.get('reloadOnChangeUrl');
            if (reloadOnChangeUrl && reloadOnChangeUrl.value) {
                var form = YAHOO.jms.editPanel.form;
                var action = form.action;
                var reloadPart = reloadOnChangeUrl.value;
                itemSelectEvent = function(sType, aArgs) {
                    YAHOO.jms.sendPostRequest(form, action + '?' + reloadPart, 'scripts/common.js');
                };
                itemSelectEvent4supplier = function(sType, aArgs) {
                    // object literal of selected item's result data
                    var oData = aArgs[2];
                    var supplierId = oData.value;
                    if (supplierId == 0) {
                        // create new supplier
                        var args = {postSubmitCallback: {
                            cache: false,
                            success: function(oResponse) {
                            	var data = YAHOO.jms.parseJsonData(oResponse.responseText);
                            	var q = '&entity.supplier.supplierId=' + data.supplierId;
                                YAHOO.jms.sendPostRequest(form, action + '?' + reloadPart + q, 'scripts/common.js', 'Edit');
                            },
                            failure: function(oResponse) {
                                YAHOO.jms.failure(oResponse);
                            }
                        }};
                        var panel = YAHOO.jms.edit(null, 'supplier/editSupplier.do?dispatch=edit', 'scripts/common.js', 'Add Supplier', null, null, args);
//                    } else if (supplierId > 0) {
//                        // create new supplier
//                        var args = {postSubmitCallback: {
//                            cache: false,
//                            success: function(oResponse) {
//                                YAHOO.jms.sendPostRequest(form, action + '?' + reloadPart, 'scripts/common.js');
//                            },
//                            failure: function(oResponse) {
//                                YAHOO.jms.failure(oResponse);
//                            }
//                        }};
//                        var panel = YAHOO.jms.edit(null, 'supplier/editSupplier.do?dispatch=edit&id=' + supplierId, 'scripts/common.js', 'Edit Supplier', null, null, args);
                    } else {
                        YAHOO.jms.sendPostRequest(form, action + '?' + reloadPart, 'scripts/common.js');
                    }
                };
            }
            var rootPath;
            if (root) {
                rootPath = root.tagName.toLowerCase() + '[id='+ root.id +'] ';
            } else if (YAHOO.jms.editPanel) {
                rootPath = YAHOO.jms.editPanel.body.tagName.toLowerCase() + '[id='+ YAHOO.jms.editPanel.id +'] ';
            } else {
                rootPath = '';
            }
            this.initAutoCompletes(YUS.query(rootPath + 'input:not([readonly])[type=text][id^=fileId.]'), '?dispatch=findFileNo&query=', itemSelectEvent);
            this.initAutoCompletes(YUS.query(rootPath + 'input:not([readonly])[type=text][id^=fcaId.]'), '?dispatch=findFcaNo&unassignedFca=false&query=', itemSelectEvent);
            this.initAutoCompletes(YUS.query(rootPath + 'input:not([readonly])[type=text][id^=sapCustNo.]'), '?dispatch=findSapCustNo&query=', itemSelectEvent);
            this.initAutoCompletes(YUS.query(rootPath + 'input:not([readonly])[type=td^=jobId.]'), '?dispatch=findJobNo&query=', itemSelectEvent);
            this.initAutoCompletes(YUS.query(rootPath + 'input:not([readonly])[type=text][id^=userId.]'), '?dispatch=userName&query=', null);
            this.initAutoCompletes(YUS.query(rootPath + 'input:not([readonly])[type=text][id^=supplierId.]'), '?dispatch=findSupplierName&query=', itemSelectEvent4supplier);
            this.initAutoCompletes(YUS.query(rootPath + 'input:not([readonly])[type=text][id^=archive.]'), '?dispatch=findArchive&query=', itemSelectEvent);
            this.initCalendarInputs();
            YAHOO.jms.ckeditor(root);
            // hide/show submit button
            var submitBtn = YAHOO.jms.editPanel._aButtons[0];
            var noSubmit = YAHOO.jms.editPanel.form.method == 'get';
            //submitBtn.set('disabled', noSubmit);
            if (noSubmit) {
                submitBtn.addClass('hidden');
            } else {
                submitBtn.removeClass('hidden');
            }
        },
        editActionCallback: {
            cache: false,
            upload: function(o) {
                var responseText = o.responseText; // should be any error or empty
                // TODO: how to define that it is fail response?
                YAHOO.jms.editPanel.setBody(responseText);
                this._init(o);
            },
            success: function(o) {
                var responseText = o.responseText;
                if (!responseText) {
                    // empty response - TODO: success message
                } else if (YAHOO.jms.currentAction.indexOf('view=detailView') > 0) {
                	var detailDivEl = YUD.get('detailDiv');
                	detailDivEl.innerHTML = responseText;
                    YAHOO.jms.loadScripts(detailDivEl);
                    YAHOO.jms.loadScript(YAHOO.jms.currentScript);
                    YAHOO.jms.tabView.set('activeTab', YAHOO.jms.detailTab);
                    // update body style
                    var bodyEl = YUD.get('bd');
                    var h = YUD.getRegion('hd').height;
                    YUD.setStyle(bodyEl, 'height', (YUD.getRegion(YAHOO.jms.tabView._contentParent).height - h) + 'px');
                    YUD.setStyle(bodyEl, 'overflow-x', 'hidden');
                    YUD.setStyle(bodyEl, 'overflow-y', 'auto');
                } else {
                    YAHOO.jms.editPanel.setBody(responseText);
                    this._init(o);
                }
            },
            failure: function(o) {
                var responseText = o.responseText;
                //YUD.get('errors').innerHTML = responseText;
                alert('Failure: ' + o.status + ' ' + responseText);
                YAHOO.jms._initAfterCallback();
            },
            _init: function(o) {
                var panel = YAHOO.jms.editPanel;
                var root = panel.body;
                YAHOO.jms._initAfterCallback();
                //YAHOO.jms.ckeditor(root);
                YAHOO.jms.loadScript(YAHOO.jms.currentScript);
                panel.render(document.body);
                // check inline script
                var scriptNodes = YUS.query('div[id=editDiv] script');
                for (var i = 0; i < scriptNodes.length; i++) {
                    var script = YL.trim(scriptNodes[i].innerHTML);
                    if (script != '&#160;') {
                        eval(script);
                    }
                }
                panel.bringToTop();
                panel.show();
            }
        },
        submitCallback: {
            cache: false,
            upload: function(o) {
                var response = o.responseText; // should be any error or empty
                var jsonData = YAHOO.jms.parseJsonData(response, true);
                //YAHOO.log('handleMultipart response: [' + o.status + '],[' + response + ']', 'info', 'edit.js');
                // TODO: how to define that it is fail response?
                // TODO: IE treat validation error response as 'undefined'
                // TODO: why??? <pre style="word-wrap: break-word; white-space: pre-wrap;"></pre>
                if (!response || jsonData) {
                	this._success(o);
                }
                else if (response && response.length > 0
                    && response.indexOf('<pre ') != 0) {
                    // Set the body to the string passed - to check for validation error(s)
                    //YAHOO.log('handleMultipart failed - [' + response + ']', 'info', 'edit.js');
                    this.failure(o);
                }
            },
            success: function(o) {
                var response = o.responseText;
                var jsonData = YAHOO.jms.parseJsonData(response, true);
                if (!response || jsonData) {
                	this._success(o);
                }
                else if (response && response.length > 0) {
                    // Set the body to the string passed - following workflow action
                    YAHOO.jms.editPanel.setBody(response);
                    this._init();
                }
            },
            failure: function(o) {
                var response = o.responseText;
                if (o.status == 400) { //SC_BAD_REQUEST=400
                    // Set the body to the string passed - validation error(s)
                    YAHOO.jms.editPanel.setBody(response);
                } else if (o.status == 406) { //SC_NOT_ACCEPTABLE = 406;
                    YUD.get('errors').innerHTML = response;
                } else {
                    YUD.get('errors').innerHTML = response;
                }
                this._init();
            },
            _init: function() {
                YAHOO.jms._initAfterCallback();
                YAHOO.jms.loadScript(YAHOO.jms.currentScript);
            },
            _success: function(o) {
                YAHOO.jms.editPanel.hide();
                // success
                var argument = YAHOO.jms.editActionCallback.argument;
                var postSubmitCallback = argument ? argument.postSubmitCallback : null;
                if (postSubmitCallback) {
                	postSubmitCallback.success(o);
                } else if (YAHOO.jms.tabView.get('activeTab') == YAHOO.jms.detailTab) {
                    // refresh detail data
                    var row = YAHOO.jms.currentRow || YAHOO.jms.getSelectedRow(dataTable);
                    var editAction = YAHOO.jms.data.actions.editAction;
                    var editScript = YAHOO.jms.data.actions.editScript;
                    if (YAHOO.jms.data.actions.editDetailsAction) {
                        editAction = YAHOO.jms.data.actions.editDetailsAction;
                        editScript = YAHOO.jms.data.actions.editDetailsScript;
                    }
                    YAHOO.jms.edit(row, editAction, editScript);
                } else if (YAHOO.jms.data.actions) {
                    // refresh table data
                    var findAction = YAHOO.jms.data.actions.findAction;
                    var findScript = YAHOO.jms.data.actions.findScript;
                    YAHOO.jms.processFilter(findAction, findScript);
                }
            }
        },
        /**
         *
         * @param oRow
         * @param oAction
         */
        view: function(oRow, oAction, oCallback) {
            if (!oRow || !oAction) {
                return;
            }
            //YUC.setForm(this.form);
            //viewAction always in format 'action?dispatch=method'
            var query = '';
            if (oRow && oRow.id) {
                query += '&id=' + oRow.id;
            }
            query += YAHOO.jms.getSelectedRecordQuery(dataTable);
            if (!oCallback) {
                oCallback = YAHOO.jms.viewActionCallback;
            }
            YUC.asyncRequest('GET', oAction + query, oCallback);
        },
        viewActionCallback: {
            cache: false,
            success: function(o) {
                var response = o.responseText;
                var previewEl = YUD.get('preview');
                previewEl.innerHTML = response;
                YAHOO.jms.toggleContainers(previewEl);
            },
            failure: function(o) {
                var response = o.responseText;
                //YUD.get('errors').innerHTML = response;
                if (o.status > 0) {
                    alert('viewActionCallback failed: ' + o.status + ' ' + response);
                }
            }
        },
        /**
         * Instantiate the Delete Dialog
         */
        deleteRow: function(oAction, oTitle) {
            if (confirm(oTitle)) {
                YUC.asyncRequest('GET', oAction, {
                    cache: false,
                    success: function(oResponse) {
                        YAHOO.jms.search(); // reload
                    },
                    failure: function(oResponse) {
                        YAHOO.jms.emptyCallback.failure(oResponse);
                    }
                });
            }
        },
        /**
         * Instantiate the Help Dialog
         */
        help: function(oAction, oTitle) {
            if (oTitle) {
                YAHOO.jms.helpPanel.setHeader(oTitle);
            }
            YUC.asyncRequest('GET', oAction, {
                cache: false,
                success: function(o) {
                    YAHOO.jms.helpPanel.setBody(o.responseText);
                    YAHOO.jms.helpPanel.render(document.body);
                    YAHOO.jms.helpPanel.bringToTop();
                    YAHOO.jms.helpPanel.show();
                },
                failure: function(o) {
                    var response = o.responseText;
                    //YUD.get('errors').innerHTML = response;
                    alert('Failure: ' + o.status + ' ' + response);
                }
            });
        },
        getMenus: function() {
            return YUS.query('#mainMenu li a');
        },
        getFirstMenu: function() {
            var menus = YAHOO.jms.getMenus();
            return menus[0];
        },
        /**
         * Calendar
         */
        aseChangeCal: function() {
            YAHOO.jms._initCal('file/calendarAseChange.do?dispatch=find', 'ASE Change Calendar');
        },
        jobActionCal: function() {
            YAHOO.jms._initCal('job/calendarJobAction.do?dispatch=find', 'Job Action Calendar');
        },
        staffCal: function() {
            YAHOO.jms._initCal('user/calendarStaff.do?dispatch=find&userId=', 'Staff Calendar');
        },
        _initCal: function(baseCalendarUrl, baseCalendarTitle) {
            YUD.removeClass(YUS.query('#mainMenu li'), 'selected');
            YAHOO.jms.currentURL = baseCalendarUrl + '&year=' + calendarYear + '&month=' + calendarMonth;
            var tab = YAHOO.jms.dataTab;
            tab.set('label', baseCalendarTitle + ' ' + months[calendarMonth] + '-' + calendarYear);
            YAHOO.jms.navigate(YAHOO.jms.currentURL, baseCalendarTitle); // via browser history manager
            YAHOO.jms.tabView.set('activeTab', tab);
        },
        /**
         * File
         */
        auditFile: function(fileId) {
            if (fileId) {
                YAHOO.jms.edit(null, 'file/fileAudit.do?dispatch=find&id=' + fileId, null, 'File Changes');
            }
        },
        /**
         * View/Edit/Complete FileAction
         */
        viewFileAction: function(actionId, fileId) {
            if (actionId) {
                YAHOO.jms.edit(null, 'file/viewFileAction.do?dispatch=viewFileAction&actionId=' + actionId, null, 'View File Action');
            }
        },
        editFileAction: function(actionId, fileId) {
            if (actionId) {
                YAHOO.jms.edit(null, 'file/editFileAction.do?dispatch=viewFileAction&actionId=' + actionId, 'scripts/common.js,scripts/module/file/editEmail.js', 'Complete File Action');
            }
        },
        deleteFileAction: function(actionId, fileId) {
            if (actionId) {
                YAHOO.jms.edit(null, 'file/deleteFileAction.do?dispatch=viewFileAction&actionId=' + actionId, null, 'Delete File Action');
            }
        },
        downloadFileAction: function(actionId) {
            if (actionId) {
                YAHOO.jms.sendDownloadRequest('file/downloadFileAction.do?dispatch=downloadFileAction&actionId=' + actionId);
            }
        },
        downloadFileActionAttachment: function(actionId) {
            if (actionId) {
                YAHOO.jms.sendDownloadRequest('file/downloadFileAction.do?dispatch=downloadFileActionAttachment&actionId=' + actionId);
            }
        },
        /**
         * View/Edit/Complete JobAction
         */
        viewJobAction: function(actionId, fileId) {
            if (actionId) {
                YAHOO.jms.edit(null, 'job/viewJobAction.do?dispatch=viewJobAction&actionId=' + actionId, null, 'View Job Action');
            }
        },
        editJobAction: function(actionId, fileId) {
            if (actionId) {
                YAHOO.jms.edit(null, 'job/editJobAction.do?dispatch=viewJobAction&actionId=' + actionId, null, 'Complete Job Action'); // 'scripts/common.js,scripts/module/file/editEmail.js'
            }
        },
        deleteJobAction: function(actionId, fileId) {
            if (actionId) {
                YAHOO.jms.edit(null, 'job/deleteJobAction.do?dispatch=viewJobAction&actionId=' + actionId, null, 'Delete Job Action');
            }
        },
        downloadJobAction: function(actionId) {
            if (actionId) {
                YAHOO.jms.sendDownloadRequest('job/downloadJobAction.do?dispatch=downloadJobAction&actionId=' + actionId);
            }
        },
        downloadJobActionAttachment: function(actionId) {
            if (actionId) {
                YAHOO.jms.sendDownloadRequest('job/downloadJobAction.do?dispatch=downloadJobActionAttachment&actionId=' + actionId);
            }
        },
        // =======================================================================
        // mailOut.js
        //=======================================================================
        mailOut: {
            bulkSelectAll: function(oChecked) {
                var rs = dataTable.getRecordSet();
                var length = rs.getLength();
                for (var i = 0; i < length; i++) {
                    var record = rs.getRecord(i);
                    dataTable.updateCell(record, 'selected', oChecked, i == (length - 1));
                    YAHOO.jms.mailOut.bulkSelection(record);
                }
            },
            bulkSelection: function(oRecord) {
                // re-create selection table
                if (!oRecord && YAHOO.jms.mailOut.bulkSelectionDT) {
                    YAHOO.jms.mailOut.bulkSelectionDT.destroy();
                    YAHOO.jms.mailOut.bulkSelectionDT = null;
                }
                if (!YAHOO.jms.mailOut.bulkSelectionDT) {
                    var oData = {};
                    oData.records = [];
                    // same as WEB-INF/module/file/mailOut/find.jsp
                    oData.columns = [
                         {key: 'id', label: 'Action ID', className: 'width0', hidden: true},
                         {key: 'fileId', label: '', className: 'width0', hidden: true},
                         {key: 'fcaId', label: 'FCA No', sortable: true, 'formatter': 'link'},
                         {key: 'buildingName', label: 'Building Name', sortable: true},
                         {key: 'nextActionDate', label: 'Completed', sortable: true},
                         {key: 'lastSent', label: 'Last Sent', sortable: true},
                         {key: 'lastReceived', label: 'Last Received', sortable: true},
                         {key: 'nextAction', label: 'Last Action', sortable: true},
                         {key: 'selected', label: '', className: 'width1em', title: 'Exclude File Action', sortable: false, formatter: 'checkbox', disabled: false}
                    ];
                    var container = document.createElement('div');
                    container.setAttribute('id', 'previewDiv');
                    YUD.get('preview').appendChild(container);
                    YAHOO.jms.mailOut.bulkSelectionDT = YAHOO.jms.createStaticDataTable(container, oData);
                    YAHOO.jms.mailOut.bulkSelectionDT.subscribe('checkboxClickEvent', function(oArgs) {
                        var elCheckbox = oArgs.target;
                        if (!elCheckbox.checked) {
                            var oRecord = this.getRecord(elCheckbox);
                            YAHOO.jms.mailOut.bulkSelectionDT.deleteRow(oRecord);
                        }
                    });
                }
                // find existing record
                if (oRecord) {
                    var fileId = oRecord.getData('fileId');
                    var index = -1;
                    var rs = YAHOO.jms.mailOut.bulkSelectionDT.getRecordSet();
                    for (var i = 0; i < rs.getLength(); i++) {
                        var r = rs.getRecord(i);
                        if (r.getData('fileId') === fileId) {
                            index = i;
                            break;
                        }
                    }
                    // add/remove
                    if (oRecord.getData('selected') === true) {
                        if (index < 0) {
                            YAHOO.jms.mailOut.bulkSelectionDT.addRow(oRecord.getData(), 0);
                        }
                    } else {
                        if (index >= 0) {
                            YAHOO.jms.mailOut.bulkSelectionDT.deleteRow(index);
                        }
                    }
                }
            },
            createBatch: function() {
                if (!YAHOO.jms.mailOut.bulkSelectionDT) {
                    return;
                }
                var fileIds = [];
                var rs = YAHOO.jms.mailOut.bulkSelectionDT.getRecordSet();
                for (var i = 0; i < rs.getLength(); i++) {
                    var row = rs.getRecord(i);
                    if (row.getData('selected') === true) {
                        fileIds.push(row.getData('fileId'));
                    }
                }
                var postdata = {
                    actionCodeId: YUD.get('filter0Hidden').value,
                    fileIds: fileIds
                };
                var callback = {
                    cache: false,
                    success: function(oResponse) {
                        YAHOO.jms.filterActionCallback.success(oResponse);
                        // remove completed
                        var rs = YAHOO.jms.mailOut.bulkSelectionDT.getRecordSet();
                        for (var i = rs.getLength() - 1; i >= 0; i--) {
                            var row = rs.getRecord(i);
                            if (row.getData('selected') === true) {
                                YAHOO.jms.mailOut.bulkSelectionDT.deleteRow(row);
                            }
                        }
                        // set from response
                        var data = YAHOO.jms.parseJsonData(oResponse.responseText);
                        YAHOO.jms.mailOut.batchId = data.tabs[2].config.batchId;
                    },
                    failure: function(oResponse) {
                        YAHOO.jms.filterActionCallback.failure(oResponse);
                    }
                };
                YAHOO.jms.sendPostRequest(postdata, 'file/mailOut.do?readOnly=false&dispatch=createBatch', null, null, callback);
            },
            loadTab3: function(oStatuses) {
                // re-create toolbar
                if (YAHOO.jms.mailOut.tab3Toolbar) {
                    YAHOO.jms.mailOut.tab3Toolbar.destroy();
                    YAHOO.jms.mailOut.tab3Toolbar = null;
                }
                YAHOO.jms.mailOut.tab3Toolbar = new YAHOO.widget.Toolbar('tab3.toolbar', {buttons: [
                    {id: 'tb_completeBatch', type: 'push', label: 'Complete Batch', value: 'custom'}
                ]});
                YAHOO.jms.mailOut.tab3Toolbar.on('buttonClick', function(ev) {
                    if (ev.button.id == 'tb_completeBatch') {
                        var row = YAHOO.jms.getSelectedRow(YAHOO.jms.mailOut.batchDT);
                        var batchId = row ? row.id : null;
                        if (batchId) {
                            if (confirm('Do you want to complete selected actions for batch #' + batchId + '?')) {
                                YAHOO.jms.mailOut.completeBatch();
                            }
                        }
                    }
                });
                // re-create batch table
                if (YAHOO.jms.mailOut.batchDT) {
                    YAHOO.jms.mailOut.batchDT.destroy();
                    YAHOO.jms.mailOut.batchDT = null;
                }
                var h = YUD.getRegion(YAHOO.jms.tabView._contentParent).height / 2;
                var w = YUD.getRegion(YAHOO.jms.tabView._contentParent).width;
                var config = {
                    responseType: YAHOO.util.DataSource.TYPE_HTMLTABLE,
                    columns: [
                        {key: 'id', label: 'Batch ID', sortable: true, className: 'number', width: 40},
                        {key: 'name', label: 'Batch Name', sortable: true, className: ''},
                        {key: 'createdBy', label: 'Created By', sortable: true, className: '', width: 100},
                        {key: 'createdDate', label: 'Created Date', sortable: true, className: '', width: 60},
                        {key: 'completed', label: 'Completed %', sortable: true, className: 'number', width: 60}
                    ]
                };
                YAHOO.jms.mailOut.batchDT = YAHOO.jms.createStaticDataTable('batchDiv', 'batchData', config, h + 'px', w + 'px');
                YAHOO.jms.mailOut.batchDT.subscribe('rowSelectEvent', function() {
                    var row = YAHOO.jms.getSelectedRow(YAHOO.jms.mailOut.batchDT);
                    var batchId = row ? row.id : null;
                    YAHOO.jms.mailOut.batchId = batchId;
                    // delete all rows
                    var length = YAHOO.jms.mailOut.batchViewDT.getRecordSet().getLength();
                    YAHOO.jms.mailOut.batchViewDT.deleteRows(0, length);
                    // add new rows
                    var callback = {
                        cache: false,
                        success: function(oResponse) {
                            var data = YAHOO.jms.parseJsonData(oResponse.responseText);
                            YAHOO.jms.mailOut.batchViewDT.addRows(data.rows);
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    };
                    YUC.asyncRequest('GET', 'file/mailOut.do?dispatch=findBatchFiles&batchId=' + batchId, callback);
                }, YAHOO.jms.mailOut.batchDT, true);
                // re-create batch view table
                if (YAHOO.jms.mailOut.batchViewDT) {
                    YAHOO.jms.mailOut.batchViewDT.destroy();
                    YAHOO.jms.mailOut.batchViewDT = null;
                }
                var data = {
                    columns: [
                        {key: 'id', label: 'Action ID', className: 'width0', hidden: true},
                        {key: 'fileId', label: '', className: 'width0', hidden: true},
                        {key: 'fcaId', label: 'FCA No', sortable: true, formatter: 'link'},
                        {key: 'buildingName', label: 'Building Name', sortable: true},
                        {key: 'actionCode', label: 'Action Code', sortable: true},
                        {key: 'dueDate', label: 'Due Date', sortable: true},
                        {key: 'status', label: 'Status', sortable: true, editor: 'dropdown', dropdownOptions: oStatuses},
                        {key: 'statusDate', label: 'Status Date', sortable: true},
                        {key: 'view', label: '', className: 'width1em', formatter: 'link'},
                        {key: 'selected', label: '', className: 'width1em', title: 'Exclude File Action', sortable: false, formatter: 'checkbox', disabled: false}
                    ]
                };
                YAHOO.jms.mailOut.batchViewDT = YAHOO.jms.createStaticDataTable('batchViewDiv', data);
                // select batch
                var rs = YAHOO.jms.mailOut.batchDT.getRecordSet();
                for (var i = 0; i < rs.getLength(); i++) {
                    var r = rs.getRecord(i);
                    if (r.getData('id') == YAHOO.jms.mailOut.batchId) {
                        YAHOO.jms.mailOut.batchDT.selectRow(r);
                        break;
                    }
                }
                // Fired when a CellEditor input is saved.
                var onStatusSaveEvent = function(oArgs) {
                    var editor = oArgs.editor;
                    var row = editor.getRecord();
                    var id = row.getData('id');
                    var value = editor.dropdown.value;
                    var callback = {
                        cache: false,
                        success: function(o) {

                        },
                        failure: function(o) {
                            YAHOO.jms.filterActionCallback.failure(o);
                        }
                    };
                    YUC.asyncRequest('GET', 'file/mailOut.do?readOnly=false&dispatch=saveStatus&id=' + id + '&value=' + value, callback);
                };
                YAHOO.jms.mailOut.batchViewDT.getColumn('status').editor.subscribe('saveEvent', onStatusSaveEvent);
            },
            completeBatch: function() {
                var row = YAHOO.jms.getSelectedRow(YAHOO.jms.mailOut.batchDT);
                if (!row) {
                    return;
                }
                var batchId = row.id || YAHOO.jms.mailOut.batchId;
                var fileIds = [];
                var rs = YAHOO.jms.mailOut.batchViewDT.getRecordSet();
                for (var i = 0; i < rs.getLength(); i++) {
                    var row = rs.getRecord(i);
                    if (row.getData('selected') === true) {
                        fileIds.push(row.getData('fileId'));
                    }
                }
                var postdata = {
                    batchId: batchId,
                    fileIds: fileIds
                };
                var callback = {
                    cache: false,
                    success: function(o) {
                        // reload tab3
                        var tab = YAHOO.jms.tabView.get('activeTab');
                        tab._dataConnect();
                    },
                    failure: function(o) {
                        YAHOO.jms.filterActionCallback.failure(o);
                    }
                };
                YAHOO.jms.sendPostRequest(postdata, 'file/mailOut.do?readOnly=false&dispatch=completeBatch', null, null, callback);
            }
        },
        mailIn: {
            /**
             * Fired when a CellEditor input is saved.
             * @param oArgs
             *     oArgs.editor <YAHOO.widget.CellEditor> The CellEditor instance
             *     oArgs.newData <Object> New data value from form input field
             *     oArgs.oldData <Object> Old data value.
             */
            onStatusSaveEvent: function(oArgs) {
                var editor = oArgs.editor;
                var row = editor.getRecord();
                var id = row.getData('id');
                var value = editor.dropdown.value;
                var callback = {
                    cache: false,
                    success: function(o) {

                    },
                    failure: function(o) {
                        YAHOO.jms.filterActionCallback.failure(o);
                    }
                };
                YUC.asyncRequest('GET', 'file/mailIn.do?readOnly=false&dispatch=saveStatus&id=' + id + '&value=' + value, callback);
            }
        },
        user: {
            onchangeReissuePassword: function(oCheckbox) {
                if (YUD.get('userTypeId').value == 0) { // system
                    YAHOO.jms.toggleClass('password', 'hidden');
                }
            }
        },
        /**
         * Edit File AseChange
         */
        editAseChange: function(fileId) {
            YAHOO.jms.edit(null, 'file/editAseChange.do?dispatch=edit&id=' + fileId, 'scripts/common.js', 'ASE Change Over - Supplier Scheduling');
        },
        /**
         * StaffLeave
         */
        editStaffLeave: function(staffLeaveId) {
            YAHOO.jms.edit(null, 'user/calendarStaff.do?dispatch=edit&id=' + staffLeaveId, null, 'Edit Staff Leave', 0.8);
        },
        approveStaffLeave: function(staffLeaveId) {
            YAHOO.jms.sendGetRequest('user/calendarStaff.do?dispatch=approve&readOnly=false&id=' + staffLeaveId, null, 'Approve Staff Leave');
        },
        declineStaffLeave: function(staffLeaveId) {
            YAHOO.jms.sendGetRequest('user/calendarStaff.do?dispatch=decline&readOnly=false&id=' + staffLeaveId, null, 'Decline Staff Leave');
        },
// =======================================================================
// file.js
//=======================================================================
        file: {
            fcaDoc: {
                fcaDocsTree: null,
                fcaDocsMenu: null,
                createTree: function() {
                    YAHOO.jms.file.fcaDoc.destroyTree();
                    // tree
                    var treeEl = YUD.get('fcaDocsTree');
                    var tree = new YAHOO.widget.TreeView(treeEl);
                    tree.subscribe('dblClickEvent', tree.onEventEditNode);
                    tree.subscribe('editorSaveEvent', function(oArgs) {
                        YAHOO.jms.file.fcaDoc.updateFileDirectory(oArgs.node, oArgs.oldValue, oArgs.newValue);
                    });
                    tree.singleNodeHighlight = true;
                    tree.subscribe('clickEvent', tree.onEventToggleHighlight);
                    //tree.setNodesProperty('propagateHighlightUp', true);
                    //tree.setNodesProperty('propagateHighlightDown', true);
                    tree.render();
                    YAHOO.jms.file.fcaDoc.fcaDocsTree = tree;
                    // contextMenu
                    var editFcaDocEl = YUD.get('editFcaDoc');
                    if (editFcaDocEl && editFcaDocEl.value == 'true') {
                        var menu = new YAHOO.widget.ContextMenu('fcaDocsMenu', {
                            trigger: 'fcaDocsTree',
                            lazyload: true,
                            zIndex: 2,
                            itemdata: [
                                   { text: 'New File', onclick: { fn: function() {YAHOO.jms.file.fcaDoc.addFile();} } },
                                { text: 'New Folder', onclick: { fn: function() {YAHOO.jms.file.fcaDoc.addDirectory();} } },
                                { text: 'Edit Name', onclick: { fn: function() {YAHOO.jms.file.fcaDoc.editFileDirectory();} } },
                                { text: 'Delete', onclick: { fn: function() {YAHOO.jms.file.fcaDoc.deleteFileDirectory();} } }
                            ]
                        });
                        menu.subscribe('triggerContextMenu', function(e) {
                            var oTarget = this.contextEventTarget;
                            var node = YAHOO.jms.file.fcaDoc.fcaDocsTree.getNodeByElement(oTarget);
                            if (!node) {
                                this.cancel();
                            }
                        });
                        YAHOO.jms.file.fcaDoc.fcaDocsMenu = menu;
                    }
                },
                destroyTree: function() {
                    if (YAHOO.jms.file.fcaDoc.fcaDocsTree) {
                        YAHOO.jms.file.fcaDoc.fcaDocsTree.destroy();
                        YAHOO.jms.file.fcaDoc.fcaDocsTree = null;
                    }
                    if (YAHOO.jms.file.fcaDoc.fcaDocsMenu) {
                        YAHOO.jms.file.fcaDoc.fcaDocsMenu.destroy();
                        YAHOO.jms.file.fcaDoc.fcaDocsMenu = null;
                    }
                },
                reloadTree: function() {
                    var fileId = YUD.get('fileId').value;
                    YUC.asyncRequest('GET', 'file/editFile.do?dispatch=viewFcaDocs&id=' + fileId, {
                        cache: false,
                        success: function(oResponse) {
                            YAHOO.jms.file.fcaDoc.destroyTree();
                            YUD.get('fcaDocsTree').innerHTML = oResponse.responseText;
                            YAHOO.jms.file.fcaDoc.createTree();
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.alert('Failure', oResponse);
                        }
                    });
                },
                getFcaDocName: function(node) {
                    var fcaDocIds = YUS.query('td[id^=fcaDoc]', node.getContentEl());
                    return fcaDocIds[0].innerHTML;
                },
                getFcaDocDirectory: function(node) {
                    var dir = '';
                    var parent = node.parent;
                    while (!parent.isRoot()) {
                        dir = YAHOO.jms.file.fcaDoc.getFcaDocName(parent) + '/' + dir;
                        parent = parent.parent;
                    }
                    return dir;
                },
                addFile: function() {
                    var node = YAHOO.jms.file.fcaDoc.fcaDocsTree.getHighlightedNode();
                    if (node && node.isLeaf) {
                        node = node.parent; // use file parent (directory)
                    }
                    if (node && !node.isLeaf) {
                        var fileId = YUD.get('fileId').value;
                        var dir = YAHOO.jms.file.fcaDoc.getFcaDocDirectory(node) + YAHOO.jms.file.fcaDoc.getFcaDocName(node);
                        YAHOO.jms.edit(null, 'file/editFile.do?dispatch=uploadFile&id=' + fileId + '&dir=' + encodeURIComponent(dir),
                            null, 'Add FCA Document', 450, 150);
                    }
                },
                addDirectory: function() {
                    var tree = YAHOO.jms.file.fcaDoc.fcaDocsTree;
                    if (tree) {
                        var parent = tree.getHighlightedNode();
                        if (parent && parent.isLeaf) {
                            return;
                        }
                        if (!parent) {
                            parent = tree.getRoot();
                        }
                        var node = new YAHOO.widget.TextNode({
                            label: 'New Folder',
                            editable: true,
                            expanded: false
                        }, parent);
                        tree.render();
                        node.editNode();
                    }
                },
                editFileDirectory: function() {
                    var node = YAHOO.jms.file.fcaDoc.fcaDocsTree.getHighlightedNode();
                    if (node) {
                        var oldName = YAHOO.jms.file.fcaDoc.getFcaDocName(node);
                        var newName = window.prompt('Enter a new name for this folder: ', oldName);
                        if (newName && newName.length > 0) {
                            YAHOO.jms.file.fcaDoc.updateFileDirectory(node, oldName, newName);
                        }
                    }
                },
                deleteFileDirectory: function() {
                    var node = YAHOO.jms.file.fcaDoc.fcaDocsTree.getHighlightedNode();
                    if (node) {
                        var oldName = YAHOO.jms.file.fcaDoc.getFcaDocName(node);
                        if (confirm('Do you want to delete folder: ' + oldName + '?')) {
                            YAHOO.jms.file.fcaDoc.updateFileDirectory(node, oldName, null);
                        }
                    }
                },
                updateFileDirectory: function(node, oldName, newName) {
                    var fileId = YUD.get('fileId').value;
                    var dir = YAHOO.jms.file.fcaDoc.getFcaDocDirectory(node);
                    YUC.asyncRequest('GET', 'oauth/file/fcaDoc/updateName/' + fileId
                            + '?oldName=' + (oldName ? encodeURIComponent(dir + oldName) : '')
                            + '&newName=' + (newName ? encodeURIComponent(dir + newName) : ''),
                    {
                        cache: false,
                        success: function(oResponse) {
                            YAHOO.jms.file.fcaDoc.reloadTree();
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.alert('Failure', oResponse);
                        	//YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    });
                },
                downloadFileDoc: function(pathname) {
					YAHOO.jms.sendDownloadRequest('file/downloadFileDoc.do?dispatch=downloadFcaDoc&id=' + encodeURIComponent(pathname), null, 'Download FCA Document');
				}
            },
            aseKey: {
            	edit: function(aseKeyId) {
            		if (aseKeyId) {
                        YAHOO.jms.edit(null, 'file/aseKey.do?dispatch=edit&id=' + aseKeyId, null, 'Edit Ase Key');
            		} else {
                        YAHOO.jms.edit(null, 'file/aseKey.do?dispatch=edit', null, 'Add Ase Key');
            		}
            	},
            	editDetailView: function(aseKeyId) {
            		if (aseKeyId) {
                        YAHOO.jms.edit(null, 'file/aseKey.do?dispatch=detailView&view=detailView&id=' + aseKeyId, 'scripts/module/file/edit.js');
            		} else {
                        var row = YAHOO.jms.getSelectedRow(dataTable);
                        YAHOO.jms.edit(row, 'file/aseKey.do?dispatch=detailView&view=detailView', 'scripts/module/file/edit.js');
            		}
            	}
            },
            aseKeyOrder: {
            	aseKeyDT: null,
            	init: function() {
            		// aseKeyOrderEdit
                    YUE.addListener('aseKeyOrderEdit', 'click', function(e) {
                        YUE.stopEvent(e);
                        var orderId = YAHOO.jms.getSelectedRowId(dataTable);
                        YAHOO.jms.file.aseKeyOrder.edit(orderId);
                    });
                    // aseKeyAdd
                    YUE.addListener('aseKeyAdd', 'click', function(e) {
                        YUE.stopEvent(e);
                        var orderId = YAHOO.jms.getSelectedRowId(dataTable);
                        YAHOO.jms.file.aseKeyOrder.addAseKey(orderId);
                    });
            		// aseKeys dataTable
            		YUE.onContentReady('aseKeysDiv', function() {
                        if (YAHOO.jms.file.aseKeyOrder.aseKeyDT) {
                            YAHOO.jms.file.aseKeyOrder.aseKeyDT.destroy(); YAHOO.jms.file.aseKeyOrder.aseKeyDT = null;
                        }
                        var columnDefs = [
                            {key: 'id', label: '', hidden: true, className: 'width0'},
                            {key: 'aseKeyNo', label: 'Key Number', className: 'number', sortable: true, formatter: 'link'},
                            {key: 'contact.name', label: 'Key Holder', sortable: true},
                            {key: 'licenseNo', label: 'License Number', sortable: true},
                            {key: 'contact.mobile', label: 'Mobile', sortable: true},
                            {key: 'status', label: 'Status', sortable: true}
                        ];
                        // re-create batch table
                        if (YAHOO.jms.file.aseKeyOrder.aseKeyDT) {
                            YAHOO.jms.file.aseKeyOrder.aseKeyDT.destroy(); YAHOO.jms.file.aseKeyOrder.aseKeyDT = null;
                        }
                        var aseKeyDT = YAHOO.jms.createStaticDataTable(this, 'aseKeysData', {
                        	columns: columnDefs,
                        	responseType: YAHOO.util.DataSource.TYPE_HTMLTABLE
                        });
                        aseKeyDT.subscribe('rowDblclickEvent', function() {
                            var orderId = YAHOO.jms.getSelectedRowId(dataTable);
                            var aseKeyId = YAHOO.jms.getSelectedRowId(this);
                            YAHOO.jms.file.aseKeyOrder.editAseKey(aseKeyId);
                        }, aseKeyDT, true);
                        YAHOO.jms.file.aseKeyOrder.aseKeyDT = aseKeyDT;
            		});
            	},
            	edit: function(orderId) {
            		if (orderId) {
                        YAHOO.jms.edit(null, 'file/aseKeyOrder.do?dispatch=edit&id=' + orderId, null, 'Edit Order');
            		} else {
                        YAHOO.jms.edit(null, 'file/aseKeyOrder.do?dispatch=edit', null, 'Add Order');
            		}
            	},
            	editDetailView: function(orderId) {
            		if (orderId) {
                        YAHOO.jms.edit(null, 'file/aseKeyOrder.do?dispatch=detailView&view=detailView&id=' + orderId, 'scripts/module/file/edit.js');
            		} else {
                        var row = YAHOO.jms.getSelectedRow(dataTable);
                        YAHOO.jms.edit(row, 'file/aseKeyOrder.do?dispatch=detailView&view=detailView', 'scripts/module/file/edit.js');
            		}
            	},
            	addAseKey: function(orderId) {
                    YAHOO.jms.edit(null, 'file/aseKeyOrder.do?dispatch=addAseKey&id=' + orderId, null, 'Add ASE Key');
            	},
               	editAseKey: function(aseKeyId) {
                    YAHOO.jms.edit(null, 'file/aseKeyOrder.do?dispatch=editAseKey&aseKey.aseKeyId=' + aseKeyId, null, 'Edit ASE Key');
            	}
            }
        },
// =======================================================================
// finance.js
//=======================================================================
        finance: {
            invoiceTabId: 'tab3',
            invoiceToolbarTop: null,
            invoiceToolbarBottom: null,
            glDataTable: null,
            addBatchInvoice: function(oBatchId) {
                this.loadInvoiceTab('finance/externalInvoice.do?dispatch=addInvoice&readOnly=false&id=' + oBatchId, true);
            },
            editBatchInvoice: function(oInvoiceId) {
                this.loadInvoiceTab('finance/externalInvoice.do?dispatch=editInvoice&id=' + oInvoiceId, true);
            },
            viewBatchInvoice: function(oInvoiceId) {
                this.loadInvoiceTab('finance/externalInvoice.do?dispatch=viewInvoice&id=' + oInvoiceId, false);
            },
            addGlData: function(oInvoiceId) {
                this.glDataTable.addRow([{glAccount: '', costCenter: '', taxCode: '', amount: '', debitCredit: '', text: '', fca: ''}], 0);
            },
            loadInvoiceTab: function(oDataSrc, oEditable) {
                var tabId = this.invoiceTabId;
                var tab = YAHOO.jms.getTab(tabId);
                tab.set('dataSrc', oDataSrc);
                tab.set('cacheData', false);
                tab.get('contentEl').innerHTML = '';
                YUE.onContentReady(tabId + '.content', function() {
                    // invoiceToolbarTop
                    var toolbar = new YAHOO.widget.Toolbar(tabId + '.toolbar', {buttons: [
                        {id: 'tb_openDocument', type: 'push', label: 'Open Document', value: 'openDocument'},
                        {id: 'tb_addInvoice', type: 'push', label: 'Add Invoice', value: 'newInvoice'}
                    ]});
                    toolbar.on('buttonClick', function(ev) {
                        if (ev.button.id == 'tb_openDocument') {
                            // TODO:
                        } else if (ev.button.id == 'tb_addInvoice') {
                            var batchId = YUD.get('batchId').value;
                            if (batchId) {
                                if (confirm('Do you want to create new invoice for batch #' + batchId + '?')) {
                                    YAHOO.jms.finance.addBatchInvoice(batchId);
                                }
                            }
                        }
                    });
                    YAHOO.jms.finance.invoiceToolbarTop = toolbar;
                    // invoiceToolbarBottom
                    toolbar = new YAHOO.widget.Toolbar(tabId + '.toolbar.bottom', {buttons: [
                        {id: 'tb_submit', type: 'push', label: 'Submit', value: 'submit'},
                        {id: 'tb_cancel', type: 'push', label: 'Close', value: 'close'}
                    ]});
                    toolbar.on('buttonClick', function(ev) {
                        if (ev.button.id == 'tb_submit') {
                            var form = document.forms['externalInvoiceForm'];
                            var action = form.action;
                            // add readOnly parameter to mark transaction for commit (@see TransactionFilter)
                            action += (action.indexOf('?') > 0 ? '&' : '?') + 'readOnly=false';
                            // glDataTable - add to form
                            var dt = YAHOO.jms.finance.glDataTable;
                            var cd = dt.getColumnSet().getDefinitions();
                            var rs = dt.getRecordSet();
                            for (var r = 0; r < rs.getLength(); r++) {
                                var row = rs.getRecord(r);
                                var base = 'entity.glData[' + r + '].';
                                for (var c = 0; c < cd.length; c++) {
                                    var key = cd[c].key;
                                    var cell = row.getData(key);
                                    var input = YUD.get(base + key);
                                    if (!input) {
                                        input = document.createElement('input');
                                        input.setAttribute('type', 'hidden');
                                        input.setAttribute('id', base + key);
                                        input.setAttribute('name', base + key);
                                        form.appendChild(input);
                                    }
                                    if (key == 'amount' && cell) {
                                        if (cell.indexOf('$') == 0) {
                                            cell = cell.substring(1);
                                        }
                                        cell = cell.replace(',', '');
                                    }
                                    input.setAttribute('value', cell ? cell : '');
                                }
                            }
                            // oData, oAction, oScript, oTitle, oCallback, w, h
                            YAHOO.jms.sendPostRequest(form, action, null, null, {
                                success: function(oResponse) {
                                    YAHOO.jms.finance.editBatchInvoice(YUD.get('invoiceId').value);
                                },
                                failure: function(oResponse) {
                                    YAHOO.jms.emptyCallback.failure(oResponse);
                                }
                            });
                        } else if (ev.button.id == 'tb_cancel') {
                            if (confirm('Do you want to revert your changes?')) {
                                YAHOO.jms.finance.editBatchInvoice(YUD.get('invoiceId').value);
                            }
                        }
                    });
                    YAHOO.jms.finance.invoiceToolbarBottom = toolbar;
                    // invoice glDataTable
                    if (YAHOO.jms.finance.glDataTable) { YAHOO.jms.finance.glDataTable.destroy(); YAHOO.jms.finance.glDataTable = null; }
                    var columnDefs = [
                        {key: 'invoiceGlDataId', label: '', hidden: true, className: 'width0'},
                        {key: 'glAccount', label: 'GL Account', sortable: true, editor: !oEditable ? null : new YAHOO.widget.TextboxCellEditor({disableBtns: true})},
                        {key: 'costCenter', label: 'Cost Center', sortable: true, editor: !oEditable ? null : new YAHOO.widget.TextboxCellEditor({disableBtns: true})},
                        {key: 'taxCode', label: 'Tax Code', sortable: true, editor: !oEditable ? null : new YAHOO.widget.TextboxCellEditor({disableBtns: true})},
                        {key: 'amount', label: 'Amount', className: 'number', sortable: true, editor: !oEditable ? null : new YAHOO.widget.TextboxCellEditor({disableBtns: true, validator: YAHOO.jms.validateCurrency})},
                        {key: 'debitCredit', label: 'Debit/Credit', sortable: true, editor: !oEditable ? null : new YAHOO.widget.RadioCellEditor({radioOptions: ['DR', 'CR'], disableBtns: true})},
                        {key: 'text', label: 'Text', sortable: true, editor: !oEditable ? null : new YAHOO.widget.TextareaCellEditor({disableBtns: true})},
                        {key: 'assignment', label: 'Assignment', sortable: true, editor: !oEditable ? null : new YAHOO.widget.TextboxCellEditor({disableBtns: true})}
                    ];
                    var dataSource = new YAHOO.util.LocalDataSource(YUD.get('glData'));
                    dataSource.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
                    dataSource.responseSchema = {fields: YAHOO.jms.getFields(columnDefs)};
                    var dataTable = new YAHOO.widget.DataTable('glDataDiv', columnDefs, dataSource, {});
                    // Set up editing flow
                    if (oEditable) {
                        var highlightEditableCell = function(oArgs) {
                            var elCell = oArgs.target;
                            if (YUD.hasClass(elCell, 'yui-dt-editable')) {
                                this.highlightCell(elCell);
                            }
                        };
                        dataTable.subscribe('cellMouseoverEvent', highlightEditableCell);
                        dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
                        dataTable.subscribe('cellClickEvent', dataTable.onEventShowCellEditor);
                    } else {
                        dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
                        dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
                        dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
                    }
                    YAHOO.jms.finance.glDataTable = dataTable;
                    // toggle
                    YAHOO.jms.toggleContainers(this);
                    if (oEditable) {
                        YAHOO.jms._initAfterCallback(this);
                    }
                });
                YAHOO.jms.tabView.set('activeTab', tab);
                tab.set('cacheData', true);
            }
        },
// =======================================================================
// setup.js
//=======================================================================
        setup: {
        	runProcess: function(name) {
        		var callback = {
    	            cache: false,
    	            success: function(oResponse) {
    	                //var responseText = oResponse.responseText;
    	            },
    	            failure: function(oResponse) {
    	            	YAHOO.jms.emptyCallback.failure(oResponse);
    	            },
    	            argument: {silentProgress: false}
        		};
        		YUC.asyncRequest('GET', 'setup/database.do?dispatch=runProcess&propogationBehavior=5&name=' + name, callback);
        	}
        },
// =======================================================================
// entity.js
//=======================================================================
        entity: {
        	actionWorkflow: {
        		onchangeActive: function(oCheckbox, oRecordId, oDataTable) {
        			oDataTable = oDataTable || dataTable;
        			var r = oDataTable.getRecord(oRecordId);
                    if (!r) {
                    	return false;
                    }
        			var d = r._oData;
                    YUC.asyncRequest('GET', 'oauth/entity/actionWorkflow/active/'
                        + (d.id ? d.id : 0) + '/' + d.actionCodeId + '/' + d.outcomeId + '/' + oCheckbox.checked,
                    {
                        cache: false,
                        success: function(oResponse) {
                            //alert('Success');
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.alert('Failure', oResponse);
                        	//YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    });
           		}
        	}
        }
// =======================================================================
// *.js
//=======================================================================

    };

    //Call loader the first time
    var loader = new YAHOO.util.YUILoader({
//        //Allows you to specify a different location (as a full or relative filepath) for the YUI build directory.
//        base: 'yui/',
//        require: ['animation','autocomplete','element','get','history','connection','button','container','cookie','datatable','datasource','element','editor','get','json','logger','layout','menu','paginator','reset-fonts-grids','resize','selector','tabview','treeview','utilities'],
//        loadOptional: false,
//        //Combine YUI files into a single request (per file type) by using the Yahoo! CDN combo service.
//        combine: true,
//        allowRollup: true,
//        //skin: {base: 'assets/skins/',defaultSkin: 'sam' },
        onSuccess: function() {
            //Use the DD shim on all DD objects
            YAHOO.util.DDM.useShim = true;

            //Error message: "A script on this page is causing Internet Explorer to run slowly" (http://support.microsoft.com/kb/175500)
            //http://ajaxian.com/archives/no-more-ie6-background-flicker
            if (YAHOO.env.ua.ie >= 6) {
                try {
                    document.execCommand('BackgroundImageCache', false, true);
                } catch (ignore) {
                    // just in case this fails .. ?
                }
//                // Disable backspace and delete key with javascript in IE
//                document.onkeydown(function(e) {
//                    if (e.keyCode === 8) {
//                        var element = e.target.nodeName.toLowerCase();
//                        if ((element != 'input' && element != 'textarea') || $(e.target).attr("readonly")) {
//                            return false;
//                        }
//                    }
//                });
            }

            /*
             * Subscribe to global listeners
             * var oResponse = args[0];
             */
            var globalEvents = {
                start: function(type, args){
                    YAHOO.jms.progressStart(args);
                },
                complete: function(type, args){
                    YAHOO.jms.progressStop(args);
                },
                success: function(type, args){
                    YAHOO.jms.progressStop(args);
                },
                failure: function(type, args){
                    YAHOO.jms.progressStop(args);
                },
                abort: function(type, args){
                    YAHOO.jms.progressStop(args);
                }
            };
            YUC.startEvent.subscribe(globalEvents.start);
            YUC.completeEvent.subscribe(globalEvents.complete);
            YUC.successEvent.subscribe(globalEvents.success);
            YUC.failureEvent.subscribe(globalEvents.failure);
            YUC.abortEvent.subscribe(globalEvents.abort);

            //Load the global CSS files.
            YUG.css('styles/default.css');

//=======================================================================
// layout.js
//=======================================================================
            YAHOO.jms.layout = new YAHOO.widget.Layout({
                minWidth: 1000,
                units: [
                    { position: 'top', height: 45, resize: false, body: 'header', zIndex: 3 },
                    debug ? { position: 'right', width: 200, body: '', header: 'Console', collapse: true } : {}, //LOG
                    { position: 'left', width: 200, resize: false, body: 'leftNav', collapse: false, gutter: '0 5px 0 5px', zIndex: 2 },
                    { position: 'center', gutter: '0 5px 0 2px', zIndex: 1 }
                ]
            });

            //On render, load tabview.js, button.js etc
            YAHOO.jms.layout.on('render', function() {
                window.setTimeout(function() {
                    if (debug) YUG.script('scripts/logger.js'); //LOG

//=======================================================================
// tabview.js
//=======================================================================
                    // Create the tabView
                    YAHOO.jms.tabView = new YAHOO.widget.TabView();
                    // Create the Data tab
                    YAHOO.jms.dataTab = new YAHOO.widget.Tab({
                        //Inject a span for the icon
                        label: 'Home Page',
                        id: 'tab1',
                        active: true,
                        content:
                            '<div id="dataDiv">' +
                                '<div id="top2">' +
                                    '<div id="menuBarDiv"></div>' +
                                    '<div id="dataToolbarDiv"></div>' +
                                    '<div id="filtersDiv" class="yui-gb">' +
                                        '<div id="filtersLeftDiv" class="yui-u first"></div>' +
                                        '<div id="filtersCenterDiv" class="yui-u"></div>' +
                                        '<div id="filtersRightDiv" class="yui-u"></div>' +
                                    '</div>' +
                                '</div>' +
                                '<div id="center2">' +
                                    '<div id="errors"></div>' +
                                    '<div id="dataTable"></div>' +
                                    '<div id="dataTable.paginator"></div>' +
                                '</div>' +
                                '<div id="bottom2" class="jms-container" style="position:relative; display:none;">' +
                                    '<div class="jms-container-hd">' +
                                        '<h2>View Selection</h2>' +
                                        '<span id="toggle.bottom2" class="collapse collapsed">X</span>' +
                                    '</div>' +
                                    '<div id="toggle.bottom2.container" class="jms-container-bd" style="display:none;">' +
                                        '<div id="preview"></div>' +
                                    '</div>' +
                                '</div>' +
                            '</div>'
                    });
                    YAHOO.jms.tabView.addTab(YAHOO.jms.dataTab);
                    // Create the Detail tab
                    YAHOO.jms.detailTab = new YAHOO.widget.Tab({
                        //Inject a span for the icon
                        label: 'Details',
                        id: 'tab2',
                        content:
                            '<div>' +
                                '<div id="errors"></div>' +
                                '<div id="center3">' +
                                    '<div id="detailDiv"></div>' +
                                '</div>' +
                            '</div>'
                    });
                    // TODO: add/remove tab dynamically
                    YAHOO.jms.tabView.addTab(YAHOO.jms.detailTab);
                    //YAHOO.jms.tabView.removeTab(YAHOO.jms.detailTab);
                    YAHOO.jms.tabView.on('activeTabChange', function(ev) {
                        // Tabs have changed
                        var tab = ev.newValue;
                        var tabId = tab.get('id');
                        if (tabId == 'tab1') {
                            //data tab was selected
                            if (!YAHOO.jms.dataLoaded && !YAHOO.jms.dataLoading) {
                                //YAHOO.log('Data is not loaded yet, use Get to fetch it', 'info', 'tabview.js');
                                //YAHOO.log('Adding loading class to tabview', 'info', 'tabview.js');
                                YAHOO.jms.getData();
                            }
                        }
                        else if (tabId == 'tab2') {
                            //detail tab was selected
                        }
                    });
                    // Add the tabview to the center unit of the main layout
                    var elCenterWrap = YAHOO.jms.layout.getUnitByPosition('center').get('wrap');
                    YAHOO.jms.tabView.appendTo(elCenterWrap);
                    YAHOO.jms.toggleContainers(elCenterWrap);
                    //
                    var elLeftWrap = YAHOO.jms.layout.getUnitByPosition('left').get('wrap').firstChild;
                    YUD.setStyle(elLeftWrap, 'overflow', 'visible');
                    // When tab1 is available, update the height..
                    YUE.onContentReady('tab1', function() {
                        YAHOO.jms.resizeWindow();
                        var t = YAHOO.jms.tabView.get('tabs');
                        for (var i = 0; i < t.length; i++) {
                            if (t[i].get('id') == 'tab1') {
                                var el = t[i].get('contentEl');
                                el.id = 'dataHolder';
                                //el = YUD.get('dataTable');
                                var contentRegion = YUD.getRegion(YAHOO.jms.tabView._contentParent);
                                //YUD.setStyle(el, 'height', contentRegion.height + 'px');
                                YUD.setStyle(el, 'height', YUD.getStyle(YAHOO.jms.tabView._contentParent, 'height'));
                                YUD.setStyle(el, 'overflow-x', 'hidden');
                                YUD.setStyle(el, 'overflow-y', 'auto');
                                // resize
                                if (!true) {
                                    var topRegion = YUD.getRegion('top2');
                                    var height = contentRegion.height - topRegion.height;
                                    var rowCenter = YUD.get('center2');
                                    var rowBottom = YUD.get('bottom2');
                                    var resize = new YAHOO.util.Resize('bottom2', {
                                        handles: ['t']
                                        //, minHeight: 150, maxHeight: height - 150
                                    });
                                    resize.on('resize', function(e) {
                                        var h = e.height;
                                        YUD.setStyle(rowBottom, 'width', '');
                                        YUD.setStyle(rowCenter, 'height', (height - h - 6) + 'px');
                                    });
                                    //resize.resize(null, height * 0.7, 0, 0, 0, true); // ev, h, w, t, l, force, silent
                                }
                                break;
                            }
                        }
                    });
                    // When tab2 is available, update the height..
                    YUE.onContentReady('tab2', function() {
                        YAHOO.jms.resizeWindow();
                        var t = YAHOO.jms.tabView.get('tabs');
                        for (var i = 0; i < t.length; i++) {
                            if (t[i].get('id') == 'tab2') {
                                var el = t[i].get('contentEl');
                                el.id = 'detailHolder';
                                YUD.setStyle(el, 'height', YUD.getStyle(YAHOO.jms.tabView._contentParent, 'height'));
                                YUD.setStyle(el, 'overflow-x', 'hidden');
                                YUD.setStyle(el, 'overflow-y', 'hidden');
                                break;
                            }
                        }
                    });
                    // Listen for the layout resize and call the method
                    YAHOO.jms.layout.on('resize', YAHOO.jms.resizeWindow);

//=======================================================================
// datatable.js
//=======================================================================
                    // custom class formatter
                    YAHOO.widget.DataTable.formatClass = function(elLiner, oRecord, oColumn, oData) {
                        elLiner.title = oData.title;
                        elLiner.parentNode.className += ' ' + oData.className;
                    };
                    // The standard link and email formatters use the same value both for the display text and the underlying href attribute for the link
                    YAHOO.widget.DataTable.formatLink = function(elLiner, oRecord, oColumn, oData) {
                        //if (!oData) return;
                        var title = (oData && oData.title) || oColumn.title;
                        if (oData && oData.indexOf('javascript:') == 0) {
                            var idx = oData.indexOf(';');
                            var href = oData.substr(0, idx + 1); // eg javascript:YAHOO.jms.setup.runProcess('sps_region');
                            var text = oData.substr(idx + 1);
                            var className = '';
                            if (text.indexOf('.') == 0) {
                            	className = text.substr(1);
                            	text = '&#160;&#160;&#160;&#160;';
                            }
                            elLiner.innerHTML = '<a href="' + href + '" class="' + className + '"' + (title ? ' title="' + title + '"' : '') + '>' + text + '</a>';
                        } else {
                            elLiner.innerHTML = '<a href="' + oData + '" target="_blank"' + (title ? ' title="' + title + '"' : '') + '>' + oData + '</a>';
                        }
                        if (oData && oData.className) {
                            elLiner.parentNode.className += ' ' + oData.className;
                        }
                    };
                    // custom tree formatter
                    YAHOO.widget.DataTable.formatTree = function(elLiner, oRecord, oColumn, oData) {
                        var cellEl = elLiner.parentNode;
                        var data = oRecord._oData;
                        var tree = new YAHOO.widget.TreeView(cellEl, [
                            {type: 'text', id: data.id, label: oData, expanded: false}
                        ]);
//                        // read 'loadNodeData' from column definition
//                        var loadNodeData = null;
//                        if (oColumn.loadNodeData == 'YAHOO.jms.findNodeData') {
//                            loadNodeData = YAHOO.jms.findNodeData;
//                        }
//                        tree.setDynamicLoad(loadNodeData, 0);
                        tree.render();
                        //cellEl.className = 'ygtv-checkbox';
                    };
                    // findNodeData tree formatter
                    YAHOO.jms.findNodeData = function(oNode, fnLoadComplete) {
                        var callback = {
                            cache: false,
                            success: function(oResponse) {
                                var response = oResponse.responseText;
                                try {
                                    var data = YAHOO.jms.parseJsonData(response);
                                    for (var i = 0; i < data.length; i++) {
                                        var d = {id: data[i].key, label: data[i].value, title: 'Child action', isLeaf: true};
                                        /*var itemNode = */new YAHOO.widget.TextNode(d, oNode);
                                    }
                                    oResponse.argument.fnLoadComplete();
                                } catch (e) {
                                    YAHOO.jms.alert('Invalid JSON data: ' + e, oResponse);
                                }
                            },
                            failure: function(oResponse) {
                                YAHOO.jms.emptyCallback.failure(oResponse);
                                oResponse.argument.fnLoadComplete();
                            },
                            argument: {
                                'node': oNode,
                                'fnLoadComplete': fnLoadComplete
                            }
                        };
                        if (oNode.data && oNode.data.id) {
                            YAHOO.jms.sendGetRequest('fileAction.do?dispatch=findChildrenActions&actionId=' + oNode.data.id, callback);
                        }
                    };

//=======================================================================
// calendar.js
//=======================================================================
                    // callback function
                    var doDateChanged = function(calendar) {
                        // Beware that this function is called even if the end-user only
                        // changed the month/year.  In order to determine if a date was
                        // clicked you can use the dateClicked property of the calendar:
                        //var date = calendar.date;
                        //var year = date.getFullYear();
                        //var month = date.getMonth();     // integer, 0..11
                        //var day = date.getDate();      // integer, 1..31
                        //alert('doDateChanged: ' + date + ', ' + year + ', ' + month + ', ' + day);
                        if (calendar.dateClicked) {
                            //a date was clicked
                            //redirect to /yyyy/mm/dd/index.php
                            //window.location = "/" + year + "/" + month + "/" + day + "/index.php";
                        } else {
                            var tab = YAHOO.jms.tabView.get('activeTab');
                            var label = tab.get('label');
                            if (label.indexOf('ASE Change Calendar') == 0) {
                                YAHOO.jms.aseChangeCal();
                            } else if (label.indexOf('Job Action Calendar') == 0) {
                                YAHOO.jms.jobActionCal();
                            } else if (label.indexOf('Staff Calendar') == 0) {
                                YAHOO.jms.staffCal();
                            }
                        }
                    };
                    // dateStatus function
                    var doDateStatusFunc = function(date, year, month, day) {
                        calendarYear = year;
                        calendarMonth = month;
                        return false; // other dates are enabled
                        //return true; // we want to disable other dates
                    };
                    Calendar.setup({
                        flat           : 'cal',  // ID of the parent element
                        flatCallback   : doDateChanged,
                        firstDay       : '0', //Sunday
                        dateStatusFunc : doDateStatusFunc
                    });
                    // Handle the click event on the cal box at the bottom
                    YUE.on('aseChangeCal', 'click', function(ev) {
                        YUE.stopEvent(ev);
                        YAHOO.jms.aseChangeCal();
                    });
                    YUE.on('jobActionCal', 'click', function(ev) {
                        YUE.stopEvent(ev);
                        YAHOO.jms.jobActionCal();
                    });
                    YUE.on('staffCal', 'click', function(ev) {
                        YUE.stopEvent(ev);
                        YAHOO.jms.staffCal();
                    });

//=======================================================================
// edit.js
//=======================================================================
                    // Define various event handlers for Dialog
                    var handleSubmit = function() {
                        YAHOO.jms.updateEditorInput();
                        //add readOnly parameter to mark transaction for commit (@see TransactionFilter)
                        var submitAction = this.form.action;
                        if (submitAction.indexOf('/oauth/') > 0) {

                        } else {
                            submitAction += (submitAction.indexOf('?') > 0 ? '&' : '?') + 'readOnly=false';
                        }
                        // oForm, requestAction, requestScript, requestTitle, oCallback
                        YAHOO.jms.sendPostRequest(this.form, submitAction, null, null, YAHOO.jms.submitCallback, null, null);
                    };
                    var handleCancel = function() {
                        //this.hide();
                        this.cancel();
                    };
                    //Create a SimpleDialog used to mimic an OS dialog
                    var buttons = [];
                    if (handleSubmit) {
                        buttons.push({text: 'Submit', isDefault: true, handler: handleSubmit});
                    }
                    buttons.push({text: 'Cancel', handler: handleCancel});
                    YAHOO.jms.editPanel = new YAHOO.widget.SimpleDialog('editDiv', {
                        close: false,
                        draggable: true,
                        fixedcenter: true,
                        visible: false,
                        modal: true,
                        width: '500px',
                        height: '400px',
                        autofillheight: 'body',
                        constraintoviewport: true,
                        context: ['showbtn', 'tl', 'bl'],
                        //icon: YAHOO.widget.SimpleDialog.ICON_INFO,
                        buttons: buttons
                    });
                    //will hide a Panel when the escape key is pressed
                    var escapeKey = new YAHOO.util.KeyListener(document, { keys: 27 },
                        { fn: handleCancel, scope: YAHOO.jms.editPanel, correctScope: true } );
                    YAHOO.jms.editPanel.cfg.queueProperty('keylisteners', escapeKey);
                    YAHOO.jms.editPanel.subscribe('render', function (oType, oArgs) {
                        //resizable by the right side (t, r, b, l, tr, tl, br, bl) or (all)
                        var resize = new YAHOO.util.Resize('editDiv', {
                            handles: ['br'], //Defaults to: ['r', 'b', 'br']
                            //knobHandles: true,
                            autoRatio: false,
                            minWidth: 300,
                            minHeight: 100,
                            status: false
                        });
                        /*
                        Dragging the handle will now resize the outer containing DIV of the panel.
                        Since we want to keep the contents of the panel in sync with the new dimensions of the containing DIV,
                        we listen for the resize event fired by the Resize instance.
                        In the listener, we set the Panel's height configuration property to match the new pixel height of the containing DIV.
                        This will result in the body element, which we specified in the autofillheight property for the Panel,
                        being resized to fill out the height of the containing DIV. The width is handled automatically by the browser,
                        with the header, body and footer DIVs filling out their containing element.
                        Setting the height configuration property, will also result in the iframe shim and shadow being resized
                        to match the new dimensions of the containing DIV if required for the browser (IE6 and IE7 quirks mode).
                         */
                        resize.on('resize', function(args) {
                            var panelHeight = args.height;
                            this.cfg.setProperty('height', panelHeight + 'px');
                        }, YAHOO.jms.editPanel, true);
                        /*
                         We also setup a listener for the startResize event, which we use to setup the constraints
                         for the height and widthof the resized element, if the panel's constraintoviewport value is set to true.
                         */
                        // Setup startResize handler, to constrain the resize width/height
                        // if the constraintoviewport configuration property is enabled.
                        resize.on('startResize', function(args) {
                            if (this.cfg.getProperty('constraintoviewport')) {
                                var clientRegion = YUD.getClientRegion();
                                var elRegion = YUD.getRegion(this.element);
                                resize.set('maxWidth', clientRegion.right - elRegion.left - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                                resize.set('maxHeight', clientRegion.bottom - elRegion.top - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                            } else {
                                resize.set('maxWidth', null);
                                resize.set('maxHeight', null);
                            }
                        }, YAHOO.jms.editPanel, true);
                    });
                    YAHOO.jms.editPanel.setHeader('Edit');
                    YAHOO.jms.editPanel.setBody('Notta');
                    YAHOO.jms.editPanel.render(document.body);

//=======================================================================
// help.js
//=======================================================================
                    var handleClose = function() {
                        //this.hide();
                        this.cancel();
                    };
                    // Create a SimpleDialog used to mimic an OS dialog
                    YAHOO.jms.helpPanel = new YAHOO.widget.SimpleDialog('helpDiv', {
                        close: false,
                        draggable: true,
                        fixedcenter: true,
                        visible: false,
                        modal: true,
                        width: '500px',
                        height: '400px',
                        autofillheight: 'body',
                        constraintoviewport: true,
                        context: ['showbtn', 'tl', 'bl'],
                        //icon: YAHOO.widget.SimpleDialog.ICON_INFO,
                        buttons: [
                           { text: 'Close', handler: handleClose }
                        ]
                    });
                    // will hide a Panel when the escape key is pressed
                    var escapeKey = new YAHOO.util.KeyListener(document, { keys: 27 },
                        { fn: handleClose, scope: YAHOO.jms.helpPanel, correctScope: true } );
                    YAHOO.jms.helpPanel.cfg.queueProperty('keylisteners', escapeKey);
                    YAHOO.jms.helpPanel.subscribe('render', function (oType, oArgs) {
                        // resizable by the right side (t, r, b, l, tr, tl, br, bl) or (all)
                        var resize = new YAHOO.util.Resize('helpDiv', {
                            handles: ['br'], //Defaults to: ['r', 'b', 'br']
                            //knobHandles: true,
                            autoRatio: false,
                            minWidth: 300,
                            minHeight: 100,
                            status: false
                        });
                        /*
                        Dragging the handle will now resize the outer containing DIV of the panel.
                        Since we want to keep the contents of the panel in sync with the new dimensions of the containing DIV,
                        we listen for the resize event fired by the Resize instance.
                        In the listener, we set the Panel's height configuration property to match the new pixel height of the containing DIV.
                        This will result in the body element, which we specified in the autofillheight property for the Panel,
                        being resized to fill out the height of the containing DIV. The width is handled automatically by the browser,
                        with the header, body and footer DIVs filling out their containing element.
                        Setting the height configuration property, will also result in the iframe shim and shadow being resized
                        to match the new dimensions of the containing DIV if required for the browser (IE6 and IE7 quirks mode).
                         */
                        resize.on('resize', function(args) {
                            var panelHeight = args.height;
                            this.cfg.setProperty('height', panelHeight + 'px');
                        }, YAHOO.jms.helpPanel, true);
                        /*
                         We also setup a listener for the startResize event, which we use to setup the constraints
                         for the height and widthof the resized element, if the panel's constraintoviewport value is set to true.
                         */
                        // Setup startResize handler, to constrain the resize width/height
                        // if the constraintoviewport configuration property is enabled.
                        resize.on('startResize', function(args) {
                            if (this.cfg.getProperty('constraintoviewport')) {
                                var clientRegion = YUD.getClientRegion();
                                var elRegion = YUD.getRegion(this.element);
                                resize.set('maxWidth', clientRegion.right - elRegion.left - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                                resize.set('maxHeight', clientRegion.bottom - elRegion.top - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                            } else {
                                resize.set('maxWidth', null);
                                resize.set('maxHeight', null);
                            }
                        }, YAHOO.jms.helpPanel, true);
                    });
                    YAHOO.jms.helpPanel.setHeader('Help');
                    YAHOO.jms.helpPanel.setBody('Notta');
                    YAHOO.jms.helpPanel.render(document.body);

//=======================================================================
// history.js
//=======================================================================
                    // Register our only module. Module registration MUST take place
                    // BEFORE calling initializing the browser history management library!
                    // If there is no bookmarked state, assign the default state:
                    var firstMenu = YAHOO.jms.getFirstMenu();
                    var initialState = YUH.getBookmarkedState(YAHOO.jms.module)
                        || YUH.getQueryStringParameter('state')
                        || YAHOO.jms.currentURL
                        || firstMenu.getAttribute('href') + '&title=' + firstMenu.title
                        ;
                    //
                    YUH.register(YAHOO.jms.module, initialState, function (oState) {
                        // This is called after calling YUH.navigate, or after the user has triggered the back/forward button.
                        // We cannot distinguish between these two situations.
                        YAHOO.jms.loadState(oState); // the earliest history entry
                    });
                    // Use the Browser History Manager onReady method to initialize the application.
                    YUH.onReady(function () {
                        YAHOO.jms.reload();
                    });
                    // Initialize the browser history management library.
                    YUH.initialize('yui-history-field', 'yui-history-iframe');
                }, 0);
                //if (debug) this.getUnitByPosition('right').collapse(); //LOG
                YUD.setStyle(document.body, 'visibility', 'visible');
            });

            // Render the layout
            YAHOO.jms.layout.render();

            // Initialize and render the Menu when its elements are ready to be scripted.
            YUE.onContentReady('mainMenu', function() {
                var oMenu = new YAHOO.widget.Menu('mainMenu', {
                    position: 'static',
                    hidedelay: 750,
                    lazyload: true
                });
                oMenu.render();
            });

            // Setup the click listeners on the folder list
            YUE.on('mainMenu', 'click', function(ev) {
                //YAHOO.log('mainMenu clicked: ' + ev, 'info', 'default.js');
                var tar = YUE.getTarget(ev);
                if (tar.tagName.toLowerCase() != 'a') {
                    tar = tar.parentNode;
                    if (tar.tagName.toLowerCase() != 'a') {
                        return;
                    }
                }
                // Make sure we are a link in the list's
                if (YUS.test(tar, '#mainMenu li a')) {
                    var url = tar.href;
                    if (!url || url == 'javascript:;') {
                        return;
                    }
                    YUE.stopEvent(ev);
                    //
                    var title = tar.title; // innerHTML;
                    var tabs = YAHOO.jms.tabView.get('tabs');
                    for (var i = 0; i < tabs.length; i++) {
                        var tab = tabs[i];
                        var tabId = tab.get('id');
                        if (tabId == 'tab1') {
                            tab.set('label', title);
                            // to avoid refresh
                            if (YAHOO.jms.currentURL != url) {
                                YAHOO.jms.currentURL = url;
                                YAHOO.jms.navigate(url, title); // via browser history manager
                                YAHOO.jms.tabView.set('activeTab', tab);
                            }
                        }
                    }
                }
            });
        }
        //Configure the Get utility to timeout after 10 seconds for any given node insert
        //, timeout: 10000,
    });
    loader.insert();
})();