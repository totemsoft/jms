package au.gov.qld.fire.jms.service;

import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.domain.finance.InvoiceBatch;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface InvoiceService
{

	/**
	 * 
	 * @return
	 */
	List<Integer> findFiscalYears();

	/**
	 * 
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	List<BasePair> findInvoiceArea(String name) throws ServiceException;

	/**
	 * Create Batch
	 * @param invoiceTypeId
	 * @param invoiceAreaId
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	InvoiceBatch createBatch(Long invoiceTypeId, Long invoiceAreaId) throws ServiceException;

	/**
	 * 
	 * @param batchId
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	Invoice createInvoice(Long batchId) throws ServiceException;

	/**
	 * 
	 * @param batchId
	 * @return
	 * @throws ServiceException
	 */
	InvoiceBatch findBatchById(Long batchId) throws ServiceException;

	/**
	 * 
	 * @param invoiceId
	 * @return
	 * @throws ServiceException
	 */
	Invoice findInvoiceById(Long invoiceId) throws ServiceException;

	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws ServiceException
	 */
	List<Invoice> findInvoiceByCriteria(InvoiceSearchCriteria criteria)
	    throws ServiceException;

	/**
     * 
     * @param entities
     * @param contentStream
     * @param contentType
     * @throws ServiceException
     */
	void exportInvoice(List<Invoice> entities,
	    OutputStream contentStream, ContentTypeEnum contentType)
	    throws ServiceException;

	/**
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	void saveInvoice(Invoice entity) throws ServiceException;

}