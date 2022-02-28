package au.gov.qld.fire.jms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.jms.dao.InvoiceDao;
import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.domain.finance.InvoiceArea;
import au.gov.qld.fire.jms.domain.finance.InvoiceData;
import au.gov.qld.fire.jms.domain.finance.InvoiceGlData;
import au.gov.qld.fire.jms.domain.finance.InvoiceHeader;
import au.gov.qld.fire.jms.domain.finance.InvoiceText;
import au.gov.qld.fire.jms.domain.finance.InvoiceType;
import au.gov.qld.fire.service.Importer;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UaaInvoiceImporterImpl implements Importer<List<List<String>>>
{

    private static final Logger LOG = Logger.getLogger(UaaInvoiceImporterImpl.class);

	@Inject private InvoiceDao invoiceDao;

    /** dd.MM.yyyy date format */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.Importer#importData(java.lang.String, java.util.List)
     */
    public Map<? extends Request, List<ValidationMessage>> importData(String name, List<List<String>> tableData) throws ServiceException
    {
        try
        {
            //Map<Request, List<ValidationMessage>> errors = new LinkedHashMap<Request, List<ValidationMessage>>();
            Map<Integer, Invoice> invoices = new LinkedHashMap<Integer, Invoice>();
            Integer skipSequence = null;
            Date incidentDate = null;
            for (List<String> rowData : tableData)
            {
                // process data rows
                int i = 0;
                String transactionType = rowData.get(i++);
                // comment
                if (StringUtils.startsWith(transactionType, "**"))
                {
                	if (incidentDate == null)
                	{
                		incidentDate = getIncidentDate(rowData);
                	}
                    continue;
                }
                // data
                String rowType = rowData.get(i++);
                Integer sequence = new BigDecimal(rowData.get(i++)).intValue();
                if (skipSequence == sequence)
                {
                	continue;
                }
                if (StringUtils.equals(transactionType, "AR"))
                {
                    if (StringUtils.equals(rowType, "HDR"))
                    {
                        InvoiceHeader r = new InvoiceHeader();
                        r.setTransactionType(transactionType);
                        r.setRowType(rowType);
                        r.setSequence(sequence);
                        r.setCompanyCode(new BigDecimal(rowData.get(i++)).intValue());
                        r.setDocumentDate(DateUtils.parse(rowData.get(i++), DATE_FORMAT, null));
                        r.setPostDate(DateUtils.parse(rowData.get(i++), DATE_FORMAT, null));
                        r.setDocumentType(rowData.get(i++));
                        r.setCurrency(rowData.get(i++));
                        String reference = rowData.get(i++);
                        // no more invoice data
                        if (StringUtils.isBlank(reference))
                        {
                        	break;
                        }
                        // check if this invoice was already imported
                        if (invoiceDao.findByReference(reference) != null)
                        {
                        	skipSequence = sequence;
                        	continue;
                        }
                        // ok to proceed
                        r.setReference(reference);
                        i = i + 4;
                        r.setText(rowData.get(i++));
                        //
                        Invoice invoice = getInvoice(invoices, sequence);
                        invoice.setHeader(r);
                        invoice.setIncidentDate(incidentDate);
                        incidentDate = null;
                    }
                    else if (StringUtils.equals(rowType, "ITM"))
                    {
                    	InvoiceData r = new InvoiceData();
                        r.setTransactionType(transactionType);
                        r.setRowType(rowType);
                        r.setSequence(sequence);
                        i = i + 10;
                        r.setAmount(new BigDecimal(rowData.get(i++)));
                        r.setDebitCredit(rowData.get(i++));
                        r.setText(rowData.get(i++));
                        r.setAssignment(rowData.get(i++));
                        r.setSapCustNo(new BigDecimal(rowData.get(i++)).longValue());
                        r.setPaymentTerm(rowData.get(i++));
                        r.setDunningArea(rowData.get(i++));
                        getInvoice(invoices, sequence).setData(r);
                    }
                    else if (StringUtils.equals(rowType, "ILT"))
                    {
                    	InvoiceText r = new InvoiceText();
                        r.setTransactionType(transactionType);
                        r.setRowType(rowType);
                        r.setSequence(sequence);
                        i = i + 1; // long text value 0001
                        r.setText(rowData.get(i++));
                        getInvoice(invoices, sequence).setText(r);
                    }
                }
                else if (StringUtils.equals(transactionType, "GL"))
                {
                    if (StringUtils.equals(rowType, "ITM"))
                    {
                    	InvoiceGlData r = new InvoiceGlData();
                        r.setTransactionType(transactionType);
                        r.setRowType(rowType);
                        r.setSequence(sequence);
                        String glAccount = rowData.get(i++);
                        if (StringUtils.isBlank(glAccount))
                        {
                        	continue; // last GL Data record for this invoice
                        }
                        r.setGlAccount(new BigDecimal(glAccount).longValue());
                        r.setCostCenter(new BigDecimal(rowData.get(i++)).longValue());
                        i = i + 3;
                        r.setTaxCode(rowData.get(i++));
                        i = i + 4;
                        r.setAmount(new BigDecimal(rowData.get(i++)));
                        r.setDebitCredit(rowData.get(i++));
                        r.setText(rowData.get(i++));
                        r.setAssignment(rowData.get(i++));
                        getInvoice(invoices, sequence).add(r);
                    }
                }
            }
            // save
            for (Invoice invoice : invoices.values())
            {
            	invoiceDao.save(invoice);
            }
            LOG.debug(invoices.size() + " invoices imported.");
            return null;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    private Invoice getInvoice(Map<Integer, Invoice> invoices, Integer sequence)
    {
        Invoice invoice = invoices.get(sequence);
        if (invoice == null)
        {
            invoice = new Invoice();
            Date today = DateUtils.getCurrentDate();
            invoice.setDateReceived(today);
            invoice.setFiscalYear(DateUtils.getFiscalYear(today));
            invoice.setInvoiceArea(new InvoiceArea(1L));
            invoice.setInvoiceType(new InvoiceType(1L));
            invoices.put(sequence, invoice);
        }
        return invoice;
    }

    private Date getIncidentDate(List<String> rowData)
    {
		boolean incidentDateNext = false;
		for (String cellData : rowData)
		{
			if ("Incident Date".equals(cellData))
			{
				incidentDateNext = true;
			}
			else if (incidentDateNext)
			{
				Date incidentDate = DateUtils.parse(cellData, DATE_FORMAT, null);
				if (incidentDate != null)
				{
					return incidentDate;
				}
			}
		}
		return null;
    }

}