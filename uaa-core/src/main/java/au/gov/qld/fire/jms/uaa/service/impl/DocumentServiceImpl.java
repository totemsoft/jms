package au.gov.qld.fire.jms.uaa.service.impl;

import org.apache.log4j.Logger;

import au.gov.qld.fire.jms.uaa.service.DocumentService;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocumentServiceImpl extends au.gov.qld.fire.service.impl.DocumentServiceImpl implements DocumentService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(DocumentServiceImpl.class);

    /** The location of all default reports. */
    private static final String REPORT_DIR = "/au/gov/qld/fire/jms/uaa/template/report/";

}