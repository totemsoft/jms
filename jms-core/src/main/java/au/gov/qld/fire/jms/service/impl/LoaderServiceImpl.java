package au.gov.qld.fire.jms.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.oxm.Unmarshaller;

import au.gov.qld.fire.dao.DocumentDao;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.refdata.AuditStatusEnum;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.dao.FcaDao;
import au.gov.qld.fire.jms.dao.FileAuditDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileAudit;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form;
import au.gov.qld.fire.jms.service.LoaderService;
import au.gov.qld.fire.service.ServiceException;

/**
 * Load OM89 Forms
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class LoaderServiceImpl implements LoaderService
{

    /** logger. */
    //private static final Logger LOG = Logger.getLogger(LoaderServiceImpl.class);

    /** oxm */
    //@Inject private Marshaller marshaller;
    @Inject private Unmarshaller unmarshaller;

    @Inject private DocumentDao documentDao;

    @Inject private FcaDao fcaDao;

    @Inject private FileDao fileDao;

    @Inject private FileAuditDao fileAuditDao;

    private HttpMessageConverter<File> fileConverter;

    public void setFileConverter(HttpMessageConverter<File> fileConverter) {
        this.fileConverter = fileConverter;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.LoaderService#loadOM89Form(java.lang.String, java.io.InputStream)
     */
    public FileAudit loadOM89Form(String name, InputStream source) throws ServiceException {
        try {
            Document document = new Document();
            document.setName(name);
            document.setContentType(ContentTypeEnum.APPLICATION_XML.getContentType());
            document.setContent(IOUtils.toByteArray(source));
            documentDao.save(document);
            //
            final InputStream dataStream = new ByteArrayInputStream(document.getContent());
            // via oxm/jaxb2
            OM89Form form = (OM89Form) unmarshaller.unmarshal(new StreamSource(dataStream));
//            // via digester
//            final String OM89_DIGESTER_RULES = "au/gov/qld/fire/jms/mailOut/OM89-digester-rules.xml";
//            URL rulesXml = getClass().getClassLoader().getResource(OM89_DIGESTER_RULES);
//            RuleSet om89RuleSet = new FromXmlRuleSet(rulesXml, new DigesterRuleParser());
//            Digester digester = new Digester();
//            //digester.setNamespaceAware(true);
//            //digester.setValidating(true);
//            //digester.setUseContextClassLoader(true);
//            digester.addRuleSet(om89RuleSet);
//            // Digest domain specific xml to domain objects
//            OM89Form form = (OM89Form) digester.parse(dataStream);
            //
            String fcaNo = form.getAlarmDetails().getFca();
            Fca fca = fcaDao.findById(fcaNo);
            if (fca == null) {
                throw new ServiceException("No FCA found: " + fcaNo);
            }
            File file = fca.getFile();
            if (file == null) {
                throw new ServiceException("No FCA file found: " + fcaNo);
            }
            // load form data to file
            byte[] fileAuditContent = File.convert(ConvertUtils.convert(form, file), fileConverter);
            fileDao.detach(file); // to prevent from saving global entities
            // save file as audit (staging)
            FileAudit fileAudit = fileAuditDao.findById(file.getId());
            if (fileAudit == null) {
                fileAudit = new FileAudit(file);
            }
            fileAudit.setStatus(AuditStatusEnum.ACTIVE);
            fileAudit.setContent(fileAuditContent);
            fileAudit.setDocument(document);
            fileAuditDao.save(fileAudit);
            return fileAudit;
        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}