(function() {
//    var loader = new YAHOO.util.YUILoader({
//        onSuccess: function() {
            var pagetoolsMenuBar = new YAHOO.widget.MenuBar('pagetools', {
                autosubmenudisplay: false,
                lazyload: true
            });
            pagetoolsMenuBar.render();

            YUE.addListener('editDiary', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editDiary.do?dispatch=editDiary&todoAction=false', null, 'New Diary');
            });
            YUE.addListener('editCall', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editCall.do?dispatch=editCall&todoAction=false', null, 'New Call');
            });
            YUE.addListener('editLetter', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editLetter.do?dispatch=editLetter&todoAction=false', null, 'New Letter');
            });
            YUE.addListener('editEmail', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editEmail.do?dispatch=editEmail&todoAction=false', 'scripts/module/file/editEmail.js', 'New Email');
            });
            YUE.addListener('editSMS', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editSMS.do?dispatch=editSMS&todoAction=false', null, 'New SMS');
            });
            YUE.addListener('editRFI', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editRFI.do?dispatch=editRFI&todoAction=false', null, 'New RFI');
            });
            YUE.addListener('editFileStatus', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editFileStatus.do?dispatch=editFileStatus&todoAction=false', null, 'Change File Status');
            });
            YUE.addListener('editJob', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.edit(null, 'file/editJob.do?dispatch=editJob&todoAction=false', null, 'New Job');
            });
            YUE.addListener('fileAudit', 'click', function(e) {
                YUE.stopEvent(e);
                //var target = YUE.getTarget(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/fileAudit.do?dispatch=find&id=' + fileId, null, 'File Changes');
            });

            YAHOO.jms.toggleContainers(YUD.get('detailDiv'));

            YUE.addListener('ownerEdit', 'click', function(e) {
                YUE.stopEvent(e);
                //var target = YUE.getTarget(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editOwner.do?dispatch=edit&ownerTypeId=1&id=' + fileId, null, 'Owner Details', 0, 420);
            });
            YUE.addListener('bodyCorporateEdit', 'click', function(e) {
                YUE.stopEvent(e);
                //var target = YUE.getTarget(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editOwner.do?dispatch=edit&ownerTypeId=2&id=' + fileId, null, 'Body Corporate Details', 0, 420);
            });
            YUE.addListener('propertyManagerEdit', 'click', function(e) {
                YUE.stopEvent(e);
                //var target = YUE.getTarget(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editOwner.do?dispatch=edit&ownerTypeId=3&id=' + fileId, null, 'Property Manager Details', 0, 420);
            });
            YUE.addListener('tenantEdit', 'click', function(e) {
                YUE.stopEvent(e);
                //var target = YUE.getTarget(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editOwner.do?dispatch=edit&ownerTypeId=4&id=' + fileId, null, 'Tenant Details', 0, 420);
            });
            //
            // om89
            YUE.addListener('alternateEdit', 'click', function(e) {
                YUE.stopEvent(e);
                //var target = YUE.getTarget(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editOwner.do?dispatch=edit&ownerTypeId=5&id=' + fileId, null, 'Alternate Details', 0, 420);
            });
//            YUE.addListener('defaultContactEdit', 'click', function(e) {
//                YUE.stopEvent(e);
//                //var target = YUE.getTarget(e);
//                var fileId = YUD.get('fileId').value;
//                var ownerTypeId = '';
//                YAHOO.jms.edit(null, 'file/editOwner.do?dispatch=edit&ownerTypeId=' + ownerTypeId + '&id=' + fileId, null, 'Default Contact Details', 0, 420);
//            });
            YUE.onContentReady('defaultOwnerTypeId', function() {
                YUE.on(this, 'change', function(oEvent) {
                    var dropdown = YUE.getTarget(oEvent);
                    var fileId = YUD.get('fileId').value;
                    var callback = {
                        cache: false,
                        success: function(oResponse) {
                            YAHOO.jms.editFile(fileId);
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    };
                    YAHOO.jms.asyncRequest('GET', 'oauth/file/defaultOwnerType/' + fileId + '/' + dropdown.value, callback);
                });
            });
            YUE.onContentReady('mailSentBtn', function() {
                new YAHOO.widget.Button('mailSentBtn').on('click', function(oEvent) {
                    var fileId = YUD.get('fileId').value;
                    var callback = {
                        cache: false,
                        success: function(oResponse) {
                            //YUD.get('sentDate').value = YAHOO.jms.formatDate(new Date(parseInt(oResponse.responseText)));
                            YAHOO.jms.editFile(fileId);
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    };
                    YAHOO.jms.asyncRequest('GET', 'oauth/file/mailSent/' + fileId, callback);
                });
            });
            YUE.onContentReady('mailReceivedBtn', function() {
                new YAHOO.widget.Button('mailReceivedBtn').on('click', function(oEvent) {
                    var fileId = YUD.get('fileId').value;
                    var callback = {
                        cache: false,
                        success: function(oResponse) {
                            //YUD.get('receivedDate').value = YAHOO.jms.formatDate(new Date(parseInt(oResponse.responseText)));
                            YAHOO.jms.editFile(fileId);
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    };
                    YAHOO.jms.asyncRequest('GET', 'oauth/file/mailReceived/' + fileId, callback);
                });
            });
            YUE.onContentReady('mailMethodId', function() {
                YUE.on(this, 'change', function(oEvent) {
                    var dropdown = YUE.getTarget(oEvent);
                    var fileId = YUD.get('fileId').value;
                    var callback = {
                        cache: false,
                        success: function(oResponse) {
                            YUD.get('mailMethodId').value = oResponse.responseText;
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    };
                    YAHOO.jms.asyncRequest('GET', 'oauth/file/mailMethod/' + fileId + '/' + dropdown.value, callback);
                });
            });
            YUE.onContentReady('noMailOut', function() {
                YUE.on(this, 'change', function(oEvent) {
                    var checkbox = YUE.getTarget(oEvent);
                    var fileId = YUD.get('fileId').value;
                    var callback = {
                        cache: false,
                        success: function(oResponse) {
                            YUD.get('noMailOut').checked = oResponse.responseText === 'true';
                        },
                        failure: function(oResponse) {
                            YAHOO.jms.emptyCallback.failure(oResponse);
                        }
                    };
                    YAHOO.jms.asyncRequest('GET', 'oauth/file/noMailOut/' + fileId + '/' + checkbox.checked, callback);
                });
            });

            var copyWarning = 'Warning, this will overide all entries for the selected data';
            var copyParentWarning = 'Warning, this will replace all contact information with data from the parent record';
            if (YAHOO.jms.file.copyMenu) {
                YAHOO.jms.file.copyMenu.destroy();
                YAHOO.jms.file.copyMenu = null;
            }
            var copyMenuTriggers = YUS.query('div[id^=copy.]', null);
            YAHOO.jms.file.copyMenu = new YAHOO.widget.ContextMenu('copyMenu', {
                trigger: copyMenuTriggers,
                lazyload: true,
                zIndex: 2,
                itemdata: [
                    [{text:'Copy Owner'}
                    ,{text:'Copy Body Corporate'}
                    ,{text:'Copy Property Manager'}
                    ,{text:'Copy Tenant'}],
                    [{text:'Copy Parent Details'}]
                ]
            });
            YAHOO.jms.file.copyMenu.subscribe('beforeShow', function(oType, oArgs) {
                var oMenuItems = this.getItems();
                for (var i = 0; i < oMenuItems.length; i++) {
                    var oItem = oMenuItems[i];
                    var target = this.contextEventTarget || this.parent.parent.contextEventTarget;
                    var ownerTypeId = YUD.get(target.id + '.ownerTypeId');
                    if (oItem.groupIndex == 0) {
                        if (ownerTypeId) {
                            YUD.removeClass(oItem.element.parentNode, 'hidden');
                        } else {
                            YUD.addClass(oItem.element.parentNode, 'hidden');
                        }
                        switch (oItem.index) {
                        case 0:
                            var copyId = YUD.get('owner.id');
                            oItem.cfg.setProperty('disabled', !copyId);
                            break;
                        case 1:
                            var copyId = YUD.get('bodyCorporate.id');
                            oItem.cfg.setProperty('disabled', !copyId);
                            break;
                        case 2:
                            var copyId = YUD.get('propertyManager.id');
                            oItem.cfg.setProperty('disabled', !copyId);
                            break;
                        case 3:
                            var copyId = YUD.get('tenant.id');
                            oItem.cfg.setProperty('disabled', !copyId);
                            break;
                        }
                    }
                    else if (oItem.groupIndex == 1) {
                        switch (oItem.index) {
                        case 0:
                            var copyId = YUD.get(target.id + '.parentId').value;
                            oItem.cfg.setProperty('disabled', !copyId);
                            break;
                        }
                    }
                }
            });
            YAHOO.jms.file.copyMenu.subscribe('click', function(oType, oArgs) {
                //var oEvent = oArgs[0];
                var oItem = oArgs[1]; // The MenuItem that was clicked
                if (!oItem || oItem.cfg.getProperty('disabled')) {
                    return;
                }
                var target = this.contextEventTarget || this.parent.parent.contextEventTarget;
                var fileId = YUD.get('fileId').value;
                var ownerTypeId = YUD.get(target.id + '.ownerTypeId');
                if (oItem.groupIndex == 0) {
                    ownerTypeId = ownerTypeId.value;
                    switch (oItem.index) {
                    case 0:
                        YAHOO.jms.confirm(copyWarning, function() {
                            var copyId = YUD.get('owner.id').value;
                            YAHOO.jms.edit(null,
                                'file/editOwner.do?dispatch=edit&ownerTypeId=' + ownerTypeId + '&id=' + fileId + '&copyId=' + copyId,
                                null, 'Copy Owner Details', 0, 420);
                        });
                        break;
                    case 1:
                        YAHOO.jms.confirm(copyWarning, function() {
                            var copyId = YUD.get('bodyCorporate.id').value;
                            YAHOO.jms.edit(null,
                                'file/editOwner.do?dispatch=edit&ownerTypeId=' + ownerTypeId + '&id=' + fileId + '&copyId=' + copyId,
                                null, 'Copy Body Corporate Details', 0, 420);
                        });
                        break;
                    case 2:
                        YAHOO.jms.confirm(copyWarning, function() {
                            var copyId = YUD.get('propertyManager.id').value;
                            YAHOO.jms.edit(null,
                                'file/editOwner.do?dispatch=edit&ownerTypeId=' + ownerTypeId + '&id=' + fileId + '&copyId=' + copyId,
                                null, 'Copy Property Manager Details', 0, 420);
                        });
                        break;
                    case 3:
                        YAHOO.jms.confirm(copyWarning, function() {
                            var copyId = YUD.get('tenant.id').value;
                            YAHOO.jms.edit(null,
                                'file/editOwner.do?dispatch=edit&ownerTypeId=' + ownerTypeId + '&id=' + fileId + '&copyId=' + copyId,
                                null, 'Copy Tenant Details', 0, 420);
                        });
                        break;
                    }
                }
                else if (oItem.groupIndex == 1) {
                    switch (oItem.index) {
                    case 0:
                        var copyId = YUD.get(target.id + '.parentId').value;
                        if (copyId) {
                            YAHOO.jms.confirm(copyParentWarning, function() {
                                if (ownerTypeId) {
                                    YAHOO.jms.edit(null,
                                        'file/editOwner.do?dispatch=edit&ownerTypeId=' + ownerTypeId.value + '&id=' + fileId + '&copyId=' + copyId,
                                        null, 'Copy Parent Details', 0, 420);
                                } else {
                                    YAHOO.jms.edit(null,
                                        'file/editEmergencyContact.do?dispatch=edit&id=' + fileId + '&copyId=' + copyId,
                                        null, 'Copy Parent Details', 0.8);
                                }
                            });
                        }
                        break;
                    }
                }
            });

            YUE.addListener('multiSiteEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editMultiSite.do?dispatch=edit&id=' + fileId, null, 'Multi Site Details');
            });
            YUE.addListener('fcaAseDetailsEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editFcaAse.do?dispatch=edit&id=' + fileId, null, 'FCA & ASE Details');
            });
            YUE.addListener('aseKeyEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var aseKeyId = YUD.get('aseKeyId').value;
                YAHOO.jms.edit(null, 'file/aseKey.do?dispatch=edit&id=' + aseKeyId, null, 'ASE Key Details', 0, 330);
            });
            YUE.addListener('mailRegisterEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var mailRegisterIdEl = YUD.get('mailRegisterId');
                if (mailRegisterIdEl) {
                    YAHOO.jms.edit(null, 'file/mailRegister.do?dispatch=reload&id=' + mailRegisterIdEl.value, null, 'Mail Register Details', 0, 0);
                } else {
                    var fileId = YUD.get('fileId').value;
                    YAHOO.jms.edit(null, 'file/mailRegister.do?dispatch=edit&fileId=' + fileId, null, 'Mail Register Details', 0, 0);
                }
            });
            YUE.addListener('mailRegisterNew', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/mailRegister.do?dispatch=edit&fileId=' + fileId, null, 'Mail Register Details', 0, 0);
            });
            if (YAHOO.jms.supplierTooltip) {
                YAHOO.jms.supplierTooltip.destroy();
                YAHOO.jms.supplierTooltip = null;
            }
            // The tooltip text for context element will be set from the title attribute
            YAHOO.jms.supplierTooltip = new YAHOO.widget.Tooltip('supplierTooltip', {
                context: 'aseSupplierLink',
                autodismissdelay: 30000 //The number of milliseconds to wait before automatically dismissing the Tooltip. Defaults to 5000.
            });
            YUE.addListener('aseChangeEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editAseChange.do?dispatch=edit&id=' + fileId, 'scripts/common.js', 'ASE Change Over - Supplier Scheduling');
            });
            YUE.addListener('stationKeyEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editStationKey.do?dispatch=edit&id=' + fileId, null, 'Nearest Responding Station &amp; Keys');
            });
            YUE.addListener('emergencyContactEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editEmergencyContact.do?dispatch=edit&id=' + fileId, null, 'Emergency Contacts', 0.8);
            });
            YUE.addListener('alarmPanelEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editAlarmPanel.do?dispatch=edit&id=' + fileId, null, 'Fire Panel Data &amp; External Alarm Technician');
            });
            YUE.addListener('buildingDetailsEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editBuilding.do?dispatch=edit&id=' + fileId, null, 'Building Details');
            });
            //
            if (YUD.get('crmActionsTree')) {
                if (YAHOO.jms.file.crmActionsTree) {
                    YAHOO.jms.file.crmActionsTree.destroy();
                    YAHOO.jms.file.crmActionsTree = null;
                }
                var tree = new YAHOO.widget.TreeView('crmActionsTree');
                tree.render();
                tree.subscribe('dblClickEvent', tree.onEventEditNode);
                tree.singleNodeHighlight = true;
                tree.subscribe('clickEvent', tree.onEventToggleHighlight);
                //tree.setNodesProperty('propagateHighlightUp', true);
                //tree.setNodesProperty('propagateHighlightDown', true);
                YAHOO.jms.file.crmActionsTree = tree;
            }
            YUE.addListener('crmActionsToDoEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/addFileAction.do?dispatch=edit&todoAction=true&id=' + fileId, null, 'Add CRM Action To Do');
            });
            //
            if (YUD.get('jobActionsTree')) {
                if (YAHOO.jms.file.jobActionsTree) {
                    YAHOO.jms.file.jobActionsTree.destroy();
                    YAHOO.jms.file.jobActionsTree = null;
                }
                var tree = new YAHOO.widget.TreeView('jobActionsTree');
                tree.render();
                tree.subscribe('dblClickEvent', tree.onEventEditNode);
                tree.singleNodeHighlight = true;
                tree.subscribe('clickEvent', tree.onEventToggleHighlight);
                //tree.setNodesProperty('propagateHighlightUp', true);
                //tree.setNodesProperty('propagateHighlightDown', true);
                YAHOO.jms.file.jobActionsTree = tree;
            }
            YUE.addListener('jobActionsToDoEdit', 'click', function(e) {
                YUE.stopEvent(e);
                //var fileId = YUD.get('fileId').value;
                //YAHOO.jms.edit(null, 'job/addJobAction.do?dispatch=edit&id=' + fileId, null, 'Add Job Actions To Do');
            });
            YUE.addListener('fileDocChkListEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editFileDocChkList.do?dispatch=edit&id=' + fileId, null, 'Document Checklist');
            });
            YUE.addListener('fileDocEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editFileDoc.do?dispatch=edit&id=' + fileId, null, 'Documents');
            });
            //
            // FCA Document
            //
            YUE.addListener('fcaDocEdit', 'click', function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.file.fcaDoc.addDirectory();
            });
            if (YUD.get('fcaDocsTree')) {
                YAHOO.jms.file.fcaDoc.createTree();
            }
            //
            // SAP Billing
            //
            YUE.addListener('sapBillingEdit', 'click', function(e) {
                YUE.stopEvent(e);
                var fileId = YUD.get('fileId').value;
                YAHOO.jms.edit(null, 'file/editSapBilling.do?dispatch=edit&id=' + fileId, null, 'Edit SAP Billing Data');
            });
//        }
//    });
//    loader.insert();
})();