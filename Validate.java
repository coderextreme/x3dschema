/**
 * Copyright (c) 2022-2023. John Carlson
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of content nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE

*/
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileWriter;
import org.web3d.x3d.jsail.X3DLoaderDOM;
import org.web3d.x3d.jsail.Core.X3D;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;

class MyDefaultHandler extends DefaultHandler {
	public void warning(SAXParseException e) {
		e.printStackTrace(System.err);
	}
	public void error(SAXParseException e) {
		e.printStackTrace(System.err);
	}
	public void fatalError(SAXParseException e) {
		e.printStackTrace(System.err);
	}
}

public class Validate {
	public static void main(String [] args) throws Exception {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "Script");

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new File("x3d-4.0.xsd"));
		Validator validator = schema.newValidator();
		validator.setErrorHandler(new MyDefaultHandler());

		for (int i = 0; i < args.length; i++) {
			try {
				//if (args[i].contains(File.separator+"originals"+File.separator)) {
					//continue;
				//}
				X3DLoaderDOM xmlLoader = new X3DLoaderDOM();
				Document document = null;
				File f = new File(args[i]);
				System.err.println("Processing "+args[i]);
				if (args[i].endsWith(".json") || args[i].endsWith(".x3dj")) {
					X3DJSONLD loader = new X3DJSONLD();
					JsonObject jsobj = loader.readJsonFile(f);
					String version = loader.getX3DVersion(jsobj);
					document = loader.loadJsonIntoDocument(jsobj, version, args[i].endsWith(".x3dj"));
					DOMImplementation domImpl = document.getImplementation();
					DocumentType doctype = domImpl.createDocumentType("X3D",
					    "ISO//Web3D//DTD X3D "+version+"//EN",
					    "https://www.web3d.org/specifications/x3d-"+version+".dtd");
					// document.insertBefore(doctype, document.getDocumentElement().getNextChild());
					transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
					transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
					File xmlf = new File(args[i].substring(0, args[i].lastIndexOf("."))+".xml");
					FileWriter fw = new FileWriter(xmlf);
					DOMSource source = new DOMSource(document);
					StreamResult streamResult = new StreamResult(fw);
					DOMResult domResult = new DOMResult();
					transformer.transform(source, streamResult);
					transformer.transform(source, domResult);
					document = (Document)domResult.getNode();
					// fw.write((loader.serializeDOM(version, document)));
					fw.close();
				} else {
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					dbf.setNamespaceAware(true);
					dbf.setValidating(true);
					DocumentBuilder db = dbf.newDocumentBuilder();

					document = db.parse(f);
				}

				// Validate with XML Schema
				DOMSource source = new DOMSource(document);
				validator.validate(source);

				/*
				// Validate with X3DJSAIL
				X3D X3D0 = (X3D)xmlLoader.toX3dModelInstance(document);
				String validationResults = X3D0.validationReport();
				if (validationResults.startsWith("\n")) {
					System.err.println("Validation report for "+args[i]);
					System.err.println(validationResults.trim());
				} else {
					System.err.println("Valid "+args[i]);
				}
				*/
			} catch (Exception e) {
				System.err.println("Invalid "+args[i]);
				e.printStackTrace(System.err);
			}
		}
	}
}
