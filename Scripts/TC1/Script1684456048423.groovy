import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files

import javax.xml.namespace.NamespaceContext
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

import org.w3c.dom.Document
import org.w3c.dom.NodeList

import com.kms.katalon.core.configuration.RunConfiguration

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path xml = projectDir.resolve("response.xml")
assert Files.exists(xml)

FileInputStream fileIS = new FileInputStream(xml.toFile())
DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance()
builderFactory.setNamespaceAware(true)
DocumentBuilder builder = builderFactory.newDocumentBuilder()
Document xmlDocument = builder.parse(fileIS)

XPath xPath = XPathFactory.newInstance().newXPath()
xPath.setNamespaceContext(new NamespaceContext() {
	@Override
	public Iterator getPrefixes(String arg0) {
		return null;
	}
	@Override
	public String getPrefix(String arg0) {
		return null;
	}
	@Override
	public String getNamespaceURI(String arg0) {
		if ("my".equals(arg0)) {
			return "GeoTribUy"
		} else if ("SOAP-ENV".equals(arg0)) {
			return "http://schemas.xmlsoap.org/soap/envelope/"
		}
		return null
	}
})

String expr1 = "/SOAP-ENV:Envelope/SOAP-ENV:Body"
NodeList nodeList = (NodeList) xPath.compile(expr1).evaluate(xmlDocument, XPathConstants.NODESET)
println "nodeList.getLength()=" + nodeList.getLength()
assert nodeList.getLength() > 0


String expr2 = "/SOAP-ENV:Envelope/SOAP-ENV:Body/my:GenWs03ConsultadeDeudaCC.ExecuteResponse/my:Buenpaga";
String s = (String) xPath.compile(expr2).evaluate(xmlDocument, XPathConstants.STRING);
println s
assert s == "S"