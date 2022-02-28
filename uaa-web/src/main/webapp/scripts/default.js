YAHOO.namespace("jms");

var YL = YAHOO.lang,
    YUC = YAHOO.util.Connect,
    YUD = YAHOO.util.Dom,
    YUE = YAHOO.util.Event,
    YUG = YAHOO.util.Get,
    YUH = YAHOO.util.History,
    YUS = YAHOO.util.Selector;

var debug = false;

var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
    calendarYear = null,
    calendarMonth = null;

(function() {
    YAHOO.jms = {
        module: 'uaaModule',
        form: 'dataForm',
        //
        tabView: null,
        tabViewActiveIndex: 'YAHOO.jms.tabView.activeIndex',
        tab1: null,
        tab1Prefix: '<span class="welcome">&#160;</span>',
        //
        alertPanel: null,
        editPanel: null,
        editorInput: null,
        //
        currentURL: 'about.do',
        progressStart: function() {
            YUD.addClass(YAHOO.jms.tabView._tabParent, 'loading');            
        },
        progressStop: function() {
            YUD.removeClass(YAHOO.jms.tabView._tabParent, 'loading');            
        },
        /**
         * 
         */
        toggleClass: function(el, selectedClass) {
            if (YUD.hasClass(el, selectedClass)) {
                YUD.removeClass(el, selectedClass);
            } else {
                YUD.addClass(el, selectedClass);
            }
        },
        /**
         * el <String | HTMLElement | Array> The element or collection to remove the class from 
         * className <String> the class name to remove from the class attribute 
         */
        removeClass: function(el, className) {
            if (YUD.hasClass(el, className)) {
                YUD.removeClass(el, className);
            }
        },
        /**
         * el <String | HTMLElement | Array> The element or collection to add the class to 
         * className <String> the class name to add to the class attribute 
         */
        addClass: function(el, className) {
            if (!YUD.hasClass(el, className)) {
                YUD.addClass(el, className);
            }
        },
        /**
         * 
         */
        toggleInit: function(root, onclickHandler) {
            var toggleNodes = YAHOO.util.Selector.query('span[id^=toggle.]', root);
            YAHOO.util.Event.removeListener(toggleNodes, 'click');
            if (!onclickHandler) {
                onclickHandler = function(e) {
                    var toggleNode = YAHOO.util.Event.getTarget(e);
                    YAHOO.jms.toggle(toggleNode);
                };
            }
            YAHOO.util.Event.addListener(toggleNodes, 'click', onclickHandler);
            return toggleNodes;
        },
        toggle: function(toggleNode, collapse) {
            if (!toggleNode) {
                return;
            }
            collapse = !!collapse;
            var containerNode = YUD.get(toggleNode.id + '.container');
            if (!collapse && YUD.hasClass(toggleNode, 'collapsed')) {
                YUD.removeClass(toggleNode, 'collapsed');
                YUD.setStyle(containerNode, 'display', 'block');
            } else {
                YUD.addClass(toggleNode, 'collapsed');
                YUD.setStyle(containerNode, 'display', 'none');
            }
        },
        /**
         * 
         */
        disableElement: function(el, disable) {
            el = YUD.get(el);
            if (!el) {
                return;
            }
            if (el.tagName.toLowerCase() == 'input') {
                if (disable) {
                    YUD.setAttribute(el, 'disabled', 'disabled');
                } else {
                    YUD.get(el).removeAttribute('disabled');
                }
            } else {
                if (disable) {
                    YUD.addClass(el, 'hidden');
                    //YUD.setStyle(el, 'display', 'none');
                } else {
                    YUD.removeClass(el, 'hidden');
                    //YUD.setStyle(el, 'display', 'block');
                }
            }
        },
        disableElements: function(els, disable) {
            for (var i = 0; i < els.length; i++) {
                YAHOO.jms.disableElement(els[i], disable);
            }
        },
        /**
         * parse JSON string - replace unsafe characters
         */
        //YAHOO.lang.JSON.useNativeParse = false; // Default Value: true
        parseJsonData: function(jsonData) {
            if (!jsonData) {
                return null;
            }
            //encode Back Slashes
            //jsonData = jsonData.replace(/\\/g, '\\\\');
            //parse
            return YAHOO.lang.JSON.parse(jsonData);
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
                        script.parentNode.removeChild(script);
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
        /**
         * @param root - root element (optional)
         */
        hideErrors: function(root) {
            var errorRows = YAHOO.util.Selector.query('tr[class=error]', root);
            for (var i = 0; i != errorRows.length; i++) {
                var trEl = errorRows[i];
                YUD.replaceClass(trEl, 'error', 'error-hidden');
                for (var j = 0; j < trEl.children.length; j++) {
                    var tdEl = trEl.children[j];
                    tdEl.innerHTML = '';
                }
            }
        },
        /**
         * @param errors - json errors
         * @param root - root element (optional)
         */
        showErrors: function(errors, root) {
            YAHOO.jms.hideErrors(root);
            var result = false;
            for (var i = 0; i != errors.length; i++) {
                var error = errors[i];
                var id = !error.name ? 'errors' : ('errors.' + error.name);
                var message = error.message;
                var tdEl = YUD.get(id);
                if (tdEl) {
                    tdEl.innerHTML = message;
                    var trEl = tdEl.parentNode;
                    YUD.replaceClass(trEl, 'error-hidden', 'error');
                    result = true;
                } else {
                    // create new error row
                    var errorRows = YAHOO.util.Selector.query('tr[class=error-hidden]', root);
                    if (errorRows.length != 0) {
                        var cloneRow = errorRows[0]; // first row
                        var trEl = cloneRow.cloneNode(true);
                        var tdEl = YAHOO.util.Selector.query('td', trEl)[0];
                        tdEl.setAttribute('id', id);
                        tdEl.innerHTML = message;
                        YUD.replaceClass(trEl, 'error-hidden', 'error');
                        YUD.insertAfter(trEl, cloneRow);
                        result = true;
                    }
                }
            }
            return result;
        },
        /**
         * Validate integer input
         */
        inputInteger: function(oEvent) {
            return YAHOO.jms.inputNumber(oEvent, false);    
        },
        /**
         * Validate decimal input
         */
        inputDecimal: function(oEvent) {
            return YAHOO.jms.inputNumber(oEvent, true);
        },
        /**
         * Validate number input
         */
        inputNumber: function(oEvent, decimals, length) {
            var input = YAHOO.util.Event.getTarget(oEvent);
            if (!length) {
                length = 64;
            }
            var which = oEvent.which;
            var key = oEvent.keyCode;
            if (typeof(which) == 'undefined') {
                which = key;
                key = 0;
            }
            // delete or backspace
            if ((which == 0 && key == 46) || (which == 8 && key == 8)) {
                return true;
            }
            // 45='-' minus (TODO: should be first char)
            if (which == 45) {
                return true;
            }
            // 46='.' dot
            if (which == 46 && input.value.indexOf('.') == -1 && decimals && input.value.length < length) {
                return true;
            }
            // 8=backspace, 9=tab, 13=CR, 27=
            if (!((which >= 48 && which <= 57 && input.value.length < length) || oEvent.ctrlKey || (key == 8 || key == 9 || key == 13))) {
                oEvent.returnValue = false;
                return false;
            }
            return true;
        },
        failure: function(oResponse) {
            YAHOO.jms.alert('Failure: ', oResponse);
        },
        emptyCallback: {
            cache:false,
            success: function(oResponse) {
                // do nothing
            },
            failure: function(oResponse) {
                try {
                    var data = YAHOO.jms.parseJsonData(oResponse.responseText);
                    if (data && YAHOO.lang.isArray(data.errors)) {
                        YAHOO.jms.alert(data.errors[0].message);
                    } else {
                        YAHOO.jms.failure(oResponse);
                    }
                } catch (e) {
                    YAHOO.jms.alert(e.message, oResponse);
                }
            }
        },
        sendGetRequest: function(action, callback) {
            YAHOO.util.Connect.asyncRequest('GET', action, callback); 
        },
        sendPostRequest: function(form, action, callback, postdata) {
            YAHOO.util.Connect.setDefaultPostHeader(true);
            //YAHOO.util.Connect.resetDefaultHeaders();
            if (form) {
                //handle multi-part
                if (YAHOO.jms.isMultipart(form)) {
                    //YAHOO.log('File Upload: ' + file.value, 'info', 'edit.js');
                    // http://developer.yahoo.com/yui/connection/#file
                    // the second argument is true to indicate file upload
                    // the third argument is set true to have Connection Manager set the iframe source to 'javascript:false'
                    YAHOO.util.Connect.setForm(form, true, true);
                } else {
                    YAHOO.util.Connect.setForm(form);
                }
            } else if (postdata) {
                if (YAHOO.lang.isObject(postdata)) {
                    postdata = YAHOO.lang.JSON.stringify(postdata);
                    YAHOO.util.Connect.setDefaultPostHeader(false);
                    //YAHOO.util.Connect.initHeader('Accept', 'application/json');
                    YAHOO.util.Connect.initHeader('Content-Type', 'application/json;charset=UTF-8');
                }
            }
            //YAHOO.util.Connect.setForm(form, true, true); // TODO: multipart
            YAHOO.util.Connect.asyncRequest('POST', action, callback, postdata); 
        },
        isMultipart: function(form) {
            var multipart = YAHOO.util.Selector.test(form, 'form[enctype=multipart/form-data]');
            if (!multipart) {
                return false;
            }
            var files = YAHOO.util.Selector.query('input[type=file][id^=attachment]', null);
            var file = files && files.length != 0 ? files[0] : null;
            return file && file.value;
        }
    };

//=======================================================================
// datatable.js
//=======================================================================
    // custom formatter(s)
    YAHOO.widget.DataTable.formatHidden = function(elLiner, oRecord, oColumn, oData) {
        var hiddenName = oColumn.key + (oData.index >= 0 ? ('[' + oData.index + ']') : '');
        var hiddenId = oColumn.key + oRecord.getId();
        elLiner.innerHTML =
              //oColumn.key + '[' + oData.index + ']=' + oData.value + // debug
              '<input type="hidden"'
            + ' name="' + hiddenName + '"'
            + ' value="' + oData.value + '"'
            + ' id="' + hiddenId + '" />';
    };
    // generic number formatter
    YAHOO.widget.DataTable.formatNumber = function(elLiner, oRecord, oColumn, oData) {
        var innerHTML = '';
        if (oData && oData.id) {
            var hiddenName = oColumn.name + 'Id';
            var hiddenId = hiddenName + oData.id;
            if (oData.editable) {
                innerHTML += '<input type="hidden" id="' + hiddenId + '" value="' + oData.id + '" name="' + hiddenName + '" />';
            }
            if (oData.value) {
                if (oData.editable) {
                    var inputName = oColumn.name + 'Value';
                    var inputId = inputName + oData.id;
                    innerHTML += '<input type="text" id="' + inputId + '" value="' + oData.value + '" name="' + inputName + '"'
                        + ' class="width95 number small" onkeypress="return YAHOO.jms.inputDecimal(event);" />';
                } else {
                    innerHTML += '<span class="float-right">' + oData.value + '</span>';
                }
            }
        }
        elLiner.innerHTML = innerHTML;
    };
    // formatCheckbox
    YAHOO.widget.DataTable.formatCheckbox = function(elLiner, oRecord, oColumn, oData) {
        if (oData == true || oData == false) {
            var bChecked = oData == true ? ' checked="checked"' : '';
            elLiner.innerHTML = '<input type="checkbox"' + bChecked
                + ' class="' + YAHOO.widget.DataTable.CLASS_CHECKBOX + '" />';
        }
        else if (oData && oData.value) {
            var inputName = oColumn.name + 'Id';
            var inputId = oData.id;
            elLiner.innerHTML = '<input type="checkbox" class="checkbox" value="' + oData.value + '"'
                + (oColumn.name ? ' name="' + inputName + '"' : '')
                + (oData.disabled ? ' disabled="disabled"' : '')
                + (oData.checked ? ' checked="checked"' : '')
                + (oData.id ? ' id="' + inputId + '"' : '')
                + ' /> '
                + (oData.label ? oData.label : '');
        }
    };
    // The standard link and email formatters use the same value both for the display text and the underlying href attribute for the link
    YAHOO.widget.DataTable.formatLink = function(elLiner, oRecord, oColumn, oData) {
        //if (!oData) return;
        if (oData && oData.indexOf('javascript:') == 0) {
            var idx = oData.indexOf(';');
            elLiner.innerHTML = '<a href="' + oData.substr(0, idx + 1) + '">' + oData.substr(idx + 1) + '</a>';
        } else {
            elLiner.innerHTML = '<a href="' + oData + '" target="_blank">' + oData + '</a>';
        }
    };
    // custom class formatter
    YAHOO.widget.DataTable.formatClass = function(elLiner, oRecord, oColumn, oData) {
        if (oData == true) {
            elLiner.innerHTML = '<em class="' + oColumn.key + '"/>';
        }
    };
    // Custom formatter for column to preserve line breaks
    YAHOO.widget.DataTable.formatTextarea = function(elCell, oRecord, oColumn, oData) {
        elCell.innerHTML = '<pre>' + oData + '</pre>';
    };
    YAHOO.jms.updateColumnFormatters = function(oColumnDefs) {
        if (!oColumnDefs) {
            return;
        }
        var hasLink = false;
        // Update the Column Definitions (pass as string in find.jsp)
        for (var c = 0; c < oColumnDefs.length; c++) {
            var column = oColumnDefs[c];
            if (column.formatter) {
                hasLink = hasLink || YAHOO.jms.updateColumnFormatter(column);
            } else if (column.children) { // nested columns TODO: while
                for (var c2 = 0; c2 < column.children.length; c2++) {
                    var columnNested = column.children[c2];
                    if (columnNested.formatter) {
                        hasLink = hasLink || YAHOO.jms.updateColumnFormatter(columnNested);
                    }
                }
            }
        }
        return hasLink;
    };
    YAHOO.jms.updateColumnFormatter = function(oColumn) {
        var hasLink = false;
        if (oColumn.formatter == 'YAHOO.widget.DataTable.formatLink' || oColumn.formatter == 'link') {
            oColumn.formatter = YAHOO.widget.DataTable.formatLink;
            hasLink = true;
        } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatHidden' || oColumn.formatter == 'hidden') {
            oColumn.formatter = YAHOO.widget.DataTable.formatHidden;
        } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatTextarea' || oColumn.formatter == 'textarea') {
            oColumn.formatter = YAHOO.widget.DataTable.formatTextarea;
        } else if (oColumn.formatter == 'YAHOO.widget.DataTable.formatCheckbox' || oColumn.formatter == 'checkbox') {
            oColumn.formatter = YAHOO.widget.DataTable.formatCheckbox;
        } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatRadio' || oColumn.formatter == 'radio') {
            oColumn.formatter = YAHOO.widget.DataTable.formatRadio;
        } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatNumber' || oColumn.formatter == 'number') {
            oColumn.formatter = YAHOO.widget.DataTable.formatNumber;
        } else  if (oColumn.formatter == 'YAHOO.widget.DataTable.formatClass' || oColumn.formatter == 'class') {
            oColumn.formatter = YAHOO.widget.DataTable.formatClass;
        }
        return hasLink;
    };
    YAHOO.jms.updateColumnEditors = function(oColumnDefs) {
        if (!oColumnDefs) {
            return;
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
    };
    YAHOO.jms.updateColumnEditor = function(oColumn) {
        if (oColumn.editor == 'YAHOO.widget.DropdownCellEditor') {
            oColumn.editor = new YAHOO.widget.DropdownCellEditor({dropdownOptions:oColumn.dropdownOptions,disableBtns:!!oColumn.disableBtns});
        } else if (oColumn.editor == 'YAHOO.widget.TextboxCellEditor') {
            oColumn.editor = new YAHOO.widget.TextboxCellEditor({disableBtns:false});
        } else if (oColumn.editor == 'YAHOO.widget.TextareaCellEditor') {
            oColumn.editor = new YAHOO.widget.TextareaCellEditor({disableBtns:false});
        }
        oColumn.editor.key = oColumn.key;
    };
    /**
     * oContainer  <HTMLElement> Container element for the TABLE
     * oData                      data
     */
    YAHOO.jms.createStaticDataTable = function(oContainer, oData) {
        // dataSource
        var dataSource = new YAHOO.util.DataSource(oData.records, {
            responseType: YAHOO.util.XHRDataSource.TYPE_JSARRAY,
            responseSchema: {fields: oData.fields}
        });
        // custom formatter(s)
        var columnDefs = oData.columns;
        var hasLink = YAHOO.jms.updateColumnFormatters(columnDefs);
        var hasEditable = YAHOO.jms.updateColumnEditors(columnDefs);
        // dataTable
        var dataTable = new YAHOO.widget.DataTable(oContainer, columnDefs, dataSource);
        //var dataTable = new YAHOO.widget.ScrollingDataTable(oContainer, columnDefs, dataSource);
        //
        return dataTable;
    };
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
    YAHOO.jms.createDynamicDataTable = function(oContainer, oLiveData, oColumnDefs, oForm, oConfig, oHeight, oWidth) {
        var oFields = [];
        for (var i = 0; i < oColumnDefs.length; i++) {
            oFields.push(oColumnDefs[i].key);
        }
        var hasLink = YAHOO.jms.updateColumnFormatters(oColumnDefs);
        var hasEditable = YAHOO.jms.updateColumnEditors(oColumnDefs);
        // Create DataSource
        var dataSource = new YAHOO.util.XHRDataSource(oLiveData, {
            connXhrMode: 'queueRequests', //If a request is already in progress, wait until response is returned before sending the next request.
            connMethodPost: !!oForm, //POST
            maxCacheEntries: 0, //Set to 0 to turn off caching
            responseType: YAHOO.util.XHRDataSource.TYPE_JSON,
            responseSchema: {
                resultsList: 'records',
                fields: oFields,
                metaFields: oConfig ? oConfig.metaFields : {}
            }
        });
        var requestBuilder = function(oState, oDataTable) {
            if (oForm) {
                YAHOO.util.Connect.setForm(oForm);
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
        // Set up editing flow
        var highlightEditableCell = function(oArgs) {
            var elCell = oArgs.target;
            if (YUD.hasClass(elCell, 'yui-dt-editable')) {
                this.highlightCell(elCell);
            }
        };
        var highlightCell = function(oArgs) {
            var elCell = oArgs.target;
            if (YUD.hasClass(elCell, 'yui-dt-highlighted')) {
                this.unhighlightCell(elCell);
            } else {
                this.highlightCell(elCell);
            }
        };
        if (hasEditable) {
            dataTable.subscribe('cellMouseoverEvent', highlightEditableCell);
            dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
            dataTable.subscribe('cellClickEvent', dataTable.onEventShowCellEditor);
        } else if (oConfig && oConfig.cellSelect == true) {
            dataTable.subscribe('cellMouseoverEvent', dataTable.onEventHighlightCell);
            dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
            dataTable.subscribe('cellClickEvent', highlightCell);
            //dataTable.subscribe('cellClickEvent', dataTable.onEventSelectCell);
            //dataTable.subscribe('cellSelectEvent', dataTable.clearTextSelection);
        } else {
            dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
            dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
            dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
        }
        //
        //dataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
        //    dataTable.validateColumnWidths(null);
        //    return oPayload;
        //}
        //
        return dataTable;
    };
    /**
     * @param oDataTable
     * @param insertRows (boolean) - This method will insert new rows at the beginning of the DataTable,
     *     preserving the existing data and regardless of any sort order.
     *     The insertion index for the added records can be assigned to when sending the request to the DataSource,
     *     or by accessing oPayload.insertIndex with the doBeforeLoadData() method at runtime.
     *     If applicable, creates or updates corresponding TR elements.
     * @param oRequest - Request object
     */
    YAHOO.jms.filterDataTable = function(oDataTable, insertRows, oRequest) {
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
    };

    /**
     * Call loader the first time
     */
    var loader = new YAHOO.util.YUILoader({
//        //Allows you to specify a different location (as a full or relative filepath) for the YUI build directory.
//        base: 'yui/',
//        require: ['animation','autocomplete','element','get','history','connection','button','container','cookie','datatable','datasource','element','editor','get','json','logger','layout','menu','paginator','reset-fonts-grids','resize','selector','tabview','utilities'],
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
            }

            /*
             * Subscribe to global listeners
             * var oResponse = args[0];
             */
            var globalEvents = {
                start: function(type, args){
                    YAHOO.jms.progressStart();
                },
                complete: function(type, args){
                    YAHOO.jms.progressStop();
                },
                success: function(type, args){
                    YAHOO.jms.progressStop();
                },
                failure: function(type, args){
                    YAHOO.jms.progressStop();
                },
                abort: function(type, args){
                    YAHOO.jms.progressStop();
                }
            };
            YAHOO.util.Connect.startEvent.subscribe(globalEvents.start);
            YAHOO.util.Connect.completeEvent.subscribe(globalEvents.complete);
            YAHOO.util.Connect.successEvent.subscribe(globalEvents.success);
            YAHOO.util.Connect.failureEvent.subscribe(globalEvents.failure);
            YAHOO.util.Connect.abortEvent.subscribe(globalEvents.abort);

            //Load the global CSS files.
            YAHOO.util.Get.css('styles/default.css');

//=======================================================================
// layout.js
//=======================================================================
            YAHOO.jms.layout = new YAHOO.widget.Layout({
                minWidth: 1000,
                units: [
                    { position: 'top', height: 45, resize: false, body: 'header' },
                    debug ? { position: 'right', width: 200, body: '', header: 'Console', collapse: true } : {}, //LOG
                    { position: 'left', width: 200, resize: false, body: 'leftNav', gutter: '0px 2px 0px 5px' },
                    { position: 'center', gutter: '0px 2px 0px 2px' }
                ]
            });

            //On render, load tabview.js, button.js etc
            YAHOO.jms.layout.on('render', function() {
                window.setTimeout(function() {
                    if (debug) YAHOO.util.Get.script('scripts/logger.js'); //LOG
                    
//=======================================================================
// history.js
//=======================================================================
                    // Update the UI of jms module according to the 'state' parameter
                    function loadState(oState) {
                        // update title (last parameter)
                        var idx = oState.indexOf('title=');
                        var title = idx < 0 ? '' : oState.substring(idx + 6);
                        document.title = title;
                        //check for callback (not default)
                        var callback = oState.callback ? oState.callback : YAHOO.jms.viewCallback;
                        // first request to load page is always GET
                        YAHOO.jms.sendGetRequest(oState, callback);
                    };
            
                    // Register our only module. Module registration MUST take place
                    // BEFORE calling initializing the browser history management library!
                    // If there is no bookmarked state, assign the default state:
                    var initialState = YAHOO.util.History.getBookmarkedState(YAHOO.jms.module)
                        || YAHOO.util.History.getQueryStringParameter('state')
                        || YAHOO.jms.currentURL
                        ;
                    YAHOO.util.History.register(YAHOO.jms.module, initialState, function (oState) {
                        // This is called after calling YAHOO.util.History.navigate, or after the user has triggered the back/forward button.
                        // We cannot distinguish between these two situations.
                        loadState(oState); // the earliest history entry
                    });
            
                    // Use the Browser History Manager onReady method to initialize the application.
                    YAHOO.util.History.onReady(function () {
                        var currentState = YAHOO.util.History.getCurrentState(YAHOO.jms.module);
                        loadState(currentState); // the latest history entry
                    });
            
                    // Initialize the browser history management library.
                    YAHOO.util.History.initialize('yui-history-field', 'yui-history-iframe');
            
                    /**
                     * 
                     */
                    YAHOO.jms.navigate = function(oAction, oTitle) {
                        var state = YAHOO.util.History.getQueryStringParameter('state', oAction) || oAction;
                        state += (state.indexOf('?') < 0 ? '?' : '&') + 'title=' + oTitle;
                        YAHOO.util.History.navigate(YAHOO.jms.module, state);
                    };

//=======================================================================
// tabview.js
//=======================================================================
                    // create tabView
                    YAHOO.jms.createTabView = function(oElem, oConfig) {
                        if (YAHOO.jms.tabView) {
                            YAHOO.jms.tabView.destroy();
                            YAHOO.jms.tabView = null;
                        }
                        YAHOO.jms.tabView = new YAHOO.widget.TabView(oElem);
                        // any aditional configuration
                        if (oConfig) {
                            if (YL.isFunction(oConfig.onActiveIndexChange)) {
                                YAHOO.jms.tabView.on('activeIndexChange', function(e) {
                                    YAHOO.util.Cookie.set(YAHOO.jms.tabViewActiveIndex, '' + e.newValue);
                                    oConfig.onActiveIndexChange(e);
                                });
                            }
                        }
                    };
                    // initialise tab
                    YAHOO.jms.onAvailableTab = function(oTab, oTabIndex, oConfig) {
                        YAHOO.util.Event.onContentReady(oTab, function() {
                            YAHOO.jms.resizeTabView();
                            var tab = YAHOO.jms.tabView.getTab(oTabIndex);
                            //var el = tab.get('contentEl');
                            // any aditional configuration
                            if (oConfig) {
                                if (oConfig.dataSrc) {
                                    tab.set('dataSrc', oConfig.dataSrc);
                                    tab.set('cacheData', oConfig.cacheData === false ? false : true);
                                }
                                if (YL.isFunction(oConfig.initHandler)) {
                                    oConfig.initHandler();
                                }
                            }
                            var activeIndex = YAHOO.util.Cookie.get(YAHOO.jms.tabViewActiveIndex);
                            activeIndex = !activeIndex || isNaN(activeIndex) ? 0 : parseInt(activeIndex);
                            if (activeIndex == oTabIndex) {
                                YAHOO.jms.tabView.selectTab(activeIndex);
                            }
                        });
                    };
                    // resize the tabview method
                    YAHOO.jms.resizeTabView = function() {
                        var ul = YAHOO.jms.tabView._tabParent.offsetHeight;
                        YUD.setStyle(YAHOO.jms.tabView._contentParent, 'height', ((YAHOO.jms.layout.getSizes().center.h - ul) - 2) + 'px');
                    };
                    // Listen for the layout resize and call the method
                    YAHOO.jms.layout.on('resize', YAHOO.jms.resizeTabView);

//                    //Create the tabView
//                    YAHOO.jms.tabView = new YAHOO.widget.TabView();
//                    //Add the tabview to the center unit of the main layout
//                    var elCenterWrap = YAHOO.jms.layout.getUnitByPosition('center').get('wrap');
//                    YAHOO.jms.tabView.appendTo(elCenterWrap);
//                    YAHOO.jms.toggleInit(elCenterWrap);
//                    //resize the TabView
//                    YAHOO.jms.resizeTabView();

//=======================================================================
// buttons.js
//=======================================================================
                    var searchButton = new YAHOO.widget.Button('search');
                    searchButton.on('click', function() {
                        var q = YUD.get('query').value;
                        if (q !== 'Search the Web..') {
                            //window.open('http://search.yahoo.com/search?p=' + q);
                            window.open('http://www.google.com.au/search?hl=en&q=' + q);
                        }
                    });
                    YAHOO.util.Event.on('query', 'click', function() {
                        this.value = '';
                    });
                    YAHOO.util.Event.on('query', 'blur', function() {
                        if (this.value === '') {
                            this.value = 'Search the Web..';
                        }
                    });

//=======================================================================
// calendar.js
//=======================================================================
                    //callback function
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
                            }
                        }
                    };
                    //dateStatus function
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
                    //
                    YAHOO.jms.aseChangeCal = function() {
                        YAHOO.jms.currentURL = 'file/calendarAseChange.do?dispatch=find&year=' + calendarYear + '&month=' + calendarMonth;
                        var tab = YAHOO.jms.tab1;
                        tab.set('label', 'ASE Change Calendar ' + months[calendarMonth] + '-' + calendarYear);
                        YAHOO.util.Get.script('scripts/calendar.js', { autopurge: true });
                        YAHOO.jms.sendGetRequest(YAHOO.jms.currentURL, YAHOO.jms.viewCallback);
                        YAHOO.jms.tabView.set('activeTab', tab);
                    };
                    YAHOO.jms.jobActionCal = function() {
                        YAHOO.jms.currentURL = 'job/calendarJobAction.do?dispatch=find&year=' + calendarYear + '&month=' + calendarMonth;
                        var tab = YAHOO.jms.tab1;
                        tab.set('label', 'Job Action Calendar ' + months[calendarMonth] + '-' + calendarYear);
                        YAHOO.util.Get.script('scripts/calendar.js', { autopurge: true });
                        YAHOO.jms.sendGetRequest(YAHOO.jms.currentURL, YAHOO.jms.viewCallback);
                        YAHOO.jms.tabView.set('activeTab', tab);
                    };

                    //Handle the click event on the cal box at the bottom
                    YAHOO.util.Event.on('aseChangeCal', 'click', function(ev) {
                        YAHOO.util.Event.stopEvent(ev);
                        YAHOO.jms.aseChangeCal();
                    });
                    //Handle the click event on the cal box at the bottom
                    YAHOO.util.Event.on('jobActionCal', 'click', function(ev) {
                        YAHOO.util.Event.stopEvent(ev);
                        YAHOO.jms.jobActionCal();
                    });

//=======================================================================
// alert.js
//=======================================================================
                    //Create a SimpleDialog used to mimic an OS dialog
                    YAHOO.jms.alertPanel = new YAHOO.widget.SimpleDialog('alertDiv', {
                        fixedcenter: true,
                        visible: false,
                        modal: true,
                        width: '300px',
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
                    //Set the header
                    YAHOO.jms.alertPanel.setHeader('Alert');
                    //Give the body something to render with
                    YAHOO.jms.alertPanel.setBody('Notta');
                    //Render the Dialog to the body
                    YAHOO.jms.alertPanel.render(document.body);
                    //Create a namespace alert method
                    YAHOO.jms.alert = function(str, oResponse) {
                        if (oResponse) {
                            YAHOO.jms.alertPanel.setHeader(str);
                            YAHOO.jms.alertPanel.setBody(oResponse.status + '<br/>' + oResponse.responseText);
                        } else {
                            YAHOO.jms.alertPanel.setBody(str);
                        }
                        //Set an icon
                        YAHOO.jms.alertPanel.cfg.setProperty('icon', YAHOO.widget.SimpleDialog.ICON_WARN);
                        //Bring the dialog to the top
                        YAHOO.jms.alertPanel.bringToTop();
                        //Show it
                        YAHOO.jms.alertPanel.show();
                    };
                    //YAHOO.jms.alert('This is a new product from ApolloSoft (www.apollosoft.net).');

//=======================================================================
// CKeditor.js
//=======================================================================
                    // process CKeditor input
                    //http://docs.fckeditor.net/CKEditor_3.x/Developers_Guide/Integration
                    YAHOO.jms.updateEditorInput = function() {
                        var editorInputEl = YUD.get('editorInput');
                        if (editorInputEl && YAHOO.jms.editorInput) {
                            editorInputEl.value = YAHOO.jms.editorInput.getData();
                        }
                    };
                    // process CKeditor input
                    //http://docs.fckeditor.net/CKEditor_3.x/Developers_Guide/Integration
                    YAHOO.jms.destroyEditorInput = function() {
                        if (YAHOO.jms.editorInput) {
                            YAHOO.jms.editorInput.destroy(true);
                            YAHOO.jms.editorInput = null;
                        }
                    };

//=======================================================================
// edit.js
//=======================================================================
                    // Define various event handlers for Dialog
                    var handleSubmit = function() {
                        YAHOO.jms.updateEditorInput();
                        //add readOnly parameter to mark transaction for commit (@see TransactionFilter)
                        var submitAction = this.form.action;
                        submitAction += (this.form.action.indexOf('?') > 0 ? '&' : '?') + 'readOnly=false';
                        YAHOO.jms.sendPostRequest(this.form, submitAction, null, null, YAHOO.jms.edit.submitCallback);
                    };
                    var handleCancel = function() {
                        YAHOO.jms.destroyEditorInput();
                        //this.hide();
                        this.cancel();
                    };

                    //Create a SimpleDialog used to mimic an OS dialog
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
                        buttons: [
                           { text: 'Submit', isDefault: true, handler: handleSubmit }
                           , { text: 'Cancel', handler: handleCancel }
                        ]
                    });

                    //will hide a Panel when the escape key is pressed
                    var escapeKey = new YAHOO.util.KeyListener(document, { keys: 27 },                              
                        { fn: handleCancel, scope: YAHOO.jms.editPanel, correctScope: true } );
                    YAHOO.jms.editPanel.cfg.queueProperty('keylisteners', escapeKey);

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

                    //Set the header
                    YAHOO.jms.editPanel.setHeader('Edit');
                    //Give the body something to render with
                    YAHOO.jms.editPanel.setBody('Notta');
                    //Render the Dialog to the body
                    YAHOO.jms.editPanel.render(document.body);

//=======================================================================
//scripts/module/common.js
//=======================================================================
                    /**
                     * Edit/Complete JobAction
                     */
                    YAHOO.jms.editJobAction = function(jobActionId) {
                        YAHOO.jms.sendGetRequest('job/editJobAction.do?dispatch=viewJobAction&actionId=' + jobActionId, null, 'Complete Job Action');
                    };

                    /**
                     * Edit File AseChange
                     */
                    YAHOO.jms.editAseChange = function(fileId) {
                        YAHOO.jms.sendGetRequest('file/editAseChange.do?dispatch=edit&id=' + fileId, 'scripts/module/common.js', 'ASE Change Over - Supplier Scheduling');
                    };
                }, 0);
                //if (debug) this.getUnitByPosition('right').collapse(); //LOG
                YUD.setStyle(document.body, 'visibility', 'visible');
            });

            //Render the layout
            YAHOO.jms.layout.render();

            //Setup the click listeners on the folder list
            YUE.on('folder_list', 'click', function(ev) {
                var tar = YUE.getTarget(ev);
                if (tar.tagName.toLowerCase() != 'a') {
                    tar = null;
                }
                //Make sure we are a link in the list's 
                if (tar && YUS.test(tar, '#folder_list ul li a')) {
                    var url = tar.href;
                    var title = tar.title || tar.innerHTML;
                    YUD.removeClass(YUS.query('#folder_list li'), 'selected');
                    YUD.addClass(tar.parentNode, 'selected');
                    YUE.stopEvent(ev);
                    //to avoid refresh
                    if (YAHOO.jms.currentURL != url) {
                        YAHOO.jms.currentURL = url;
                        YAHOO.jms.navigate(url, title); // via browser history manager
                        //YAHOO.jms.sendGetRequest(url, YAHOO.jms.viewCallback);
                    }
                }
            });

            /**
             * 
             */
            YAHOO.jms.viewCallback = {
                cache:false,
                success: function(oResponse) {
                    // clear cookie
                    YAHOO.util.Cookie.set(YAHOO.jms.tabViewActiveIndex, null);
                    // Set the body to the string passed
                    var response = oResponse.responseText;
                    var el = YAHOO.jms.layout.getUnitByPosition('center').get('wrap');
                    el.innerHTML = response;
                    // execute javascript (if any)
                    YAHOO.jms.loadScripts(el);
                },
                failure: function(oResponse) {
                    YAHOO.jms.failure(oResponse);
                }
            };
        }
    });
    loader.insert();
})();