<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:xhtml/>

    <div id="leftNav">
        <div id="folder_list">
            <div id="mainMenu" class="yuimenu"><div class="bd">
                <ul class="first-of-type">
                    <perm:present module="/home" path="/findHome">
                        <li id="menu.home" class="yuimenuitem first-of-type home"><a class="yuimenuitemlabel" href="${findHome}" onclick="return false;" title="Home Page"><span>${nbsp}</span>Home</a></li>
                    </perm:present>
                    <perm:present module="/file" path="">
                        <li id="menu.file" class="yuimenuitem file"><a class="yuimenuitemlabel" href="javascript:;" title="Files"><span>${nbsp}</span>Files</a>
                            <div id="fileMenu" class="yuimenu"><div class="bd">
                                <ul class="first-of-type width10em">
                                    <perm:present module="/todo" path="/findTodoFile">
                                        <li id="menu.todoFile" class="yuimenuitem todoFile"><a class="yuimenuitemlabel" href="${findTodoFile}" onclick="return false;" title="My To Do Files"><span>${nbsp}</span>My To Do</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/findFile">
                                        <li id="menu.fileActive" class="yuimenuitem fileActive" title="Opened/Closed/Archived"><a class="yuimenuitemlabel" href="${findFile}" onclick="return false;" title="All Files"><span>${nbsp}</span>Files</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/isolation">
                                        <li id="menu.isolation" class="yuimenuitem isolation"><a class="yuimenuitemlabel" href="${isolation}" onclick="return false;" title="Isolation Management"><span>${nbsp}</span>Isolation</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/findFile">
                                        <li id="menu.fileArchive" class="yuimenuitem fileArchive" title="Archiving Register"><a class="yuimenuitemlabel" href="${fileArchive}" onclick="return false;" title="Archive"><span>${nbsp}</span>Archive</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/findAseChange">
                                        <li id="menu.aseChange" class="yuimenuitem file"><a class="yuimenuitemlabel" href="${findAseChange}" onclick="return false;" title="ASE Change"><span>${nbsp}</span>ASE Change</a></li>
                                    </perm:present>
                                </ul>
                            </div></div>
                        </li>
                    </perm:present>
                    <perm:present module="/job" path="">
                        <li id="menu.job" class="yuimenuitem job"><a class="yuimenuitemlabel" href="javascript:;" title="Jobs"><span>${nbsp}</span>Jobs</a>
                            <div id="jobMenu" class="yuimenu"><div class="bd">
                                <ul class="first-of-type width10em">
                                    <perm:present module="/todo" path="/findTodoJob">
                                        <li id="menu.todoJob" class="yuimenuitem first-of-type todoJob"><a class="yuimenuitemlabel" href="${findTodoJob}" onclick="return false;" title="My To Do Jobs"><span>${nbsp}</span>My To Do</a></li>
                                    </perm:present>
                                    <perm:present module="/job" path="/findJob">
                                        <li id="menu.job" class="yuimenuitem job"><a class="yuimenuitemlabel" href="${findJob}" onclick="return false;" title="All Jobs"><span>${nbsp}</span>Jobs</a></li>
                                    </perm:present>
                                    <perm:present module="/job" path="/findJob">
                                        <li id="menu.jobRequest" class="yuimenuitem jobRequest"><a class="yuimenuitemlabel" href="${findJobRequest}" onclick="return false;" title="Jobs Pending"><span>${nbsp}</span>Jobs Pending</a></li>
                                    </perm:present>
                                </ul>
                            </div></div>
                        </li>
                    </perm:present>
                    <perm:present module="/file" path="">
                        <li id="menu.action" class="yuimenuitem action"><a class="yuimenuitemlabel" href="javascript:;" title="Actions"><span>${nbsp}</span>Actions</a>
                            <div id="actionMenu" class="yuimenu"><div class="bd">
                                <ul class="first-of-type width10em">
                                    <perm:present module="/file" path="/findCall">
                                        <li id="menu.call" class="yuimenuitem call"><a class="yuimenuitemlabel" href="${findCall}" onclick="return false;" title="Calls"><span>${nbsp}</span>Calls</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/enquiry">
                                        <li id="menu.enquiry" class="yuimenuitem enquiry"><a class="yuimenuitemlabel" href="${enquiry}" onclick="return false;" title="Enquiries"><span>${nbsp}</span>Enquiries</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/mailRegister">
                                        <li id="menu.mailRegister" class="yuimenuitem mailRegister"><a class="yuimenuitemlabel" href="file/mailRegister.do?dispatch=find" onclick="return false;" title="Mail Register"><span>${nbsp}</span>Mail Register</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/mailOut">
                                        <li id="menu.mailOut" class="yuimenuitem mailOut"><a class="yuimenuitemlabel" href="file/mailOut.do?dispatch=find" onclick="return false;" title="Bulk Mail Out"><span>${nbsp}</span>Bulk Mail Out</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/mailIn">
                                        <li id="menu.mailIn" class="yuimenuitem mailIn"><a class="yuimenuitemlabel" href="file/mailIn.do?dispatch=find" onclick="return false;" title="Bulk Mail In"><span>${nbsp}</span>Bulk Mail In</a></li>
                                    </perm:present>
                                </ul>
                            </div></div>
                        </li>
                    </perm:present>
                    <perm:present module="/file" path="/uaa">
                        <li id="menu.uaa" class="yuimenuitem file"><a class="yuimenuitemlabel" href="javascript:;" title="UAA"><span>${nbsp}</span>UAA</a>
                            <div id="uaaMenu" class="yuimenu"><div class="bd">
                                <ul class="first-of-type width10em">
                                    <perm:present module="/file" path="/uaaIncident">
                                        <li id="menu.uaaIncident" class="yuimenuitem uaaIncident" title="UAA Incidents"><a class="yuimenuitemlabel" href="file/uaaIncident.do?dispatch=find" onclick="return false;" title="Incidents"><span>${nbsp}</span>Incidents</a></li>
                                    </perm:present>
                                    <perm:present module="/file" path="/uaaInvoice">
                                        <li id="menu.uaaInvoice" class="yuimenuitem uaaInvoice" title="UAA Invoices"><a class="yuimenuitemlabel" href="file/uaaInvoice.do?dispatch=find" onclick="return false;" title="Invoices"><span>${nbsp}</span>Invoices</a></li>
                                    </perm:present>
                                </ul>
                            </div></div>
                        </li>
                    </perm:present>
                    <perm:present module="/finance" path="">
                        <li id="menu.finance" class="yuimenuitem finance"><a class="yuimenuitemlabel" href="javascript:;" title="Finance"><span>${nbsp}</span>Finance</a>
                            <div id="financeMenu" class="yuimenu"><div class="bd">
                                <ul class="first-of-type width15em">
                                    <perm:present module="/finance" path="/externalInvoice">
                                        <li id="menu.externalInvoice" class="yuimenuitem externalInvoice" title="Open Invoicing"><a class="yuimenuitemlabel" href="finance/externalInvoice.do?dispatch=find" onclick="return false;" title="Community Safety Invoicing"><span>${nbsp}</span>Invoicing</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/accountInfo">
                                        <li id="menu.accountInfo" class="yuimenuitem first-of-type accountInfo" title="Enhanced SAP/Account Information"><a class="yuimenuitemlabel" href="finance/accountInfo.do?dispatch=find" onclick="return false;" title="SAP/Account"><span>${nbsp}</span>SAP/Account</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/budget">
                                        <li id="menu.budget" class="yuimenuitem budget" title="Budgets"><a class="yuimenuitemlabel" href="finance/budget.do?dispatch=find" onclick="return false;" title="Budgets"><span>${nbsp}</span>Budgets</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/accountPayable">
                                        <li id="menu.accountPayable" class="yuimenuitem accountPayable" title="Accounts Payable"><a class="yuimenuitemlabel" href="finance/accountPayable.do?dispatch=find" onclick="return false;" title="Accounts Payable"><span>${nbsp}</span>Accounts Payable</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/accrual">
                                        <li id="menu.accrual" class="yuimenuitem accrual" title="Accruals"><a class="yuimenuitemlabel" href="finance/accrual.do?dispatch=find" onclick="return false;" title="Accruals"><span>${nbsp}</span>Accruals</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/invoiceGeneration">
                                        <li id="menu.invoiceGeneration" class="yuimenuitem invoiceGeneration" title="Annual Invoice Generation"><a class="yuimenuitemlabel" href="finance/invoiceGeneration.do?dispatch=find" onclick="return false;" title="Invoice Generation"><span>${nbsp}</span>Invoice Generation</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/accountHistory">
                                        <li id="menu.accountHistory" class="yuimenuitem accountHistory" title="Accounts History"><a class="yuimenuitemlabel" href="finance/accountHistory.do?dispatch=find" onclick="return false;" title="Accounts History"><span>${nbsp}</span>Accounts History</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/debtInfo">
                                        <li id="menu.debtInfo" class="yuimenuitem debtInfo" title="Debt Information"><a class="yuimenuitemlabel" href="finance/debtInfo.do?dispatch=find" onclick="return false;" title="Debt Information"><span>${nbsp}</span>Debt Information</a></li>
                                    </perm:present>
                                    <perm:present module="/finance" path="/creditControl">
                                        <li id="menu.creditControl" class="yuimenuitem creditControl" title="Credit Control"><a class="yuimenuitemlabel" href="finance/creditControl.do?dispatch=find" onclick="return false;" title="Credit Control"><span>${nbsp}</span>Credit Control</a></li>
                                    </perm:present>
                                </ul>
                            </div></div>
                        </li>
                    </perm:present>
                    <perm:present module="/fca" path="/findFca">
                        <li id="menu.fca" class="yuimenuitem fca"><a class="yuimenuitemlabel" href="${findFca}" onclick="return false;" title="FCA"><span>${nbsp}</span>FCA</a></li>
                    </perm:present>
                    <perm:present module="/file" path="/aseKey">
                        <li id="menu.aseKey" class="yuimenuitem aseKey"><a class="yuimenuitemlabel" href="javascript:;" title="Ase Keys"><span>${nbsp}</span>Ase Keys</a>
                            <div id="aseKeyMenu" class="yuimenu"><div class="bd">
                                <ul class="first-of-type width10em">
                                    <li id="menu.aseKeyOrders" class="yuimenuitem first-of-type aseKeyOrder"><a class="yuimenuitemlabel" href="${aseKeyOrder}" onclick="return false;" title="Orders"><span>${nbsp}</span>Orders</a></li>
                                    <li id="menu.aseKeys" class="yuimenuitem aseKey"><a class="yuimenuitemlabel" href="${aseKey}" onclick="return false;" title="Keys"><span>${nbsp}</span>Keys</a></li>
                                </ul>
                            </div></div>
                        </li>
                    </perm:present>
                    <perm:present module="/user" path="/workflowRegister">
                        <li id="menu.workflowRegister" class="yuimenuitem workflowRegister"><a class="yuimenuitemlabel" href="${workflowRegister}" onclick="return false;" title="Workflow Register"><span>${nbsp}</span>Workflow Register</a></li>
                    </perm:present>
                </ul>
                <ul>
                    <perm:present module="/report">
                        <li id="menu.report" class="yuimenuitem first-of-type report"><a class="yuimenuitemlabel" href="${findReport}" onclick="return false;" title="Reports"><span>${nbsp}</span>Reports</a></li>
                    </perm:present>
                </ul>
                
            </div></div>
            <div class="wrap">
                <perm:present module="/user" path="/calendarStaff">
                    <div id="staffCal" title="Staff Calendar"><span class="icon">${nbsp}</span><span>Staff Calendar</span></div>
                </perm:present>
                <perm:present module="/job" path="/calendarJobAction">
                    <div id="jobActionCal" title="Job Action Calendar"><span class="icon">${nbsp}</span><span>Job Action Calendar</span></div>
                </perm:present>
                <perm:present module="/file" path="/calendarAseChange">
                    <div id="aseChangeCal" title="ASE Change Calendar"><span class="icon">${nbsp}</span><span>ASE Change Calendar</span></div>
                </perm:present>
            </div>
            <div class="wrap">
                <div id="calContainer"><div id="cal">${nbsp}</div></div>
            </div>
        </div>
    </div>

</jsp:root>