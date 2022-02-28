package au.gov.qld.fire.jms.service.impl;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import au.com.bytecode.opencsv.CSVWriter;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.dao.InvoiceAreaDao;
import au.gov.qld.fire.jms.dao.InvoiceBatchDao;
import au.gov.qld.fire.jms.dao.InvoiceDao;
import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.domain.finance.InvoiceArea;
import au.gov.qld.fire.jms.domain.finance.InvoiceBatch;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;
import au.gov.qld.fire.jms.domain.finance.InvoiceType;
import au.gov.qld.fire.jms.service.InvoiceService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InvoiceServiceImpl implements InvoiceService
{

    //private static final Logger LOG = Logger.getLogger(InvoiceServiceImpl.class);

    @Inject private InvoiceDao invoiceDao;

    @Inject private InvoiceAreaDao invoiceAreaDao;

    @Inject private InvoiceBatchDao invoiceBatchDao;

    @Inject private TemplateDao templateDao;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.InvoiceService#findFiscalYears()
     */
    public List<Integer> findFiscalYears()
    {
        try
        {
            return invoiceDao.findFiscalYears();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.InvoiceService#findInvoiceArea(java.lang.String)
     */
    public List<BasePair> findInvoiceArea(String name) throws ServiceException
    {
        try
        {
            List<BasePair> result = new ArrayList<BasePair>();
            for (InvoiceArea entity : invoiceAreaDao.findByNameLike(name))
            {
                result.add(new BasePair(entity.getId(), entity.getName()));
            }
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.InvoiceService#createBatch()
     */
    public InvoiceBatch createBatch(Long invoiceTypeId, Long invoiceAreaId)
        throws ServiceException
    {
        // validation
        if (invoiceTypeId == null)
        {
            throw new ServiceException("No valid Invoice Type found.");
        }
        if (invoiceAreaId == null)
        {
            throw new ServiceException("No valid Invoice Area found.");
        }
        try
        {
            final Date now = ThreadLocalUtils.getDate();
            int fiscalYear = DateUtils.getFiscalYear(now);
            int sequence = invoiceBatchDao.findMaxSequence(fiscalYear) + 1;
            InvoiceBatch result = new InvoiceBatch(new Long(fiscalYear + StringUtils.leftPad("" + sequence, 6, '0')));
            result.setInvoiceType(new InvoiceType(invoiceTypeId));
            result.setInvoiceArea(new InvoiceArea(invoiceAreaId));
            result.setFiscalYear(fiscalYear);
            result.setMonth(DateUtils.getMonth(now));
            result.setDay(DateUtils.getDay(now));
            result.setSequence(sequence);
            invoiceBatchDao.save(result);
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.InvoiceService#createInvoice(java.lang.Long)
     */
    public Invoice createInvoice(Long batchId) throws ServiceException
    {
        try
        {
            InvoiceBatch batch = findBatchById(batchId);
            Invoice result = new Invoice();
            result.setInvoiceBatch(batch);
            result.setInvoiceType(batch.getInvoiceType());
            result.setInvoiceArea(batch.getInvoiceArea());
            result.setFiscalYear(batch.getFiscalYear());
            result.setDateReceived(DateUtils.getCurrentDate());
            invoiceDao.save(result);
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.InvoiceService#findBatchById(java.lang.Long)
     */
    public InvoiceBatch findBatchById(Long batchId) throws ServiceException
    {
        try
        {
            return invoiceBatchDao.findById(batchId);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    public Invoice findInvoiceById(Long invoiceId) throws ServiceException
    {
        try
        {
            return invoiceDao.findById(invoiceId);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    public List<Invoice> findInvoiceByCriteria(InvoiceSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            List<Invoice> result = new ArrayList<Invoice>();
            //
            List<InvoiceBatch> batches = invoiceBatchDao.findByCriteria(criteria);
            // existing invoices (batched/non-batched)
            List<Invoice> invoices = invoiceDao.findByCriteria(criteria);
            // empty batches (just created and no invoice added yet) - for UI actions
            for (InvoiceBatch batch : batches)
            {
                if (batch.getInvoices().isEmpty())
                {
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceBatch(batch);
                    invoices.add(invoice);
                }
            }
            //
            result.addAll(invoices);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.InvoiceService#exportInvoice(java.util.List, java.io.OutputStream, au.gov.qld.fire.domain.refdata.ContentTypeEnum)
     */
    public void exportInvoice(List<Invoice> entities,
        OutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException
    {
        if (entities.isEmpty())
        {
            return;
        }

        try
        {
            if (ContentTypeEnum.isExcel(contentType))
            {
            	InputStream is = templateDao.getTemplateContent(TemplateEnum.EXPORT_INVOICE);
            	POIFSFileSystem fs = new POIFSFileSystem(is);
            	HSSFWorkbook wb = new HSSFWorkbook(fs);
            	// Invoice data
            	HSSFSheet sheet = wb.getSheetAt(wb.getActiveSheetIndex());
                // data
                for (Invoice entity : entities)
                {
                	
                }
            	fs.writeFilesystem(contentStream);
            	IOUtils.closeQuietly(is);
            }
            else if (ContentTypeEnum.isCsv(contentType))
            {
                Writer writer = new BufferedWriter(new OutputStreamWriter(contentStream));
                CSVWriter csvWriter = new CSVWriter(writer, ',');
                // header
                List<String> header = new ArrayList<String>();
                header.add("Invoice ID");
                
                csvWriter.writeNext(header.toArray(new String[0]));
                // data
                for (Invoice entity : entities)
                {
                    List<String> row = new ArrayList<String>();
                    row.add("" + entity.getId());
                    
                    csvWriter.writeNext(row.toArray(new String[0]));
                }
                csvWriter.close();
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.InvoiceService#saveInvoice(au.gov.qld.fire.jms.domain.finance.Invoice)
     */
    public void saveInvoice(Invoice entity) throws ServiceException
    {
        try
        {
            Invoice.unsetNullable(entity);
            invoiceDao.merge(entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}